package com.guys.coding.hackathon.backend

import cats.effect.{Blocker, ContextShift, ExitCode, IO, Resource, Timer}
import cats.implicits._
import com.guys.coding.hackathon.backend.api.graphql.core.GraphqlRoute
import com.guys.coding.hackathon.backend.infrastructure.jwt.JwtTokenService
import com.guys.coding.hackathon.backend.infrastructure.slick.example.ExampleSchema
import com.guys.coding.hackathon.backend.infrastructure.slick.gym.{GymSchema, SlickGymRepository}
import com.guys.coding.hackathon.backend.infrastructure.slick.repo
import scala.concurrent.duration._
import scala.language.postfixOps

import com.guys.coding.hackathon.backend.infrastructure.slick.training.{
  ExerciseSchema,
  SlickExerciseRepository,
  SlickTrainingRepository,
  TrainingSchema
}
import com.guys.coding.hackathon.backend.infrastructure.slick.user.{
  SlickClientCoachCooperationRepository,
  SlickClientRepository,
  SlickCoachRepository,
  SlickMeasurementRepository
}
import hero.common.crypto.KeyReaders.{PrivateKeyReader, PublicKeyReader}
import hero.common.logging.Logger
import hero.common.logging.slf4j.LoggingConfigurator
import hero.common.util.LoggingExt
import org.http4s.server.{Router, Server}
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.CORS
import org.http4s.server.staticcontent._
import org.http4s.syntax.kleisli._

import scala.concurrent.ExecutionContext
import com.guys.coding.hackathon.backend.infrastructure.slick.user.CoachSchema
import com.guys.coding.hackathon.backend.infrastructure.slick.user.ClientSchema
import com.guys.coding.hackathon.backend.infrastructure.slick.user.ClientCoachCooperationSchema
import com.guys.coding.hackathon.backend.infrastructure.slick.user.MeasurementsSchema
import com.guys.coding.hackathon.backend.api.Fakerendpoint
import io.codeheroes.herochat.environment.facebook.`package`.FacebookSenderId
import akka.actor.Props
import com.guys.coding.bitehack.chat.ChatActor
import io.codeheroes.herochat.Chat
import io.codeheroes.herochat.environment.facebook.FacebookRequest
import io.codeheroes.herochat.environment.facebook.FacebookResponse
import akka.actor.ActorSystem
import io.codeheroes.herochat.`package`.PassivationConfig
import io.codeheroes.herochat.infrastructure.inmem.InMemChatRepository
import com.guys.coding.bitehack.api.core.EndpointsWrapper
import com.guys.coding.bitehack.api.core.HttpServer
import akka.stream.ActorMaterializer

class Application(config: ConfigValues)(
    implicit ec: ExecutionContext,
    appLogger: Logger[IO],
    cs: ContextShift[IO]
) extends LoggingExt {

  LoggingConfigurator.setRootLogLevel(config.app.rootLogLevel)
  LoggingConfigurator.setLogLevel("com.guys.coding.hackathon.backend", config.app.appLogLevel)

  implicit private val db: repo.profile.api.Database =
    repo.profile.api.Database.forConfig("slick.db", config.raw)

  private val schemas = List(
    ExampleSchema,
    GymSchema,
    ExerciseSchema,
    TrainingSchema,
    CoachSchema,
    ClientSchema,
    ClientCoachCooperationSchema,
    MeasurementsSchema
  )

  schemas.foreach(schema => repo.SchemaUtils.createSchemasIfNotExists(db, schema.schemas))

  private val privateKey               = PrivateKeyReader.get(config.authKeys.privatePath)
  private val publicKey                = PublicKeyReader.get(config.authKeys.publicPath)
  private val jwtTokenService          = new JwtTokenService(publicKey, privateKey)
  private val gymRepository            = new SlickGymRepository()
  val trainingRepository               = new SlickTrainingRepository()
  val exerciseRepository               = new SlickExerciseRepository()
  val clientCoachCooperationRepository = new SlickClientCoachCooperationRepository()
  val clientRepository                 = new SlickClientRepository()
  val coachRepository                  = new SlickCoachRepository()
  val measurementsRepository           = new SlickMeasurementRepository()

  private val services = Services(
    gymRepository,
    trainingRepository,
    exerciseRepository,
    clientCoachCooperationRepository,
    clientRepository,
    coachRepository,
    measurementsRepository,
    jwtTokenService
  )

  val graphqlRoute = new GraphqlRoute(services)

  val serveChatbot: IO[Unit] = IO.shift.flatMap { _ =>
    com.guys.coding.hackathon.backend.app.chat.JournalTablesInitializer.createJournalSchema(db)
    def chatActorProvider(id: FacebookSenderId) = Props(new ChatActor(id, services))

    def chatActorRepositoryProvider(propsProvider: PassivationConfig => Props)(implicit system: ActorSystem) =
      new InMemChatRepository[FacebookSenderId, FacebookRequest, FacebookResponse](propsProvider, 10 seconds)

    implicit val as  = ActorSystem()
    implicit val mat = ActorMaterializer()

    val chat = new Chat[FacebookSenderId, FacebookRequest, FacebookResponse](
      chatActorRepositoryProvider,
      chatActorProvider
    )

    import akka.http.scaladsl.server.Directives._
    val routes = new EndpointsWrapper(pathPrefix("chat")(chat.routes)).routing
    val server = new HttpServer("0.0.0.0", 8090, routes)

    IO { server.start() }
  }

  def start()(implicit t: Timer[IO]): IO[ExitCode] = {
    serveChatbot.unsafeRunAsyncAndForget()

    val app: Resource[IO, Server[IO]] =
      for {
        blocker <- Blocker[IO]
        server <- BlazeServerBuilder[IO]
                   .bindHttp(8080)
                   .bindHttp(config.app.bindPort, config.app.bindHost)
                   .withHttpApp(
                     CORS(
                       Router(
                         "/graphql" -> graphqlRoute.route,
                         "/faker"   -> Fakerendpoint.route(services),
                         "/assets"  -> fileService[IO](FileService.Config("/assets", blocker))
                       ).orNotFound
                     )
                   )
                   .resource
      } yield server

    app
      .use(_ => appLogger.info(s"Started server at ${config.app.bindHost}:${config.app.bindPort}").flatMap(_ => IO.never))
      .as(ExitCode.Success)
  }

}

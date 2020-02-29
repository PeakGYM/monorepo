package com.guys.coding.hackathon.backend

import cats.effect.Timer
import cats.effect.{ContextShift, IO}
import hero.common.logging.Logger
import hero.common.logging.slf4j.LoggingConfigurator
import com.guys.coding.hackathon.backend.infrastructure.slick.example.ExampleSchema
import com.guys.coding.hackathon.backend.infrastructure.slick.repo
import org.http4s.server.staticcontent._
import com.guys.coding.hackathon.backend.api.graphql.core.GraphqlRoute
import com.guys.coding.hackathon.backend.infrastructure.jwt.JwtTokenService
import hero.common.crypto.KeyReaders.{PrivateKeyReader, PublicKeyReader}
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.CORS
import org.http4s.syntax.kleisli._
import cats.implicits._
import cats.effect.Blocker
import cats.effect.Resource
import org.http4s.server.Server
import cats.effect.ExitCode
import scala.concurrent.ExecutionContext
import hero.common.util.LoggingExt
import com.guys.coding.hackathon.backend.infrastructure.slick.gym.GymSchema
import com.guys.coding.hackathon.backend.infrastructure.slick.gym.SlickGymRepository
import com.guys.coding.hackathon.backend.infrastructure.slick.training.SlickTrainingRepository
import com.guys.coding.hackathon.backend.infrastructure.slick.training.SlickExerciseRepository
import com.guys.coding.hackathon.backend.infrastructure.slick.training.ExerciseSchema
import com.guys.coding.hackathon.backend.infrastructure.slick.training.TrainingSchema
import com.guys.coding.hackathon.backend.infrastructure.slick.user.{
  SlickClientCoachCooperationRepository,
  SlickClientRepository,
  SlickCoachRepository,
  SlickMeasurementRepository
}

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
    TrainingSchema
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
  val measurementsRepository            = new SlickMeasurementRepository()

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

  def start()(implicit t: Timer[IO]): IO[ExitCode] = {

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
                         "/assets"  -> fileService[IO](FileService.Config("/assets", blocker))
                       ).orNotFound
                     )
                   )
                   .resource
      } yield server
    app.use(_ => appLogger.info(s"Started server at ${config.app.bindHost}:${config.app.bindPort}").flatMap(_ => IO.never)).as(ExitCode.Success)
  }

}

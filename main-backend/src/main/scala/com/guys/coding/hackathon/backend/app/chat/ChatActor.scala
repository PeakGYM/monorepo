package com.guys.coding.wwh.chat

import akka.persistence.{PersistentActor, RecoveryCompleted}
import com.typesafe.scalalogging.StrictLogging
import io.codeheroes.herochat.environment.facebook.{FacebookRequest, FacebookSenderId}
import scala.language.postfixOps

import scala.concurrent.ExecutionContext

import com.guys.coding.hackathon.backend.Services
import cats.data.OptionT
import cats.effect.IO

class ChatActor(
    id: FacebookSenderId,
    services: Services
) extends PersistentActor
    with StrictLogging {

  implicit val executionContext: ExecutionContext = context.system.dispatcher

  override def persistenceId: String = s"chat-${id.value}"

  override def receiveRecover: Receive = {
    case e: ChatEvent      => handleEvent(e)
    case RecoveryCompleted => // ignore
  }

  def handleEvent(e: ChatEvent): Unit = ()

  override def receiveCommand: Receive = initial

  private def nextWorkoutFor(clientId: String) =
    for {
      traiining <- OptionT(
                    services.trainingRepository
                      .getNextTraining(clientId)
                  )
      coachId <- OptionT.fromOption[IO](traiining.coachId)
      coach   <- OptionT(services.coachRepository.getCoachesById(List(coachId)).map(_.headOption))
    } yield (traiining, coach)

  def initial: Receive = {
    case FacebookRequest.Message(v) =>
      logger.info(s"received $v")
      logger.info(s"I AM [$id]")
      logger.info(s"I AM [$self]")
      sender ! Responses.helloMessage

    case FacebookRequest.QuickReply(r) =>
      sender ! (r match {
        case "GET_MAP"      => Responses.getMapResponse()
        case "NOT_EXERCISE" => Responses.looserResponse()
        case "OK_I_WILL" => Responses.helloMessage
        case "GET_NEXT_TRAINING" =>
          nextWorkoutFor("1").value.unsafeRunSync.map(Responses.getTrainingInfoResponse _ tupled).getOrElse(Responses.getTrainingNotFoundResponse())
      })

  }
}

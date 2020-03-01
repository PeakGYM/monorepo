package com.guys.coding.bitehack.chat

import akka.persistence.{PersistentActor, RecoveryCompleted}
import com.typesafe.scalalogging.StrictLogging
import io.codeheroes.herochat.environment.facebook.{FacebookRequest, FacebookSenderId}

import scala.concurrent.ExecutionContext

import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.domain.training.Training

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

  private def nextWorkoutFor(clientId: String): Option[Training] =
    services.trainingRepository
      .getNextTraining(clientId)
      .unsafeRunSync()

  def initial: Receive = {
    case FacebookRequest.Message(v) =>
      logger.info(s"received $v")
      logger.info(s"I AM [$id]")
      logger.info(s"I AM [$self]")
      sender ! Responses.helloMessage

    case FacebookRequest.QuickReply(r) =>
      sender ! (r match {
        case "GET_MAP"           => Responses.getMapResponse()
        case "NOT_EXERCISE"      => Responses.inProgress()
        case "GET_NEXT_TRAINING" => nextWorkoutFor("1").map(Responses.getTrainingInfoResponse).getOrElse(Responses.getTrainingNotFoundResponse())
      })

  }
}

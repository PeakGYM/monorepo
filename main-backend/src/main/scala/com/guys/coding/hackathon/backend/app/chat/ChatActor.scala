package com.guys.coding.bitehack.chat

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime, ZoneId}

import akka.actor.Status
import akka.persistence.{PersistentActor, RecoveryCompleted}
import cats.data.NonEmptyList
import cats.instances.future._
import com.typesafe.scalalogging.StrictLogging
import io.codeheroes.herochat.environment.facebook.service.FacebookService
import io.codeheroes.herochat.environment.facebook.{FacebookRequest, FacebookResponse, FacebookSenderId}
import java.util.Base64

import akka.Done

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}
import cats.data.OptionT
import scala.concurrent.Future
import com.guys.coding.hackathon.backend.Services

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

  def initial: Receive = {
    case FacebookRequest.Message(v) =>
      logger.info(s"received $v")
      logger.info(s"I AM [$id]")
      logger.info(s"I AM [$self]")
      sender ! Responses.helloMessage

    case FacebookRequest.Location(lat, lng) =>
      logger.info(s"lat $lat, lng $lng")
      sender ! Responses.ok

  }
}

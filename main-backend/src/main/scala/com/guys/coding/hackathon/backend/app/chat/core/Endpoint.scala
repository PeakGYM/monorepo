package com.guys.coding.wwh.api.core

import akka.http.scaladsl.server.{Directives, Route}
import akka.util.Timeout
import com.typesafe.scalalogging.StrictLogging
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.native.Serialization
import org.json4s.{DefaultFormats, Formats, Serialization}

import scala.concurrent.duration._
import scala.language.postfixOps

trait Endpoint extends Json4sSupport with Directives with StrictLogging {

  implicit protected val formats: Formats             = DefaultFormats ++ DomainFormatters.all
  implicit protected val serialization: Serialization = Serialization
  implicit protected val timeout: Timeout             = Timeout(60 seconds)

  def routing: Route

}

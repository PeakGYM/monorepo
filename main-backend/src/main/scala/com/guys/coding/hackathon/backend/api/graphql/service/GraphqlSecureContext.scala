package com.guys.coding.hackathon.backend.api.graphql.service

import com.typesafe.scalalogging.StrictLogging
import com.guys.coding.hackathon.backend.Token
import com.guys.coding.hackathon.backend.domain.AuthenticatedUser

import scala.concurrent.{ExecutionContext, Future}
import com.guys.coding.hackathon.backend.Services

case class GraphqlSecureContext(
    token: Option[Token],
    services: Services
)(implicit val ec: ExecutionContext)
    extends StrictLogging {

  private val authenticator = Authenticator(token, services.jwtTokenService)

  def authorized[T](fn: AuthenticatedUser => T): Future[T] =
    authorizedF(Future.successful[T] _ compose fn)

  def authorizedF[T](fn: AuthenticatedUser => Future[T]): Future[T] =
    authenticator.authorized.flatMap(fn)
}

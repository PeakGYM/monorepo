package com.guys.coding.hackathon.backend.infrastructure.jwt

import com.guys.coding.hackathon.backend.domain.AuthenticatedUser

import scala.language.postfixOps
import java.security.{PrivateKey, PublicKey}
import java.time.ZonedDateTime
import java.util.UUID

import com.guys.coding.hackathon.backend.Token

import scala.concurrent.duration._
import com.guys.coding.hackathon.backend.domain.TokenService
import scala.language.postfixOps
import scala.util.Try
import pdi.jwt.JwtAlgorithm
import pdi.jwt.JwtCirce
import pdi.jwt.JwtClaim
import com.guys.coding.hackathon.backend.domain.UserId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import io.circe.syntax._
import io.circe._
import com.guys.coding.hackathon.backend.domain.UserId.TrainerId
import hero.common.util.time.TimeUtils

class JwtTokenService(publicKey: PublicKey, privateKey: PrivateKey) extends TokenService {

  private val algorithm      = JwtAlgorithm.RS256
  private val TOKEN_DURATION = 30 days
  private val ISSUER         = "hackathon-backend"
  private val AUDIENCE       = "hackathon-backend"
  private val ROLE           = "ROLE"

  private val CLIENT  = "CLIENT"
  private val TRAINER = "TRAINER"

  def generateToken(userId: UserId): Token = {

    val (role, subject) = userId match {
      case ClientId(value)  => (CLIENT, value)
      case TrainerId(value) => (TRAINER, value)
    }

    val additionalClaims = Map(
      ROLE -> role
    )

    val issuedTime = ZonedDateTime.now.toEpochSecond
    val expiration =
      ZonedDateTime.now.plusSeconds(TOKEN_DURATION.toSeconds).toEpochSecond
    val claims = JwtClaim(
      issuer = Some(ISSUER),
      audience = Some(Set(AUDIENCE)),
      subject = Some(subject),
      expiration = Some(expiration),
      issuedAt = Some(issuedTime),
      jwtId = Some(UUID.randomUUID().toString.filter(_ != '-'))
    ) + additionalClaims.asJson.toString

    Token(JwtCirce.encode(claims, privateKey, algorithm))
  }

  def validateToken(accessToken: String): Try[AuthenticatedUser] = {

    JwtCirce.decode(accessToken, publicKey, Seq(algorithm)).map { claim =>
      if (!claim.issuer.forall(_ == ISSUER))
        throw new IllegalStateException(s"Invalid issuer [${claim.issuer}]")
      if (!claim.audience.forall(_.contains(AUDIENCE)))
        throw new IllegalStateException(
          s"Invalid audience [${claim.audience}]"
        )
      if (claim.issuedAt.isEmpty)
        throw new IllegalStateException(s"IssuedAt must be defined")

      val id = claim.subject.getOrElse(
        throw new IllegalStateException(s"Invalid subject [${claim.subject}]")
      )

      val additional: Map[String, String] =
        parser.decode[Map[String, String]](claim.content).getOrElse(throw new IllegalStateException("Invalid additional claims "))

      val userId = additional(ROLE) match {
        case `TRAINER` => TrainerId(id)
        case `CLIENT`  => ClientId(id)
      }

      AuthenticatedUser(userId, claim.issuedAt.map(TimeUtils.millisToZonedDateTime).get)
    }
  }
}

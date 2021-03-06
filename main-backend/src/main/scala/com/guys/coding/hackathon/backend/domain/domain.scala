package com.guys.coding.hackathon.backend.domain

import java.time.ZonedDateTime

sealed trait UserId

object UserId {
  case class ClientId(value: String) extends UserId
  case class CoachId(value: String)  extends UserId
}

sealed trait MeasurementId
object MeasurementId {
  case class MeasurementId(value: String)
}

case class AuthenticatedUser(id: UserId, isuedAt: ZonedDateTime)

case class Location(
    lat: Double,
    lng: Double
)

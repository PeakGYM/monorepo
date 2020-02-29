package com.guys.coding.hackathon.backend.api.graphql.schema.user

import java.util.UUID.randomUUID

import com.guys.coding.hackathon.backend.api.graphql.schema.CommonOutputTypes
import com.guys.coding.hackathon.backend.domain.MeasurementId.MeasurementId
import com.guys.coding.hackathon.backend.domain.UserId.{ClientId, CoachId}
import com.guys.coding.hackathon.backend.domain.user.{Client, ClientCoachCooperation, Coach, Measurement}
import hero.common.util.time.TimeUtils

object UserInputTypes extends CommonOutputTypes {
  case class ClientCoachCooperationGraphql(clientId: String, coachId: String) {
    def toDomain: ClientCoachCooperation =
      ClientCoachCooperation(
        id = randomUUID().toString,
        clientId = ClientId(clientId),
        coachId = CoachId(coachId)
      )
  }

  case class ClientGraphql(name: String, height: Int, pictureUrl: String) {
    def toDomain: Client =
      Client(
        id = ClientId(randomUUID().toString),
        name = name,
        height = height,
        pictureUrl = pictureUrl
      )
  }

  case class CoachGraphql(name: String, pictureUrl: String) {
    def toDomain: Coach =
      Coach(
        id = CoachId(randomUUID().toString),
        name = name,
        pictureUrl = pictureUrl
      )
  }

  case class MeasurementGraphql(
      clientId: String,
      timestamp: Long,
      weight: Option[Double],
      neck: Option[Double],
      leftBicep: Option[Double],
      rightBicep: Option[Double],
      leftForearm: Option[Double],
      rightForearm: Option[Double],
      chest: Option[Double],
      waist: Option[Double],
      hip: Option[Double],
      rightThigh: Option[Double],
      leftThigh: Option[Double],
      rightCalf: Option[Double],
      leftCalf: Option[Double]
  ) {
    def toDomain: Measurement =
      Measurement(
        MeasurementId(randomUUID().toString),
        ClientId(clientId),
        TimeUtils.millisToZonedDateTime(timestamp),
        weight,
        neck,
        leftBicep,
        rightBicep,
        leftForearm,
        rightForearm,
        chest,
        waist,
        hip,
        rightThigh,
        leftThigh,
        rightCalf,
        leftCalf
      )
  }

}

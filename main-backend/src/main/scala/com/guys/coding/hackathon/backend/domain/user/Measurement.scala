package com.guys.coding.hackathon.backend.domain.user

import java.time.ZonedDateTime

import com.guys.coding.hackathon.backend.domain.MeasurementId.MeasurementId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId

case class Measurement(
    id: MeasurementId,
    clientId: ClientId,
    timestamp: ZonedDateTime,
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
)

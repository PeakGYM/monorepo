package com.guys.coding.hackathon.backend.domain.user

import java.time.ZonedDateTime

import com.guys.coding.hackathon.backend.domain.MeasurementId.MeasurementId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId

case class Measurement(
    id: MeasurementId,
    clientId: ClientId,
    timestamp: ZonedDateTime,
    weight: Double,
    neck: Double,
    leftBicep: Double,
    rightBicep: Double,
    leftForearm: Double,
    rightForearm: Double,
    chest: Double,
    waist: Double,
    hip: Double,
    rightThigh: Double,
    leftThigh: Double,
    rightCalf: Double,
    leftCalf: Double
)

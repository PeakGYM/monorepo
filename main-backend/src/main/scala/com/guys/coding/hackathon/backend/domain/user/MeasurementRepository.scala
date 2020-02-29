package com.guys.coding.hackathon.backend.domain.user

import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import simulacrum.typeclass
@typeclass
trait MeasurementRepository[F[_]] {
  def insert(measurement: Measurement): F[Measurement]
  def getMostRecentFor(clientId: ClientId): F[Measurement] //handle possible null values?
  def getAllFor(clientId: ClientId): F[List[Measurement]]
}

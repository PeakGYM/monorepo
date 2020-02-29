package com.guys.coding.hackathon.backend.domain.user

import simulacrum.typeclass
@typeclass
trait MeasurementRepository[F[_]] {
  def insert(measurement: Measurement): F[Measurement]
  def getMostRecentFor(client: Client): F[Measurement] //handle possible null values?
  def getAllFor(client: Client): F[List[Measurement]]
}

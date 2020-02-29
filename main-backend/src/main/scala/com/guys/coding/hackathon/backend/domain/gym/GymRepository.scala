package com.guys.coding.hackathon.backend.domain.gym

import com.guys.coding.hackathon.backend.domain.Location
import simulacrum.typeclass

@typeclass
trait GymRepository[F[_]] {
  def insert(gym: Gym): F[Gym]
  def getInVicinity(location: Location): F[List[Gym]]
}

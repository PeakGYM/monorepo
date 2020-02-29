package com.guys.coding.hackathon.backend.domain.gym

import simulacrum.typeclass

@typeclass
trait GymRepository[F[_]] {
  def insert(gym: Gym): F[Gym]
}

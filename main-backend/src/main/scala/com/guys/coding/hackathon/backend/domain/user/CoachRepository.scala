package com.guys.coding.hackathon.backend.domain.user

import simulacrum.typeclass

@typeclass
trait CoachRepository[F[_]] {
  def insert(coach: Coach): F[Coach]
}

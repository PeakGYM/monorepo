package com.guys.coding.hackathon.backend.domain.user

import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import simulacrum.typeclass

@typeclass
trait CoachRepository[F[_]] {
  def insert(coach: Coach): F[Coach]
  def getCoachesById(ids: List[CoachId]): F[List[Coach]]
}

package com.guys.coding.hackathon.backend.domain.user

import com.guys.coding.hackathon.backend.domain.UserId.{ClientId, CoachId}
import simulacrum.typeclass
@typeclass
trait ClientCoachCooperationRepository[F[_]] {
  def insert(cooperation: ClientCoachCooperation): F[ClientCoachCooperation]
  def getClientsForCoach(coachId: CoachId): F[List[ClientId]]
  def getCoachesForClient(clientId: ClientId): F[List[CoachId]]
}

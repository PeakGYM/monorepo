package com.guys.coding.hackathon.backend.domain.user

import com.guys.coding.hackathon.backend.domain.UserId.{ClientId, CoachId}
import simulacrum.typeclass
@typeclass
trait ClientCoachCooperationRepository[F[_]] {
  def insert(cooperation: ClientCoachCooperation): F[ClientCoachCooperation]
  def getClientsForCoach(coach: Coach): F[List[ClientId]]
  def getCoachesForClient(client: Client): F[List[CoachId]]
}

package com.guys.coding.hackathon.backend.domain.user

import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import simulacrum.typeclass

@typeclass
trait ClientRepository[F[_]] {
  def insert(client: Client): F[Client]
  def getClientsById(ids: List[ClientId]): F[List[Client]]

}

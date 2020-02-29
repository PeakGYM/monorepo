package com.guys.coding.hackathon.backend.domain.user

import simulacrum.typeclass

@typeclass
trait ClientRepository[F[_]] {
  def insert(client: Client): F[Client]
}

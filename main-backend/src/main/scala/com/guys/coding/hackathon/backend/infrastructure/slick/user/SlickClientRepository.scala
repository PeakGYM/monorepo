package com.guys.coding.hackathon.backend.infrastructure.slick.user

import cats.effect.{ContextShift, IO}
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import com.guys.coding.hackathon.backend.domain.user.{Client, ClientRepository}
import com.guys.coding.hackathon.backend.infrastructure.slick.CustomJdbcTypes.ClientIdMap
import com.guys.coding.hackathon.backend.infrastructure.slick.repo
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.CatsIntegration
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import com.guys.coding.hackathon.backend.infrastructure.slick.user.ClientSchema._

import scala.concurrent.ExecutionContext

class SlickClientRepository(implicit db: Database, ec: ExecutionContext, cs: ContextShift[IO])
    extends repo.plain.CrudRepo[ClientId, ClientDTO, Clients](db, clients)
    with ClientRepository[IO]
    with CatsIntegration {

  override protected def id            = _.id
  override protected def DTOtoDomain   = identity
  override protected def toInsertedDTO = identity

  override def insert(client: Client): IO[Client] =
    runIO(for {
      _      <- insertAction(toDTO(client))
      result <- getFirstEntityByMatcherAction(_.id === client.id)
    } yield toDomain(result.get))

  def getCoachesById(ids: List[ClientId]): IO[List[Client]] =
    runIO(getEntriesAction(limit = Int.MaxValue, offset = 0, _.id inSet ids, None)).map(_.map(toDomain).toList)

}

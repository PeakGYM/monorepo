package com.guys.coding.hackathon.backend.infrastructure.slick.user

import cats.effect.{ContextShift, IO}
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.domain.user.{Coach, CoachRepository}
import com.guys.coding.hackathon.backend.infrastructure.slick.CustomJdbcTypes.CoachIdMap
import com.guys.coding.hackathon.backend.infrastructure.slick.repo
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.CatsIntegration
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import com.guys.coding.hackathon.backend.infrastructure.slick.user.CoachSchema._

import scala.concurrent.ExecutionContext

class SlickCoachRepository(implicit db: Database, ec: ExecutionContext, cs: ContextShift[IO])
    extends repo.plain.CrudRepo[CoachId, CoachDTO, Coaches](db, coaches)
    with CoachRepository[IO]
    with CatsIntegration {
  override protected def id            = _.id
  override protected def DTOtoDomain   = identity
  override protected def toInsertedDTO = identity

  override def insert(client: Coach): IO[Coach] =
    runIO(for {
      _      <- insertAction(toDTO(client))
      result <- getFirstEntityByMatcherAction(_.id === client.id)
    } yield toDomain(result.get))

  def getCoachesById(ids: List[CoachId]): IO[List[Coach]] = runIO(getEntriesAction(limit = Int.MaxValue, offset = 0, _.id inSet ids, None)).map(_.map(toDomain).toList)


}

package com.guys.coding.hackathon.backend.infrastructure.slick.user

import cats.effect.{ContextShift, IO}
import com.guys.coding.hackathon.backend.domain.UserId
import com.guys.coding.hackathon.backend.domain.UserId.{ClientId, CoachId}
import com.guys.coding.hackathon.backend.domain.user.{Client, ClientCoachCooperation, ClientCoachCooperationRepository, Coach}
import com.guys.coding.hackathon.backend.infrastructure.slick.CustomJdbcTypes.{ClientIdMap, CoachIdMap}
import com.guys.coding.hackathon.backend.infrastructure.slick.repo
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.CatsIntegration
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import com.guys.coding.hackathon.backend.infrastructure.slick.user.ClientCoachCooperationSchema._

import scala.concurrent.ExecutionContext

class SlickClientCoachCooperationRepository(implicit db: Database, ec: ExecutionContext, cs: ContextShift[IO])
    extends repo.plain.CrudRepo[String, ClientCoachCooperationDTO, ClientCoachCooperations](db, cooperations)
    with ClientCoachCooperationRepository[IO]
    with CatsIntegration {

  override protected def id            = _.id
  override protected def DTOtoDomain   = identity
  override protected def toInsertedDTO = identity

  override def insert(cooperation: ClientCoachCooperation): IO[ClientCoachCooperation] =
    runIO(for {
      _      <- insertAction(toDTO(cooperation))
      result <- getFirstEntityByMatcherAction(_.id === cooperation.id)
    } yield toDomain(result.get))

  override def getClientsForCoach(coachId: CoachId): IO[List[UserId.ClientId]] =
    runIO(
      getEntriesAction(limit = Int.MaxValue, offset = 0, _.coachId === coachId, None)
    ).map(coopDTO =>
      coopDTO
        .map(toDomain)
        .map(_.clientId)
    )

  override def getCoachesForClient(clientId: ClientId): IO[List[UserId.CoachId]] =
    runIO(
      getEntriesAction(limit = Int.MaxValue, offset = 0, _.clientId === clientId, None)
    ).map(coopDTO =>
      coopDTO
        .map(toDomain)
        .map(_.coachId)
    )
}

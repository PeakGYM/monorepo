package com.guys.coding.hackathon.backend.infrastructure.slick.gym

import cats.effect.IO
import com.guys.coding.hackathon.backend.domain.gym._
import com.guys.coding.hackathon.backend.infrastructure.slick.gym.GymSchema._
import com.guys.coding.hackathon.backend.infrastructure.slick.repo
import repo.CatsIntegration
import repo.profile.api._
import scala.concurrent.ExecutionContext
import cats.effect.{ContextShift, IO}
import com.guys.coding.hackathon.backend.domain.Location
import com.guys.coding.hackathon.backend.infrastructure.slick.PointFactory

class SlickGymRepository()(implicit db: Database, ec: ExecutionContext, cs: ContextShift[IO])
    extends repo.plain.CrudRepo[String, GymDTO, Gyms](db, gyms)
    with GymRepository[IO]
    with CatsIntegration {

  override protected def id            = _.id
  override protected def DTOtoDomain   = identity
  override protected def toInsertedDTO = identity

  import GymSchema._

  override def insert(gym: Gym): IO[Gym] =
    runIO(for {
      _      <- insertAction(toDTO(gym))
      result <- getFirstEntityByMatcherAction(_.id === gym.id.value)
    } yield toDomain(result.get))

  override def getInVicinity(location: Location): IO[List[Gym]] = {
    runIO {
      gyms.sortBy {
        case gym => gym.location <-> PointFactory.createPoint(location.lat, location.lng) asc
      }.result
    }.map(_.map(toDomain).toList)
  }

}

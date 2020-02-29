package com.guys.coding.hackathon.backend.infrastructure.slick.user

import java.time.ZonedDateTime

import cats.effect.{ContextShift, IO}
import com.guys.coding.hackathon.backend.domain.MeasurementId.MeasurementId
import com.guys.coding.hackathon.backend.infrastructure.slick.CustomJdbcTypes.MeasurementIdMap
import com.guys.coding.hackathon.backend.infrastructure.slick.CustomJdbcTypes.ClientIdMap
import com.guys.coding.hackathon.backend.domain.user.{Client, Measurement, MeasurementRepository}
import com.guys.coding.hackathon.backend.infrastructure.slick.repo
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.CatsIntegration
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import com.guys.coding.hackathon.backend.infrastructure.slick.user.MeasurementsSchema._
import hero.common.util.time.TimeUtils

import scala.concurrent.ExecutionContext

class SlickMeasurementRepository(implicit db: Database, ec: ExecutionContext, cs: ContextShift[IO])
    extends repo.plain.CrudRepo[MeasurementId, MeasurementDTO, Measurements](db, measurements)
    with MeasurementRepository[IO]
    with CatsIntegration {

  implicit val localDateOrdering: Ordering[ZonedDateTime] = Ordering.by(TimeUtils.zonedDateTimeToMillis)

  override protected def id            = _.id
  override protected def DTOtoDomain   = identity
  override protected def toInsertedDTO = identity

  override def insert(measurement: Measurement): IO[Measurement] =
    runIO(for {
      _      <- insertAction(toDTO(measurement))
      result <- getFirstEntityByMatcherAction(_.id === measurement.id)
    } yield toDomain(result.get))

  override def getAllFor(client: Client): IO[List[Measurement]] =
    runIO(getEntriesAction(limit = Int.MaxValue, offset = 0, _.clientId === client.id, None)).map(_.map(toDomain).toList)

  override def getMostRecentFor(client: Client): IO[Measurement] =
    runIO {
      measurements.sortBy(_.timestamp).take(1).result.head
    }.map(toDomain)
}

package com.guys.coding.hackathon.backend.infrastructure.slick.training

import cats.effect.IO
import com.guys.coding.hackathon.backend.infrastructure.slick.repo
import repo.CatsIntegration
import repo.profile.api._

import scala.concurrent.ExecutionContext
import cats.effect.{ContextShift, IO}
import java.time.ZonedDateTime

import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import hero.common.util.IdProvider
import com.guys.coding.hackathon.backend.domain.training.Training
import hero.common.util.time.TimeUtils

class SlickTrainingRepository()(implicit db: Database, ec: ExecutionContext, cs: ContextShift[IO])
    extends repo.plain.CrudRepo[String, TrainingSchema.TrainingDTO, TrainingSchema.Trainings](db, TrainingSchema.trainings)
    with CatsIntegration {

  implicit val localDateOrdering: Ordering[ZonedDateTime] = Ordering.by(TimeUtils.zonedDateTimeToMillis)

  override protected def id = _.id

  def getTrainigs(from: ZonedDateTime, to: ZonedDateTime, coachId: Option[CoachId], clientid: Option[ClientId]) = {
    val matchers = foldMatcher(_.dateFrom >= from, _.dateTo <= to)(true)(_ && _)
    def inmemmatcher(t: TrainingSchema.TrainingDTO) = {
      val co = coachId.isEmpty || t.coachId.contains(coachId.get)
      val cl = clientid.isEmpty || t.clientId == clientid.get
      co && cl
    }

    runIO(getEntriesAction(limit = Int.MaxValue, offset = 0, matchers, None)).map(_.filter(inmemmatcher).map(_.toDomain))
  }

  def getNextTraining(clientid: String): IO[Option[Training]] = {
    val matchers = foldMatcher(_.dateFrom >= ZonedDateTime.now())(true)(_ && _)
    def inmemmatcher(t: TrainingSchema.TrainingDTO) =
      t.clientId.value == clientid

    runIO(getEntriesAction(limit = Int.MaxValue, offset = 0, matchers, None))
      .map(_.filter(inmemmatcher).map(_.toDomain).sortBy(_.dateFrom).headOption)
  }

  def updateTraiing(training: Training): IO[Option[Training]] =
    runIO(upsertAction(TrainingSchema.trainingFromDomain(training))).map(_.map(_.toDomain))

  def addTraining(training: Training) =
    runIO(upsertAction(TrainingSchema.trainingFromDomain(training).copy(id = IdProvider.id.newId()))).map(_.map(_.toDomain))

  def getTraining(id: String) = runIO(getFirstEntityByMatcherAction(_.id === id)).map(_.map(_.toDomain))

}

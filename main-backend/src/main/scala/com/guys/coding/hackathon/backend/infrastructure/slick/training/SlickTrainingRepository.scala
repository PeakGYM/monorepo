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
import com.guys.coding.hackathon.backend.infrastructure.slick.training.TrainingSchema.Training

class SlickTrainingRepository()(implicit db: Database, ec: ExecutionContext, cs: ContextShift[IO]) // TODO:bcm
    extends repo.plain.CrudRepo[String, Training, TrainingSchema.Trainings](db, TrainingSchema.trainings)
    with CatsIntegration {

  override protected def id = _.id

  def getTrainigs(from: ZonedDateTime, to: ZonedDateTime, coachId: Option[CoachId], clientid: Option[ClientId]) = {

    val matchers = foldMatcher(_.dateFrom >= from, _.dateTo <= to)(true)(_ && _)

    def inmemmatcher(t: Training) = {

      val co = coachId.isEmpty || t.coach.contains(coachId.get)
      val cl = clientid.isEmpty || t.client == clientid.get

      co && cl
    }

    runIO(getEntriesAction(limit = Int.MaxValue, offset = 0, matchers, None)).map(_.filter(inmemmatcher))
  }

}

package com.guys.coding.hackathon.backend.infrastructure.slick.training

import cats.effect.IO
import com.guys.coding.hackathon.backend.infrastructure.slick.repo
import repo.CatsIntegration
import repo.profile.api._
import scala.concurrent.ExecutionContext
import cats.effect.{ContextShift, IO}
import com.guys.coding.hackathon.backend.domain.training.Exercise

class SlickExerciseRepository()(implicit db: Database, ec: ExecutionContext, cs: ContextShift[IO]) // TODO:bcm
    extends repo.plain.CrudRepo[String, Exercise, ExerciseSchema.Exercises](db, ExerciseSchema.exercises)
    with CatsIntegration {

  override protected def id = _.id

  def exercisesById(ids: List[String]): IO[List[Exercise]] = runIO(getEntriesAction(limit = Int.MaxValue, offset = 0, _.id inSet ids, None))
  def allExercises(): IO[List[Exercise]]                   = runIO(getEntriesAction(limit = Int.MaxValue, offset = 0))

}

package com.guys.coding.hackathon.backend.api.graphql.schema.gym

import com.guys.coding.hackathon.backend.api.graphql.schema.CommonOutputTypes

import sangria.macros.derive._
import sangria.schema._
import com.guys.coding.hackathon.backend.domain.training._
import sangria.execution.deferred.Fetcher
import cats.data.NonEmptyList
import scala.concurrent.Future
import sangria.execution.deferred.HasId
import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import com.guys.coding.hackathon.backend.infrastructure.slick.training.TrainingSchema.Training

object TrainingOutputTypes extends CommonOutputTypes {

  val exerciseFetcher = Fetcher.caching((ctx: GraphqlSecureContext, ids: Seq[String]) =>
    NonEmptyList.fromList(ids.toList) match {
      case None => Future.successful(Nil)
      case Some(nelIds) =>
        implicit val ec = ctx.ec

        ctx.services.exerciseRepository.exercisesById(nelIds.toList).unsafeToFuture().map { l =>
          val resolver = l.map(i => (i.id, Some(i))).toMap.withDefaultValue(None)

          nelIds.toList.map(id => (id, resolver(id)))
        }
    }
  )(HasId { case (id, _) => id })

  implicit val SeriesType: ObjectType[Unit, Series]     = deriveObjectType[Unit, Series]()
  implicit val ExerciseType: ObjectType[Unit, Exercise] = deriveObjectType[Unit, Exercise]()

  implicit val PlannedExercieType: ObjectType[GraphqlSecureContext, PlannedExercise] = deriveObjectType(
    ReplaceField(
      "exerciseId",
      Field(
        "exercise",
        OptionType(ExerciseType),
        resolve = c => {
          import c.ctx.ec
          DeferredValue(exerciseFetcher.defer(c.value.exerciseId)).map(_._2)
        }
      )
    )
  )

  implicit val TrainingType: ObjectType[GraphqlSecureContext, Training] = deriveObjectType(ObjectTypeName("Workout"))

}

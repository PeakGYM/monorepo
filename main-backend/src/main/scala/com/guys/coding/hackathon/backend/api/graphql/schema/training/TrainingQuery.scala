package com.guys.coding.hackathon.backend.api.graphql.schema.gym

import com.guys.coding.hackathon.backend.api.graphql.schema.QueryHolder
import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import sangria.schema._
import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import hero.common.util.time.TimeUtils

class TrainingQuery(services: Services) extends QueryHolder {

  val FromArg      = Argument("from", LongType)
  val ToArg        = Argument("to", LongType)
  val CoachIdArg   = Argument("coachId", OptionInputType(StringType))
  val ClientIdArg  = Argument("clientId", OptionInputType(StringType))
  val WorkoutIdArg = Argument("workoutId", StringType)

  override def queryFields(): List[Field[GraphqlSecureContext, Unit]] =
    fields[GraphqlSecureContext, Unit](
      Field(
        "workouts",
        ListType(TrainingOutputTypes.TrainingType),
        arguments = List(FromArg, ToArg, CoachIdArg, ClientIdArg),
        resolve = c =>
          c.ctx.authorizedF { _ =>
            // Should be taken from token

            services.trainingRepository
              .getTrainigs(
                from = TimeUtils.millisToZonedDateTime(c.arg(FromArg)),
                to = TimeUtils.millisToZonedDateTime(c.arg(ToArg)),
                coachId = c.arg(CoachIdArg).map(CoachId),
                clientid = c.arg(ClientIdArg).map(ClientId)
              )
              .unsafeToFuture()

          }
      ),
      Field(
        "workout",
        OptionType(TrainingOutputTypes.TrainingType),
        arguments = List(WorkoutIdArg),
        resolve = c =>
          c.ctx.authorizedF { _ =>
            // Should be taken from token

            services.trainingRepository
              .getTraining(c.arg(WorkoutIdArg))
              .unsafeToFuture()

          }
      ),
      Field(
        "exercises",
        ListType(TrainingOutputTypes.ExerciseType),
        arguments = Nil,
        resolve = c =>
          c.ctx.authorizedF { _ =>
            // Should be taken from token

            services.exerciseRepository
              .allExercises()
              .unsafeToFuture()

          }
      )
    )

}

package com.guys.coding.hackathon.backend.api.graphql.schema.gym

import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import sangria.schema._
import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.MutationHolder
import com.guys.coding.hackathon.backend.domain.training.Training
import com.guys.coding.hackathon.backend.infrastructure.slick.training.TrainingSchema.MuscleGroup
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import hero.common.util.time.TimeUtils
import hero.common.util.IdProvider
import com.guys.coding.hackathon.backend.infrastructure.Faker

class TrainingMutation(services: Services) extends MutationHolder {

  val TrainingArg = Argument("training", TrainingInputTypes.TrainingInputType)
  val FromArg     = Argument("from", LongType)
  val CoachIdArg  = Argument("coachId", OptionInputType(StringType))
  val ClientIdArg = Argument("clientId", StringType)

  override def mutationFields(): List[Field[GraphqlSecureContext, Unit]] =
    fields[GraphqlSecureContext, Unit](
      Field(
        "addWorkout",
        OptionType(TrainingOutputTypes.TrainingType),
        arguments = List(TrainingArg),
        resolve = c =>
          c.ctx.authorizedF { _ =>
            // Should be taken from token

            services.trainingRepository
              .addTraining(c.arg(TrainingArg).toDomain)
              .unsafeToFuture()

          }
      ),
      Field(
        "updateWorkout",
        OptionType(TrainingOutputTypes.TrainingType),
        arguments = List(TrainingArg),
        resolve = c =>
          c.ctx.authorizedF { _ =>
            // Should be taken from token
            services.trainingRepository
              .updateTraiing(c.arg(TrainingArg).toDomain)
              .unsafeToFuture()

          }
      ),
      Field(
        "addTestWorkout",
        OptionType(TrainingOutputTypes.TrainingType),
        arguments = List(FromArg, CoachIdArg,ClientIdArg), //bcm
        resolve = c =>
          c.ctx.authorizedF { _ =>
            val from = TimeUtils.millisToZonedDateTime(c.arg(FromArg))

            val id = IdProvider.id.newId()

            val training = Training(
              id =  id,
              name = "BestTraining",
              muscleGroup = MuscleGroup.Back :: Nil,
              coachId = c.arg(CoachIdArg).map(CoachId),
              clientId = ClientId(c.arg(ClientIdArg)),
              dateFrom = from,
              dateTo = from.plusHours(1),
              exercises = (1 to 3).map(_ => Faker.plannedEx(id)).toList,
              inperson = true
            )
            // Should be taken from token
            services.trainingRepository
              .updateTraiing(training)
              .unsafeToFuture()

          }
      )
    )

}

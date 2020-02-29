package com.guys.coding.hackathon.backend.api.graphql.schema.gym

import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import sangria.schema._
import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.MutationHolder

class TrainingMutation(services: Services) extends MutationHolder {

  val TrainingArg = Argument("training", TrainingInputTypes.TrainingInputType)

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
      )
    )

}

package com.guys.coding.hackathon.backend.api.graphql.schema.user
import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.MutationHolder
import com.guys.coding.hackathon.backend.api.graphql.schema.user.UserInputTypes.CoachGraphql
import com.guys.coding.hackathon.backend.api.graphql.schema.user.UserOutputTypes._
import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import sangria.schema.{fields, Argument, Field, StringType}

class CoachMutation(services: Services) extends MutationHolder {
  val CoachNameArg       = Argument("coachName", StringType)
  val CoachPictureUrlArg = Argument("coachPictureUrl", StringType)

  override def mutationFields(): List[Field[GraphqlSecureContext, Unit]] =
    fields[GraphqlSecureContext, Unit](
      Field(
        "addCoach",
        CoachType,
        arguments = List(CoachNameArg, CoachPictureUrlArg),
        resolve = c =>
          c.ctx.authorizedF { _ =>
            services.coachRepository
              .insert(CoachGraphql(c.arg(CoachNameArg), c.arg(CoachPictureUrlArg)).toDomain)
              .unsafeToFuture()
          }
      )
    )
}

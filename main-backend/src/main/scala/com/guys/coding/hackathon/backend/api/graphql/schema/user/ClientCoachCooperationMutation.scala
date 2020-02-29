package com.guys.coding.hackathon.backend.api.graphql.schema.user

import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.MutationHolder
import com.guys.coding.hackathon.backend.api.graphql.schema.user.UserInputTypes.ClientCoachCooperationGraphql
import com.guys.coding.hackathon.backend.api.graphql.schema.user.UserOutputTypes._
import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import sangria.schema.{Argument, Field, StringType, fields}

class ClientCoachCooperationMutation(services: Services) extends MutationHolder {
  val CoachIdArg  = Argument("coachId", StringType)
  val ClientIdArg = Argument("clientId", StringType)

  override def mutationFields(): List[Field[GraphqlSecureContext, Unit]] =
    fields[GraphqlSecureContext, Unit](
      Field(
        "addCooperation",
        ClientCoachCooperationType,
        arguments = List(CoachIdArg, ClientIdArg),
        resolve = c =>
          c.ctx.authorizedF { _ =>
            services.clientCoachCooperationRepository
              .insert(ClientCoachCooperationGraphql(c.arg(ClientIdArg), c.arg(CoachIdArg)).toDomain)
              .unsafeToFuture()
          }
      )
    )
}

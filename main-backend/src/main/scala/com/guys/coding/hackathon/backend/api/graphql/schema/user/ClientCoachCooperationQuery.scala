package com.guys.coding.hackathon.backend.api.graphql.schema.user

import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.QueryHolder
import com.guys.coding.hackathon.backend.api.graphql.schema.user.UserOutputTypes._
import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import com.guys.coding.hackathon.backend.domain.UserId.{ClientId, CoachId}
import sangria.schema.{Argument, Field, ListType, StringType, fields}

class ClientCoachCooperationQuery(services: Services) extends QueryHolder {
  val CoachIdArg  = Argument("coachId", StringType)
  val ClientIdArg = Argument("clientId", StringType)

  override def queryFields(): List[Field[GraphqlSecureContext, Unit]] =
    fields[GraphqlSecureContext, Unit](
      Field(
        "clientsForCoach",
        ListType(ClientIdType),
        arguments = List(CoachIdArg),
        resolve = c => c.ctx.authorizedF { _ =>

            services.clientCoachCooperationRepository
                .getClientsForCoach(CoachId(c.arg(CoachIdArg)))
              .unsafeToFuture()
          }
      ),
      Field(
        "coachesForClient",
        ListType(CoachIdType),
        arguments = List(ClientIdArg),
        resolve = c => c.ctx.authorizedF { _ =>

          services.clientCoachCooperationRepository
            .getCoachesForClient(ClientId(c.arg(ClientIdArg)))
            .unsafeToFuture()
        }
      )
    )



}

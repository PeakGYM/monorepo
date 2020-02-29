package com.guys.coding.hackathon.backend.api.graphql.schema.user
import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.QueryHolder
import com.guys.coding.hackathon.backend.api.graphql.schema.user.UserOutputTypes._
import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import sangria.schema.{Argument, Field, ListInputType, ListType, StringType, fields}

class CoachQuery(services: Services) extends QueryHolder {
  val CoachIdsArg = Argument("ids", ListInputType(StringType))

  override def queryFields(): List[Field[GraphqlSecureContext, Unit]] =
    fields[GraphqlSecureContext, Unit](
      Field(
        "getCoachesByIds",
        ListType(CoachType),
        arguments = List(CoachIdsArg),
        resolve = c =>
          c.ctx.authorizedF { _ =>
            services.coachRepository
              .getCoachesById(c.arg(CoachIdsArg).map(CoachId).toList)
              .unsafeToFuture()
          }
      )
    )
}

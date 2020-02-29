package com.guys.coding.hackathon.backend.api.graphql.schema.user

import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.QueryHolder
import com.guys.coding.hackathon.backend.api.graphql.schema.user.UserOutputTypes._
import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import sangria.schema.{Argument, Field, ListType, StringType, fields}

class MeasurementQuery(services: Services) extends QueryHolder {
  val ClientIdArg = Argument("clientId", StringType)

  override def queryFields(): List[Field[GraphqlSecureContext, Unit]] =
    fields[GraphqlSecureContext, Unit](
      Field(
        "getMostRecentFor",
        MeasurementType,
        arguments = List(ClientIdArg),
        resolve = c =>
          c.ctx.authorizedF { _ =>
            services.measurementsRepository
              .getMostRecentFor(ClientId(c.arg(ClientIdArg)))
              .unsafeToFuture()
          }
      ),
      Field(
        "getAllFor",
        ListType(MeasurementType),
        arguments = List(ClientIdArg),
        resolve = c =>
          c.ctx.authorizedF { _ =>
            services.measurementsRepository
              .getAllFor(ClientId(c.arg(ClientIdArg)))
              .unsafeToFuture()
          }
      )
    )
}

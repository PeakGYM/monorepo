package com.guys.coding.hackathon.backend.api.graphql.schema.user
import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.QueryHolder
import com.guys.coding.hackathon.backend.api.graphql.schema.user.UserOutputTypes._
import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import sangria.schema.{Argument, Field, ListInputType, ListType, StringType, fields}

class ClientQuery(services: Services) extends QueryHolder {
  val ClientIdsArg = Argument("ids", ListInputType(StringType))

  override def queryFields(): List[Field[GraphqlSecureContext, Unit]] =
    fields[GraphqlSecureContext, Unit](
      Field(
        "getClientsByIds",
        ListType(ClientType),
        arguments = List(ClientIdsArg),
        resolve = c =>
          c.ctx.authorizedF { _ =>
            services.clientRepository
              .getClientsById(c.arg(ClientIdsArg).map(ClientId).toList)
              .unsafeToFuture()
          }
      )
    )
}

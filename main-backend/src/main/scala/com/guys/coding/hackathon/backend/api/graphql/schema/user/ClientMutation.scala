package com.guys.coding.hackathon.backend.api.graphql.schema.user
import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.MutationHolder
import com.guys.coding.hackathon.backend.api.graphql.schema.user.UserInputTypes._
import com.guys.coding.hackathon.backend.api.graphql.schema.user.UserOutputTypes._
import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import sangria.schema.{fields, Argument, Field, IntType, StringType}

class ClientMutation(services: Services) extends MutationHolder {
  val ClientNameArg       = Argument("clientName", StringType)
  val ClientHeightArg     = Argument("clientHeight", IntType)
  val ClientPictureUrlArg = Argument("clientPictureUrl", StringType)

  override def mutationFields(): List[Field[GraphqlSecureContext, Unit]] =
    fields[GraphqlSecureContext, Unit](
      Field(
        "addClient",
        ClientType,
        arguments = List(ClientNameArg, ClientHeightArg, ClientPictureUrlArg),
        resolve = c =>
          c.ctx.authorizedF { _ =>
            services.clientRepository
              .insert(ClientGraphql(c.arg(ClientNameArg), c.arg(ClientHeightArg), c.arg(ClientPictureUrlArg)).toDomain)
              .unsafeToFuture()
          }
      )
    )
}

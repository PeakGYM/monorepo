package com.guys.coding.hackathon.backend.api.graphql.schema.example

import cats.effect.IO
import com.guys.coding.hackathon.backend.api.graphql.schema.QueryHolder
import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import com.guys.coding.hackathon.backend.domain.ExampleService
import hero.common.sangria.pagination.{PaginationArgs, PaginationTypes}
import sangria.schema._
import com.guys.coding.hackathon.backend.domain.ExampleService
import cats.effect.IO

class ExampleQuery(exampleService: ExampleService[IO]) extends QueryHolder {

  import PaginationArgs._
  import PaginationTypes._

  override def queryFields(): List[Field[GraphqlSecureContext, Unit]] =
    fields[GraphqlSecureContext, Unit](
      Field(
        "bestShowsEver",
        PaginatedType(
          ofType = NodeType[String](StringType, name = "Example"),
          "Example"
        ),
        arguments = PageArg :: EntriesPerPageArg :: Nil,
        resolve = ctx =>
          ctx.ctx.authorizedF { _ =>
            val entriesPerPage = ctx.arg(EntriesPerPageArg)

            exampleService.getBestShowEver
              .map(show =>
                Paginated(
                  entities = (1 to entriesPerPage).map(_ => Node(show)).toList,
                  3
                )
              )
              .unsafeToFuture()
          }
      )
    )
}

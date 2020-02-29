package com.guys.coding.hackathon.backend.api.graphql

import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.MutationHolder
import sangria.schema.ObjectType
import com.guys.coding.hackathon.backend.api.graphql.schema.example.ExampleMutation
import com.guys.coding.hackathon.backend.domain.ExampleService
import cats.effect.IO

class Mutation(services: Services) {

  private val mutationHolders =
    List[MutationHolder](
      new ExampleMutation(new ExampleService[IO] {})
    )

  val MutationType = ObjectType(
    name = "Mutation",
    fields = mutationHolders.flatMap(_.mutationFields())
  )
}

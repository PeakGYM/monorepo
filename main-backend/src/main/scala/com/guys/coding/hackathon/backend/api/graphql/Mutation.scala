package com.guys.coding.hackathon.backend.api.graphql

import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.MutationHolder
import sangria.schema.ObjectType

class Mutation(services: Services) {

  private val mutationHolders =
    List[MutationHolder](
      )

  val MutationType = ObjectType(
    name = "Mutation",
    fields = mutationHolders.flatMap(_.mutationFields())
  )
}

package com.guys.coding.hackathon.backend.api.graphql

import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.QueryHolder
import com.guys.coding.hackathon.backend.api.graphql.schema.example.ExampleQuery
import sangria.schema.ObjectType
import com.guys.coding.hackathon.backend.api.graphql.schema.gym.GymQuery

class Query(services: Services) {

  private val queryHolders = List[QueryHolder](
    new GymQuery(services.gymRepository)
  )

  val QueryType = ObjectType(
    name = "Query",
    fields = queryHolders.flatMap(_.queryFields())
  )
}

package com.guys.coding.hackathon.backend.api.graphql

import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.QueryHolder
import sangria.schema.ObjectType
import com.guys.coding.hackathon.backend.api.graphql.schema.gym.GymQuery
import com.guys.coding.hackathon.backend.api.graphql.schema.gym.TrainingQuery

class Query(services: Services) {

  private val queryHolders = List[QueryHolder](
    new GymQuery(services.gymRepository),
    new TrainingQuery(services)
  )

  val QueryType = ObjectType(
    name = "Query",
    fields = queryHolders.flatMap(_.queryFields())
  )
}

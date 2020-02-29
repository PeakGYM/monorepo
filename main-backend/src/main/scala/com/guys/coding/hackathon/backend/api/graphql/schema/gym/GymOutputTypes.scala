package com.guys.coding.hackathon.backend.api.graphql.schema.gym

import com.guys.coding.hackathon.backend.api.graphql.core.GraphQLContext
import com.guys.coding.hackathon.backend.api.graphql.schema.CommonOutputTypes
import sangria.macros.derive._
import sangria.schema._
import com.guys.coding.hackathon.backend.domain.gym._

object GymOutputTypes extends CommonOutputTypes {

  implicit val GymType: ObjectType[GraphQLContext, Gym] =
    deriveObjectType[GraphQLContext, Gym](
      AddFields(
        Field("coaches", ListType(CoachType), resolve = c => c.ctx.getCoaches(c.value.coachIds))
      )
    )

}

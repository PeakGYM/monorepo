package com.guys.coding.hackathon.backend.api.graphql.schema.gym

import com.guys.coding.hackathon.backend.api.graphql.schema.CommonOutputTypes
import sangria.macros.derive._
import sangria.schema._
import com.guys.coding.hackathon.backend.domain.gym._
import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext

object GymOutputTypes extends CommonOutputTypes {

  implicit val GymType: ObjectType[GraphqlSecureContext, Gym] =
    deriveObjectType[GraphqlSecureContext, Gym](
      AddFields(
        Field("coaches", ListType(CoachType), resolve = c => c.ctx.services.coachRepository.getCoachesById(c.value.coachIds).unsafeToFuture())
      )
    )

}

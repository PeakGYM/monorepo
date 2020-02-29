package com.guys.coding.hackathon.backend.api.graphql.schema.gym

import cats.effect.IO
import com.guys.coding.hackathon.backend.api.graphql.schema.QueryHolder
import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import com.guys.coding.hackathon.backend.domain.gym.GymRepository
import com.guys.coding.hackathon.backend.domain.Location
import sangria.schema._
import cats.effect.IO

class GymQuery(gymRepository: GymRepository[IO]) extends QueryHolder {

  import GymOutputTypes._

  val LatArg = Argument("lat", StringType)
  val LngArg = Argument("lng", StringType)

  override def queryFields(): List[Field[GraphqlSecureContext, Unit]] =
    fields[GraphqlSecureContext, Unit](
      Field(
        "gyms",
        ListType(GymType),
        arguments = LatArg :: LngArg :: Nil,
        resolve = c =>
          c.ctx.authorizedF { _ =>
            val location = Location(c.arg(LatArg), c.arg(LngArg))

            gymRepository
              .getInVicinity(location)
              .unsafeToFuture()
          }
      )
    )
}

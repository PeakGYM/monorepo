package com.guys.coding.hackathon.backend.api.graphql.schema.gym

import com.guys.coding.hackathon.backend.api.graphql.schema.CommonOutputTypes

import sangria.macros.derive._
import sangria.schema._
import com.guys.coding.hackathon.backend.domain.gym._

object GymOutputTypes extends CommonOutputTypes {

  implicit val GymCoachType: ObjectType[Unit, GymCoach] = deriveObjectType[Unit, GymCoach]()

  implicit val GymType: ObjectType[Unit, Gym] = deriveObjectType[Unit, Gym]()

}

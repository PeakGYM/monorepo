package com.guys.coding.hackathon.backend.api.graphql.schema.user

import com.guys.coding.hackathon.backend.api.graphql.schema.CommonOutputTypes
import com.guys.coding.hackathon.backend.domain.user.{Client, ClientCoachCooperation, Measurement}
import sangria.macros.derive.deriveObjectType
import sangria.schema.ObjectType

object UserOutputTypes extends CommonOutputTypes{

  implicit val ClientCoachCooperationType: ObjectType[Unit, ClientCoachCooperation]   = deriveObjectType[Unit, ClientCoachCooperation]()
  implicit val ClientType: ObjectType[Unit, Client]   = deriveObjectType[Unit, Client]()
  implicit val MeasurementType: ObjectType[Unit, Measurement]   = deriveObjectType[Unit, Measurement]()


}

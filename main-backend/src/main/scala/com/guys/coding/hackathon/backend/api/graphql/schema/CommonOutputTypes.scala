package com.guys.coding.hackathon.backend.api.graphql.schema

import sangria.macros.derive._
import sangria.schema._
import com.guys.coding.hackathon.backend.domain.Location
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.domain.gym.GymId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import java.time.ZonedDateTime
import hero.common.util.time.TimeUtils

trait CommonOutputTypes {

  implicit val ZonedDateTimeType: ScalarAlias[ZonedDateTime, Long] =
    ScalarAlias(LongType, TimeUtils.zonedDateTimeToMillis, value => Right(TimeUtils.millisToZonedDateTime(value)))

  implicit val ClientIdType: ScalarAlias[ClientId, String] =
    ScalarAlias[ClientId, String](StringType, _.value, value => Right(ClientId(value)))

  implicit val CoachIdType: ScalarAlias[CoachId, String] =
    ScalarAlias[CoachId, String](StringType, _.value, value => Right(CoachId(value)))

  implicit val GymIdType: ScalarAlias[GymId, String] =
    ScalarAlias[GymId, String](StringType, _.value, value => Right(GymId(value)))

  implicit val LocationType: ObjectType[Unit, Location] = deriveObjectType[Unit, Location]()

}

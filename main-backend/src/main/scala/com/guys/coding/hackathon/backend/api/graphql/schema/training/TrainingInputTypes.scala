package com.guys.coding.hackathon.backend.api.graphql.schema.gym

import com.guys.coding.hackathon.backend.api.graphql.schema.CommonOutputTypes

import sangria.macros.derive._
import sangria.schema._
import com.guys.coding.hackathon.backend.domain.training._
import io.circe.Decoder
import io.circe.generic.semiauto._
import com.guys.coding.hackathon.backend.infrastructure.slick.training.TrainingSchema
import sangria.macros.derive._
import sangria.marshalling.FromInput
import sangria.schema._
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import hero.common.util.time.TimeUtils
import hero.common.sangria.FromInputProvider

object TrainingInputTypes extends CommonOutputTypes {

  case class TrainingGraphql(
      id: String,
      name: String,
      muscleGroup: List[String],
      coachId: Option[String],
      clientId: String,
      dateFrom: Long,
      dateTo: Long,
      inperson: Boolean
  ) {
    def toDomain =
      Training(
        id = id,
        name = name,
        muscleGroup = muscleGroup.map(TrainingSchema.muscleGroupToDomain),
        coachId = coachId.map(CoachId),
        clientId = ClientId(clientId),
        dateFrom = TimeUtils.millisToZonedDateTime(dateFrom),
        dateTo = TimeUtils.millisToZonedDateTime(dateTo),
        inperson = inperson
      )
  }

  implicit val SeriesDecoder: Decoder[Series]                   = deriveDecoder
  implicit val PlannedExerciseDecoder: Decoder[PlannedExercise] = deriveDecoder
  implicit val TrainingDecoder: Decoder[TrainingGraphql]        = deriveDecoder

  implicit val SeriesFromInput: FromInput[Series]                   = FromInputProvider.fromInputForType[Series]
  implicit val PlannedExerciseFromInput: FromInput[PlannedExercise] = FromInputProvider.fromInputForType[PlannedExercise]
  implicit val TrainingFromInput: FromInput[TrainingGraphql]        = FromInputProvider.fromInputForType[TrainingGraphql]

  implicit val SeriesInputType: InputObjectType[Series]                  = deriveInputObjectType[Series](InputObjectTypeName("SeriesInput"))
  implicit val PlannedExercieInputType: InputObjectType[PlannedExercise] = deriveInputObjectType(InputObjectTypeName("PlannedExerciseInput"))

  implicit val TrainingInputType: InputObjectType[TrainingGraphql] = deriveInputObjectType(InputObjectTypeName("WorkoutInput"))

}

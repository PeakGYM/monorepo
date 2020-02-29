package com.guys.coding.hackathon.backend.infrastructure.slick

import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.DtoMappings
import slick.lifted.{TableQuery, Tag}
import java.time.ZonedDateTime

import com.guys.coding.hackathon.backend.domain.MeasurementId.MeasurementId
import com.guys.coding.hackathon.backend.domain.PictureId.PictureId
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import slick.jdbc.JdbcType

import io.circe.syntax._
import io.circe.generic.semiauto._
import com.guys.coding.hackathon.backend.domain.training._
import io.circe.Json
import slick.ast.BaseTypedType

object CustomJdbcTypes {
  implicit val ClientIdMap: JdbcType[ClientId] = MappedColumnType.base[ClientId, String](_.value, ClientId)
  implicit val CoachIdMap: JdbcType[CoachId]   = MappedColumnType.base[CoachId, String](_.value, CoachId)
  implicit val PictureIdMap: JdbcType[PictureId] = MappedColumnType.base[PictureId, String](_.value, PictureId)
  implicit val MeasurementIdMap: JdbcType[MeasurementId] = MappedColumnType.base[MeasurementId, String](_.value, MeasurementId)

  implicit val seriesDecoder         = deriveDecoder[Series]
  implicit val seriesEncoder         = deriveEncoder[Series]
  def seriesToJson: Series => Json   = _.asJson
  def seriesFromJson: Json => Series = _.as[Series].toOption.get
  implicit val SeriesMapper: JdbcType[Series] with BaseTypedType[Series] =
    MappedColumnType.base[Series, Json](seriesToJson, seriesFromJson)

  implicit val palnnedExerciseDecoder                  = deriveDecoder[PlannedExercise]
  implicit val palnnedExerciseEncoder                  = deriveEncoder[PlannedExercise]
  def palnnedExerciseToJson: PlannedExercise => Json   = _.asJson
  def palnnedExerciseFromJson: Json => PlannedExercise = _.as[PlannedExercise].toOption.get
  implicit val PalnnedExerciseMapper: JdbcType[PlannedExercise] with BaseTypedType[PlannedExercise] =
    MappedColumnType.base[PlannedExercise, Json](palnnedExerciseToJson, palnnedExerciseFromJson)

}

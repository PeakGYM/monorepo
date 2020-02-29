package com.guys.coding.hackathon.backend.infrastructure.slick

import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import slick.jdbc.JdbcType

import io.circe.syntax._
import io.circe.generic.semiauto._
import com.guys.coding.hackathon.backend.domain.training.Series
import io.circe.Json
import slick.ast.BaseTypedType

object CustomJdbcTypes {
  implicit val ClientIdMap: JdbcType[ClientId] = MappedColumnType.base[ClientId, String](_.value, ClientId)
  implicit val CoachIdMap: JdbcType[CoachId]   = MappedColumnType.base[CoachId, String](_.value, CoachId)

  implicit val seriesDecoder = deriveDecoder[Series]
  implicit val seriesEncoder = deriveEncoder[Series]

  def seriesToJson: Series => Json   = _.asJson
  def seriesFromJson: Json => Series = _.as[Series].toOption.get

  implicit val SeriesMapper: JdbcType[Series] with BaseTypedType[Series] =
    MappedColumnType.base[Series, Json](seriesToJson, seriesFromJson)

}

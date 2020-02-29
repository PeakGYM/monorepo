package com.guys.coding.hackathon.backend.infrastructure.slick

import com.github.tminglei.slickpg.{ExPostgresProfile, PgCirceJsonSupport, PgPostGISSupport}
import io.circe.{Decoder, Encoder, Json}
import slick.jdbc.JdbcType
import io.circe.Encoder.encodeList
import io.circe.syntax._

trait HackathonBackendPostgresProfile extends ExPostgresProfile with PgPostGISSupport with PgCirceJsonSupport {

  override def pgjson: String = "jsonb" // jsonb support is in postgres 9.4.0 onward; for 9.3.x use "json"

  trait API extends super.API with PostGISImplicits with PostGISAssistants with JsonImplicits {
    implicit def arrayJsonMap[T](implicit encoder: Encoder[T], decoder: Decoder[T]): JdbcType[List[T]] = {
      MappedColumnType.base[List[T], Json](
        _.asJson(encodeList(encoder)),
        json =>
          json.as[List[T]](Decoder.decodeList(decoder)) match {
            case Left(error)    => throw new IllegalStateException(s"Json list deserialization failed due to error: [$error]")
            case Right(entries) => entries
          }
      )
    }

  }

  override val api: API = new API {}

  val plainAPI = new API with CirceJsonPlainImplicits

}

object HackathonBackendPostgresProfile extends HackathonBackendPostgresProfile

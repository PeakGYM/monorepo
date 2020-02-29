package com.guys.coding.hackathon.backend.infrastructure.slick
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.DtoMappings
import slick.lifted.{TableQuery, Tag}
import java.time.ZonedDateTime
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import slick.jdbc.JdbcType

object CustomJdbcTypes {
  implicit val ClientIdMap: JdbcType[ClientId] = MappedColumnType.base[ClientId, String](_.value, ClientId)
  implicit val CoachIdMap: JdbcType[CoachId]   = MappedColumnType.base[CoachId, String](_.value, CoachId)

}

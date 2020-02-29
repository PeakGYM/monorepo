package com.guys.coding.hackathon.backend.infrastructure.slick.user

import com.guys.coding.hackathon.backend.domain.PictureId.PictureId
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import slick.jdbc.JdbcType
import slick.lifted.{TableQuery, Tag}

object CoachSchema extends SlickSchemas {
  implicit val CoachIdMap: JdbcType[CoachId]     = MappedColumnType.base[CoachId, String](_.value, CoachId)
  implicit val PictureIdMap: JdbcType[PictureId] = MappedColumnType.base[PictureId, String](_.value, PictureId)

  case class CoachDTO(
      id: CoachId,
      name: String,
      pictureId: PictureId
  )

  override def schemas = List(clients)

  val clients = TableQuery[Coaches]

  class Coaches(tag: Tag) extends Table[CoachDTO](tag, "coach") {

    def id        = column[CoachId]("id", O.PrimaryKey)
    def name      = column[String]("name")
    def pictureId = column[PictureId]("pictureId")

    override def * =
      (
        id,
        name,
        pictureId
      ) <> (CoachDTO.tupled, CoachDTO.unapply)
  }

}

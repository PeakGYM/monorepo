package com.guys.coding.hackathon.backend.infrastructure.slick.user

import com.guys.coding.hackathon.backend.domain.PictureId.PictureId
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.infrastructure.slick.CustomJdbcTypes.{CoachIdMap, PictureIdMap}
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import slick.lifted.{TableQuery, Tag}

object CoachSchema extends SlickSchemas {
  case class CoachDTO(
      id: CoachId,
      name: String,
      pictureId: PictureId
  )

  override def schemas = List(coaches)

  val coaches = TableQuery[Coaches]

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

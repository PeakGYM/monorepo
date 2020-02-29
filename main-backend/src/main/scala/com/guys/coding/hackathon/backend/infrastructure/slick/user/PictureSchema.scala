package com.guys.coding.hackathon.backend.infrastructure.slick.user

import com.guys.coding.hackathon.backend.domain.PictureId.PictureId
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import slick.jdbc.JdbcType
import slick.lifted.{TableQuery, Tag}

object PictureSchema extends SlickSchemas {
  implicit val PictureIdMap: JdbcType[PictureId] = MappedColumnType.base[PictureId, String](_.value, PictureId)
  case class PictureDTO(
      id: PictureId,
      uri: String
  )

  override def schemas = List(pictures)

  val pictures = TableQuery[Pictures]

  class Pictures(tag: Tag) extends Table[PictureDTO](tag, "picture") {

    def id  = column[PictureId]("id", O.PrimaryKey)
    def uri = column[String]("uri")

    override def * =
      (
        id,
        uri
      ) <> (PictureDTO.tupled, PictureDTO.unapply)
  }

}

package com.guys.coding.hackathon.backend.infrastructure.slick.user

import com.guys.coding.hackathon.backend.domain.PictureId.PictureId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import slick.jdbc.JdbcType
import slick.lifted.{TableQuery, Tag}

object ClientSchema extends SlickSchemas {
  implicit val ClientIdMap: JdbcType[ClientId]   = MappedColumnType.base[ClientId, String](_.value, ClientId)
  implicit val PictureIdMap: JdbcType[PictureId] = MappedColumnType.base[PictureId, String](_.value, PictureId)

  case class ClientDTO(
      id: ClientId,
      name: String,
      height: Int, // height in cm
      pictureId: PictureId
  )

  override def schemas = List(clients)

  val clients = TableQuery[Clients]

  class Clients(tag: Tag) extends Table[ClientDTO](tag, "client") {

    def id        = column[ClientId]("id", O.PrimaryKey)
    def name      = column[String]("name")
    def height    = column[Int]("height")
    def pictureId = column[PictureId]("pictureId")

    override def * =
      (
        id,
        name,
        height,
        pictureId
      ) <> (ClientDTO.tupled, ClientDTO.unapply)
  }

}

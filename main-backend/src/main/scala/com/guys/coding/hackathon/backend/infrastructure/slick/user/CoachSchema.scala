package com.guys.coding.hackathon.backend.infrastructure.slick.user

import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import slick.jdbc.JdbcType
import slick.lifted.{TableQuery, Tag}

class CoachSchema extends SlickSchemas {
  implicit val CoachIdMap: JdbcType[CoachId] = MappedColumnType.base[CoachId, String](_.value, CoachId)

  case class CoachDTO(
                         id: CoachId,
                         name: String,
                         height: Int // height in cm
  )

  override def schemas = List(clients)

  val clients = TableQuery[Coaches]

  class Coaches(tag: Tag) extends Table[CoachDTO](tag, "client") {

    def id     = column[CoachId]("id", O.PrimaryKey)
    def name   = column[String]("name")
    def height = column[Int]("height")

    override def * =
      (
        id,
        name,
        height
      ) <> (CoachDTO.tupled, CoachDTO.unapply)
  }

}

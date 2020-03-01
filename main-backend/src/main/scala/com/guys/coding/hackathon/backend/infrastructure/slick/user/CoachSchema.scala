package com.guys.coding.hackathon.backend.infrastructure.slick.user

import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.domain.user.Coach
import com.guys.coding.hackathon.backend.infrastructure.slick.CustomJdbcTypes.CoachIdMap
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import slick.lifted.{TableQuery, Tag}

object CoachSchema extends SlickSchemas {
  case class CoachDTO(
      id: CoachId,
      name: String,
      pictureUrl: String
  )

  override def schemas = List(coaches)

  val coaches = TableQuery[Coaches]

  def toDomain(coach: CoachDTO): Coach = {
    Coach(
      CoachId(coach.id.value),
      coach.name,
      coach.pictureUrl
    )
  }

  def toDTO(coach: Coach): CoachDTO = {
    CoachDTO(
      CoachId(coach.id.value),
      coach.name,
      coach.pictureUrl
    )
  }

  class Coaches(tag: Tag) extends Table[CoachDTO](tag, "coach") {

    //
    def id         = column[CoachId]("id", O.PrimaryKey)
    def name       = column[String]("name")
    def pictureUrl = column[String]("pictureUrl")

    override def * =
      (
        id,
        name,
        pictureUrl
      ) <> (CoachDTO.tupled, CoachDTO.unapply)
  }

}

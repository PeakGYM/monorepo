package com.guys.coding.hackathon.backend.infrastructure.slick.training

import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import slick.lifted.{TableQuery, Tag}

object TrainingSchema extends SlickSchemas {
  case class TrainingDTO(
      id: String,
      value: String,
      archived: Boolean
  )

  override def schemas = List(trainings)

  val trainings = TableQuery[Trainings]

  class Trainings(tag: Tag) extends Table[TrainingDTO](tag, "training") {

    def id       = column[String]("id", O.PrimaryKey)
    def value    = column[String]("value")
    def archived = column[Boolean]("archived")

    override def * =
      (
        id,
        value,
        archived
      ) <> (TrainingDTO.tupled, TrainingDTO.unapply)
  }

}

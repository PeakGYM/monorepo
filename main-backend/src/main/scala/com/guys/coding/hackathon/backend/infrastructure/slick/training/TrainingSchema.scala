package com.guys.coding.hackathon.backend.infrastructure.slick.training

import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import com.guys.coding.hackathon.backend.domain.training._
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.DtoMappings
import slick.lifted.{TableQuery, Tag}
import java.time.ZonedDateTime
import hero.common.postgres.newtype.NewtypeTranscoders
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import com.guys.coding.hackathon.backend.infrastructure.slick.CustomJdbcTypes._

object TrainingSchema extends SlickSchemas with DtoMappings with NewtypeTranscoders {
  case class Training(
      id: String,
      name: String,
      coachId: Option[CoachId],
      clientId: ClientId,
      dateFrom: ZonedDateTime,
      dateTo: ZonedDateTime,
      inperson: Boolean
  )

  override def schemas = List(trainings)

  val trainings = TableQuery[Trainings]

  class Trainings(tag: Tag) extends Table[Training](tag, "training") {

    def id               = column[String]("id", O.PrimaryKey)
    def name             = column[String]("name")
    def coach            = column[Option[CoachId]]("coach_id")
    def client           = column[ClientId]("client_id")
    def dateFrom         = column[ZonedDateTime]("time_from")
    def dateTo           = column[ZonedDateTime]("time_to")
    def plannedExercises = column[List[PlannedExercise]]("planned_exercises")
    def inperson         = column[Boolean]("inperson")

    override def * =
      (
        id,
        name,
        coach,
        client,
        dateFrom,
        dateTo,
        inperson
      ) <> (Training.tupled, Training.unapply)
  }

}

package com.guys.coding.hackathon.backend.infrastructure.slick.training

import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import com.guys.coding.hackathon.backend.domain.training.Series
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.DtoMappings
import slick.lifted.{TableQuery, Tag}
import java.time.ZonedDateTime
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import com.guys.coding.hackathon.backend.infrastructure.slick.CustomJdbcTypes._

object TrainingSchema extends SlickSchemas with DtoMappings {
  case class TrainingDTO(
      id: String,
      name: String,
      coach: Option[CoachId],
      client: ClientId,
      dateFrom: ZonedDateTime,
      dateTo: ZonedDateTime, // TODO:bcm add inpreson
      archived: Boolean
  )

  case class ExerciseDTO(
      id: String,
      imgurl: String,
      name: String
  )

  case class PlannedExerciseDTO(exerciseId: String, plannedSeries: List[Series], doneSeries: List[Series], restAfter: Int)

  override def schemas = List(trainings)

  val trainings = TableQuery[Trainings]

  class Trainings(tag: Tag) extends Table[TrainingDTO](tag, "training") {

    def id       = column[String]("id", O.PrimaryKey)
    def name     = column[String]("name")
    def coach    = column[Option[CoachId]]("coach_id")
    def client   = column[ClientId]("client_id")
    def dateFrom = column[ZonedDateTime]("time_from")
    def dateTo   = column[ZonedDateTime]("time_to")
    def archived = column[Boolean]("archived")

    override def * =
      (
        id,
        name,
        coach,
        client,
        dateFrom,
        dateTo,
        archived
      ) <> (TrainingDTO.tupled, TrainingDTO.unapply)
  }

}

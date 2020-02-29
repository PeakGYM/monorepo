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
import slick.jdbc.JdbcType
import com.guys.coding.hackathon.backend.infrastructure.slick.training.TrainingSchema.MuscleGroup.Chest
import com.guys.coding.hackathon.backend.infrastructure.slick.training.TrainingSchema.MuscleGroup.Back
import com.guys.coding.hackathon.backend.infrastructure.slick.training.TrainingSchema.MuscleGroup.Shoulders
import com.guys.coding.hackathon.backend.infrastructure.slick.training.TrainingSchema.MuscleGroup.Arms
import com.guys.coding.hackathon.backend.infrastructure.slick.training.TrainingSchema.MuscleGroup.Legs

object TrainingSchema extends SlickSchemas with DtoMappings with NewtypeTranscoders {

  sealed trait MuscleGroup

  object MuscleGroup {
    case object Chest     extends MuscleGroup
    case object Back      extends MuscleGroup
    case object Shoulders extends MuscleGroup
    case object Arms      extends MuscleGroup
    case object Legs      extends MuscleGroup
  }

  implicit val MuscleGroupJdbctype: JdbcType[MuscleGroup] = MappedColumnType.base[MuscleGroup, String](muscleGroupToDTO, muscleGroupToDomain)

  case class TrainingDTO(
      id: String,
      name: String,
      muscleGroup: List[String],
      coachId: Option[CoachId],
      clientId: ClientId,
      dateFrom: ZonedDateTime,
      dateTo: ZonedDateTime,
      plannedExercises: List[PlannedExercise],
      inperson: Boolean
  ) {
    def toDomain = Training(
      id = id,
      name = name,
      muscleGroup = muscleGroup.map(muscleGroupToDomain),
      coachId = coachId,
      clientId = clientId,
      dateFrom = dateFrom,
      dateTo = dateTo,
      exercises = plannedExercises,
      inperson = inperson
    )
  }

  def trainingFromDomain(t: Training) = TrainingDTO(
    id = t.id,
    name = t.name,
    muscleGroup = t.muscleGroup.map(muscleGroupToDTO),
    plannedExercises = t.exercises,
    coachId = t.coachId,
    clientId = t.clientId,
    dateFrom = t.dateFrom,
    dateTo = t.dateTo,
    inperson = t.inperson
  )

  def muscleGroupToDTO(m: MuscleGroup) = m match {
    case Chest     => "Chest"
    case Back      => "Back"
    case Shoulders => "Shoulders"
    case Arms      => "Arms"
    case Legs      => "Legs"
  }

  def muscleGroupToDomain(m: String): MuscleGroup = m match {
    case "Chest"     => Chest
    case "Back"      => Back
    case "Shoulders" => Shoulders
    case "Arms"      => Arms
    case "Legs"      => Legs
    case _           => throw new IllegalArgumentException("invalid muslce group")

  }

  override def schemas = List(trainings)

  val trainings = TableQuery[Trainings]

  class Trainings(tag: Tag) extends Table[TrainingDTO](tag, "training") {

    def id               = column[String]("id", O.PrimaryKey)
    def name             = column[String]("name")
    def muscleGroup      = column[List[String]]("muscle_group")
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
        muscleGroup,
        coach,
        client,
        dateFrom,
        dateTo,
        plannedExercises,
        inperson
      ) <> (TrainingDTO.tupled, TrainingDTO.unapply)
  }

}

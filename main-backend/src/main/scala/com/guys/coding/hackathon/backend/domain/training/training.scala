package com.guys.coding.hackathon.backend.domain.training

import com.guys.coding.hackathon.backend.infrastructure.slick.training.TrainingSchema.MuscleGroup
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import java.time.ZonedDateTime
case class TrainingId(value: String) extends AnyVal

case class Series(id: String, reps: Int, rest: Int, weight: Option[Int])

case class Exercise(
    id: String,
    imgurl: String,
    name: String
)

case class PlannedExercise(exerciseId: String, trainingId: String, plannedSeries: List[Series], doneSeries: List[Series], restAfter: Int)

case class Training(
    id: String,
    name: String,
    muscleGroup: List[MuscleGroup],
    coachId: Option[CoachId],
    clientId: ClientId,
    dateFrom: ZonedDateTime,
    dateTo: ZonedDateTime,
    inperson: Boolean
)

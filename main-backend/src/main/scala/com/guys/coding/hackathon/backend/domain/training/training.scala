package com.guys.coding.hackathon.backend.domain.training
case class TrainingId(value: String) extends AnyVal

case class Series(id: String, reps: Int, rest: Int, weight: Option[Int])

case class Exercise(
    id: String,
    imgurl: String,
    name: String
)

case class PlannedExercise(exerciseId: String, trainingId: String, plannedSeries: List[Series], doneSeries: List[Series], restAfter: Int)

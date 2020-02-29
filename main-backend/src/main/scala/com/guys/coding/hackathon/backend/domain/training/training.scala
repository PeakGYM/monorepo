package com.guys.coding.hackathon.backend.domain.training
case class TrainingId(value: String) extends AnyVal




  case class Series(id: String, reps: Int, rest: Int, weight: Option[Int])

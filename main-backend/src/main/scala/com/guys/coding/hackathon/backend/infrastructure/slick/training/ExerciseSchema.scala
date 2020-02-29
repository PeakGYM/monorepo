package com.guys.coding.hackathon.backend.infrastructure.slick.training

import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import com.guys.coding.hackathon.backend.domain.training._
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.DtoMappings
import slick.lifted.{TableQuery, Tag}

object ExerciseSchema extends SlickSchemas with DtoMappings {

  override def schemas = List(exercises)

  val exercises = TableQuery[Exercises]

  class Exercises(tag: Tag) extends Table[Exercise](tag, "exercises") {

    def id     = column[String]("id", O.PrimaryKey)
    def imgurl = column[String]("imgurl")
    def name   = column[String]("name")

    override def * =
      (
        id,
        imgurl,
        name
      ) <> (Exercise.tupled, Exercise.unapply)
  }

}

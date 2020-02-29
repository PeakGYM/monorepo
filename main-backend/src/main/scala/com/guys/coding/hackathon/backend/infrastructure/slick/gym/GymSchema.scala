package com.guys.coding.hackathon.backend.infrastructure.slick.gym

import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import slick.lifted.{TableQuery, Tag}
import com.guys.coding.hackathon.backend.domain.gym._
import com.guys.coding.hackathon.backend.domain.Location
import com.guys.coding.hackathon.backend.domain.UserId.CoachId

object GymSchema extends SlickSchemas {

  case class GymDTO(
      id: String,
      name: String,
      lat: String,
      lng: String,
      coachIds: List[String]
  )

  case class LocationDTO(
      lat: String,
      lng: String
  )

  def toDomain(gym: GymDTO): Gym = {
    Gym(
      GymId(gym.id),
      gym.name,
      Location(gym.lat, gym.lng),
      gym.coachIds.map(CoachId)
    )
  }

  def toDTO(gym: Gym): GymDTO = {
    GymDTO(
      gym.id.value,
      gym.name,
      gym.location.lat,
      gym.location.lng,
      gym.coachIds.map(_.value)
    )
  }

  override def schemas = List(gyms)

  val gyms = TableQuery[Gyms]

  class Gyms(tag: Tag) extends Table[GymDTO](tag, "gym") {
    def id       = column[String]("id", O.PrimaryKey)
    def name     = column[String]("name")
    def lat      = column[String]("lat")
    def lng      = column[String]("lng")
    def coachIds = column[List[String]]("coachIds")

    override def * =
      (
        id,
        name,
        lat,
        lng,
        coachIds
      ) <> (GymDTO.tupled, GymDTO.unapply)
  }

}

package com.guys.coding.hackathon.backend.infrastructure.slick.gym

import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import slick.lifted.{TableQuery, Tag}
import com.vividsolutions.jts.geom.Point
import com.guys.coding.hackathon.backend.domain.gym._
import com.guys.coding.hackathon.backend.domain.Location
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.vividsolutions.jts.geom.Point
import com.guys.coding.hackathon.backend.infrastructure.slick.PointFactory

object GymSchema extends SlickSchemas {

  case class GymDTO(
      id: String,
      name: String,
      location: Point,
      imgurl: Option[String],
      coachIds: List[String]
  )

  def toDomain(gym: GymDTO): Gym = {
    Gym(
      GymId(gym.id),
      gym.name,
      Location(lng = gym.location.getX, lat = gym.location.getY),
      gym.imgurl,
      gym.coachIds.map(CoachId)
    )
  }

  def toDTO(gym: Gym): GymDTO = {
    GymDTO(
      gym.id.value,
      gym.name,
      PointFactory.createPoint(gym.location.lat, gym.location.lng),
      gym.imgurl,
      gym.coachIds.map(_.value)
    )
  }

  override def schemas = List(gyms)

  val gyms = TableQuery[Gyms]

  class Gyms(tag: Tag) extends Table[GymDTO](tag, "gym") {
    def id       = column[String]("id", O.PrimaryKey)
    def name     = column[String]("name")
    def location = column[Point]("location")
    def imgurl   = column[Option[String]]("imgurl")
    def coachIds = column[List[String]]("coachIds")

    override def * =
      (
        id,
        name,
        location,
        imgurl,
        coachIds
      ) <> (GymDTO.tupled, GymDTO.unapply)
  }

}

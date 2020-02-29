package com.guys.coding.hackathon.backend.infrastructure.slick.gym

import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import slick.lifted.{TableQuery, Tag}
import com.guys.coding.hackathon.backend.domain.gym._
import hero.common.postgres.newtype.NewtypeTranscoders
import io.circe.{Json, _}
import com.guys.coding.hackathon.backend.domain.Location
import io.circe.generic.semiauto._
import io.circe.syntax._
import slick.ast.BaseTypedType
import slick.jdbc.JdbcType

object GymSchema extends SlickSchemas with NewtypeTranscoders {

  case class GymDTO(
      id: String,
      name: String,
      coaches: List[GymCoachDTO]
  )

  case class GymCoachDTO(
      id: String,
      location: LocationDTO
  )

  case class LocationDTO(
      lat: String,
      lng: String
  )

  def toDomain(gym: GymDTO): Gym = {
    def locationToDomain(location: LocationDTO): Location =
      Location(
        location.lat,
        location.lng
      )

    def coachToDomain(coach: GymCoachDTO): GymCoach =
      GymCoach(
        CoachId(coach.id),
        locationToDomain(coach.location)
      )

    Gym(
      GymId(gym.id),
      gym.name,
      gym.coaches.map(coachToDomain)
    )
  }

  def toDTO(gym: Gym): GymDTO = {
    def locationToDTO(location: Location): LocationDTO =
      LocationDTO(
        location.lat,
        location.lng
      )

    def coachToDTO(coach: GymCoach): GymCoachDTO =
      GymCoachDTO(
        coach.id.value,
        locationToDTO(coach.location)
      )

    GymDTO(
      gym.id.value,
      gym.name,
      gym.coaches.map(coachToDTO)
    )
  }

  implicit val GymCoachDTOEncoder: Encoder[GymCoachDTO] = deriveEncoder[GymCoachDTO]
  implicit val GymCoachDTODecoder: Decoder[GymCoachDTO] = deriveDecoder[GymCoachDTO]
  implicit val LocationDTOEncoder: Encoder[LocationDTO] = deriveEncoder[LocationDTO]
  implicit val LocationDTODecoder: Decoder[LocationDTO] = deriveDecoder[LocationDTO]

  def gymCoachToJson: GymCoachDTO => Json   = _.asJson
  def gymCoachFromJson: Json => GymCoachDTO = _.as[GymCoachDTO].toOption.get
  def locationToJson: LocationDTO => Json   = _.asJson
  def locationFromJson: Json => LocationDTO = _.as[LocationDTO].toOption.get

  implicit val GymCoachDTOMapper: JdbcType[GymCoachDTO] with BaseTypedType[GymCoachDTO] =
    MappedColumnType.base[GymCoachDTO, Json](gymCoachToJson, gymCoachFromJson)

  implicit val LocationDTOMapper: JdbcType[LocationDTO] with BaseTypedType[LocationDTO] =
    MappedColumnType.base[LocationDTO, Json](locationToJson, locationFromJson)

  override def schemas = List(gyms)

  val gyms = TableQuery[Gyms]

  class Gyms(tag: Tag) extends Table[GymDTO](tag, "gym") {
    def id      = column[String]("id", O.PrimaryKey)
    def name    = column[String]("name")
    def coaches = column[List[GymCoachDTO]]("coaches")

    override def * =
      (
        id,
        name,
        coaches
      ) <> (GymDTO.tupled, GymDTO.unapply)
  }

}

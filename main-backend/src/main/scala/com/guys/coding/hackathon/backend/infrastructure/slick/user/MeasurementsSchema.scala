package com.guys.coding.hackathon.backend.infrastructure.slick.user

import java.time.ZonedDateTime

import com.guys.coding.hackathon.backend.domain.MeasurementId.MeasurementId
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import com.guys.coding.hackathon.backend.domain.user.Measurement
import com.guys.coding.hackathon.backend.infrastructure.slick.CustomJdbcTypes.{ClientIdMap, MeasurementIdMap}
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.DtoMappings
import slick.lifted.{TableQuery, Tag}

object MeasurementsSchema extends SlickSchemas with DtoMappings {
  case class MeasurementDTO(
      //weight in kg, measurements in cm
      id: MeasurementId,
      clientId: ClientId,
      timestamp: ZonedDateTime,
      weight: Option[Double],
      neck: Option[Double],
      leftBicep: Option[Double],
      rightBicep: Option[Double],
      leftForearm: Option[Double],
      rightForearm: Option[Double],
      chest: Option[Double],
      waist: Option[Double],
      hip: Option[Double],
      rightThigh: Option[Double],
      leftThigh: Option[Double],
      rightCalf: Option[Double],
      leftCalf: Option[Double]
  )

  def toDomain(measurement: MeasurementDTO): Measurement = {
    Measurement(
      MeasurementId(measurement.id.value),
      measurement.clientId,
      measurement.timestamp,
      measurement.weight,
      measurement.neck,
      measurement.leftBicep,
      measurement.rightBicep,
      measurement.leftForearm,
      measurement.rightForearm,
      measurement.chest,
      measurement.waist,
      measurement.hip,
      measurement.rightThigh,
      measurement.leftThigh,
      measurement.rightCalf,
      measurement.leftCalf
    )
  }

  def toDTO(measurement: Measurement): MeasurementDTO = {
    MeasurementDTO(
      MeasurementId(measurement.id.value),
      measurement.clientId,
      measurement.timestamp,
      measurement.weight,
      measurement.neck,
      measurement.leftBicep,
      measurement.rightBicep,
      measurement.leftForearm,
      measurement.rightForearm,
      measurement.chest,
      measurement.waist,
      measurement.hip,
      measurement.rightThigh,
      measurement.leftThigh,
      measurement.rightCalf,
      measurement.leftCalf
    )
  }

  override def schemas = List(measurements)

  val measurements = TableQuery[Measurements]

  class Measurements(tag: Tag) extends Table[MeasurementDTO](tag, "measurement") {

    def id           = column[MeasurementId]("id", O.PrimaryKey)
    def clientId     = column[ClientId]("clientId")
    def timestamp    = column[ZonedDateTime]("timestamp")
    def weight       = column[Option[Double]]("weight")
    def neck         = column[Option[Double]]("neck")
    def leftBicep    = column[Option[Double]]("leftBicep")
    def rightBicep   = column[Option[Double]]("rightBicep")
    def leftForearm  = column[Option[Double]]("leftForearm")
    def rightForearm = column[Option[Double]]("rightForearm")
    def chest        = column[Option[Double]]("chest")
    def waist        = column[Option[Double]]("waist")
    def hip          = column[Option[Double]]("hip")
    def rightThigh   = column[Option[Double]]("rightThigh")
    def leftThigh    = column[Option[Double]]("leftThigh")
    def rightCalf    = column[Option[Double]]("rightCalf")
    def leftCalf     = column[Option[Double]]("leftCalf")
    override def * =
      (
        id,
        clientId,
        timestamp,
        weight,
        neck,
        leftBicep,
        rightBicep,
        leftForearm,
        rightForearm,
        chest,
        waist,
        hip,
        rightThigh,
        leftThigh,
        rightCalf,
        leftCalf
      ) <> (MeasurementDTO.tupled, MeasurementDTO.unapply)
  }

}

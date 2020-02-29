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


object MeasurementsSchema extends SlickSchemas with DtoMappings  {
  case class MeasurementDTO(
      //weight in kg, measurements in cm
      id: MeasurementId,
      clientId: ClientId,
      timestamp: ZonedDateTime,
      weight: Double,
      neck: Double,
      leftBicep: Double,
      rightBicep: Double,
      leftForearm: Double,
      rightForearm: Double,
      chest: Double,
      waist: Double,
      hip: Double,
      rightThigh: Double,
      leftThigh: Double,
      rightCalf: Double,
      leftCalf: Double
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
    def weight       = column[Double]("weight")
    def neck         = column[Double]("neck")
    def leftBicep    = column[Double]("leftBicep")
    def rightBicep   = column[Double]("rightBicep")
    def leftForearm  = column[Double]("leftForearm")
    def rightForearm = column[Double]("rightForearm")
    def chest        = column[Double]("chest")
    def waist        = column[Double]("waist")
    def hip          = column[Double]("hip")
    def rightThigh   = column[Double]("rightThigh")
    def leftThigh    = column[Double]("leftThigh")
    def rightCalf    = column[Double]("rightCalf")
    def leftCalf     = column[Double]("leftCalf")
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

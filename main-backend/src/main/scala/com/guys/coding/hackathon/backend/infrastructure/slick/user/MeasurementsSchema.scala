package com.guys.coding.hackathon.backend.infrastructure.slick.user

import com.guys.coding.hackathon.backend.domain.MeasurementId.MeasurementId
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import slick.jdbc.JdbcType
import slick.lifted.{TableQuery, Tag}

object MeasurementsSchema extends SlickSchemas {
  implicit val MeasurementIdMap: JdbcType[MeasurementId] = MappedColumnType.base[MeasurementId, String](_.value, MeasurementId)

  case class MeasurementDTO(
      //weight in kg, measurements in cm
      id: MeasurementId,
      timestamp: Long,
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

  override def schemas = List(clients)

  val clients = TableQuery[Measurements]

  class Measurements(tag: Tag) extends Table[MeasurementDTO](tag, "measurement") {

    def id           = column[MeasurementId]("id", O.PrimaryKey)
    def timestamp    = column[Long]("timestamp")
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

package com.guys.coding.hackathon.backend.infrastructure.slick.user
import com.guys.coding.hackathon.backend.domain.UserId.{ClientId, CoachId}
import com.guys.coding.hackathon.backend.domain.user.ClientCoachCooperation
import com.guys.coding.hackathon.backend.infrastructure.slick.CustomJdbcTypes.{ClientIdMap, CoachIdMap}
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import slick.lifted.{TableQuery, Tag}

object ClientCoachCooperationSchema extends SlickSchemas {

  case class ClientCoachCooperationDTO(
      id: String,
      clientId: ClientId,
      coachId: CoachId
  )

  override def schemas = List(cooperations)

  val cooperations = TableQuery[ClientCoachCooperations]

  def toDomain(cooperation: ClientCoachCooperationDTO): ClientCoachCooperation =
    ClientCoachCooperation(cooperation.id, cooperation.clientId, cooperation.coachId)

  def toDTO(cooperation: ClientCoachCooperation): ClientCoachCooperationDTO =
    ClientCoachCooperationDTO(cooperation.id, cooperation.clientId, cooperation.coachId)

  class ClientCoachCooperations(tag: Tag) extends Table[ClientCoachCooperationDTO](tag, "client_to_coach") {

    def id       = column[String]("id", O.PrimaryKey)
    def clientId = column[ClientId]("client_id")
    def coachId  = column[CoachId]("coach_id")

    override def * =
      (
        id,
        clientId,
        coachId
      ) <> (ClientCoachCooperationDTO.tupled, ClientCoachCooperationDTO.unapply)
  }
}

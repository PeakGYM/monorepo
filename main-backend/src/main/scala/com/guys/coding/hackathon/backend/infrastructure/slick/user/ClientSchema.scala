package com.guys.coding.hackathon.backend.infrastructure.slick.user

import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import com.guys.coding.hackathon.backend.domain.user.Client
import com.guys.coding.hackathon.backend.infrastructure.slick.CustomJdbcTypes.ClientIdMap
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.SlickSchemas
import com.guys.coding.hackathon.backend.infrastructure.slick.repo.profile.api._
import slick.lifted.{TableQuery, Tag}

object ClientSchema extends SlickSchemas {

  case class ClientDTO(
      id: ClientId,
      name: String,
      height: Int, // height in cm
      pictureUrl: String
  )

  override def schemas = List(clients)

  val clients = TableQuery[Clients]

  def toDomain(client: ClientDTO): Client = {
    Client(
      ClientId(client.id.value),
      client.name,
      client.height,
      client.pictureUrl
    )
  }

  def toDTO(client: Client): ClientDTO = {
    ClientDTO(
      ClientId(client.id.value),
      client.name,
      client.height,
      client.pictureUrl
    )
  }

  class Clients(tag: Tag) extends Table[ClientDTO](tag, "client") {

    def id        = column[ClientId]("id", O.PrimaryKey)
    def name      = column[String]("name")
    def height    = column[Int]("height")
    def pictureUrl = column[String]("picture_url")

    override def * =
      (
        id,
        name,
        height,
        pictureUrl
      ) <> (ClientDTO.tupled, ClientDTO.unapply)
  }

}

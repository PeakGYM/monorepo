package com.guys.coding.hackathon.backend.api

import org.http4s.HttpRoutes
import cats.effect.IO
import com.guys.coding.hackathon.backend.infrastructure.Faker
import com.guys.coding.hackathon.backend.Services
import org.http4s.dsl.impl.Root
import org.http4s.HttpRoutes
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import cats.effect._, org.http4s._, org.http4s.dsl.io._

object Fakerendpoint {

  def route(s: Services): HttpRoutes[IO] =
    HttpRoutes.of[IO] {
      case POST -> Root / "measurements" / clientId / cumSumWindow =>
        for {
          measurements <- Faker.fakeMeasurements(cumSumWindow.toInt, ClientId(clientId))
          _            <- s.measurementsRepository.batchInsert(measurements)
          ok           <- Ok("Super")
        } yield ok

    }
}

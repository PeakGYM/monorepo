package com.guys.coding.hackathon.backend.infrastructure

import cats.effect.IO
import com.guys.coding.hackathon.backend.domain.user.Measurement
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import scala.util.Random
import hero.common.util.IdProvider
import com.guys.coding.hackathon.backend.domain.MeasurementId
import java.time.ZonedDateTime
import com.guys.coding.hackathon.backend.domain.training.Series
import com.guys.coding.hackathon.backend.domain.training.PlannedExercise

object Faker {

  def random(from: Int, to: Int) = Some(from + Random.nextInt(to - from) + Random.nextDouble())

  private def randomMeasurement(client: ClientId, time: ZonedDateTime) =
    Measurement(
      id = MeasurementId.MeasurementId(IdProvider.id.newId()),
      clientId = client,
      timestamp = time,
      weight = random(40, 130),
      neck = random(30, 50),
      leftBicep = random(20, 55),
      rightBicep = random(20, 55),
      leftForearm = random(15, 30),
      rightForearm = random(15, 30),
      chest = random(60, 120),
      waist = random(60, 140),
      hip = random(60, 140),
      rightThigh = random(30, 80),
      leftThigh = random(30, 80),
      rightCalf = random(20, 50),
      leftCalf = random(20, 50)
    )

  implicit private class OptionDoubleExt(od: Option[Double]) {
    def `+`(o: Option[Double]) =
      for {
        a <- od
        b <- o
      } yield a + b
  }

  implicit private class MeasurementExt(m: Measurement) {
    def `+`(other: Measurement) = m.copy(
      weight = m.weight + other.weight,
      neck = m.neck + other.neck,
      leftBicep = m.leftBicep + other.leftBicep,
      rightBicep = m.rightBicep + other.rightBicep,
      leftForearm = m.leftForearm + other.leftForearm,
      rightForearm = m.rightForearm + other.rightForearm,
      chest = m.chest + other.chest,
      waist = m.waist + other.waist,
      hip = m.hip + other.hip,
      rightThigh = m.rightThigh + other.rightThigh,
      leftThigh = m.leftThigh + other.leftThigh,
      rightCalf = m.rightCalf + other.rightCalf,
      leftCalf = m.leftCalf + other.leftCalf
    )

    def `/`(value: Double) =
      m.copy(
        weight = m.weight.map(_ / value),
        neck = m.neck.map(_ / value),
        leftBicep = m.leftBicep.map(_ / value),
        rightBicep = m.rightBicep.map(_ / value),
        leftForearm = m.leftForearm.map(_ / value),
        rightForearm = m.rightForearm.map(_ / value),
        chest = m.chest.map(_ / value),
        waist = m.waist.map(_ / value),
        hip = m.hip.map(_ / value),
        rightThigh = m.rightThigh.map(_ / value),
        leftThigh = m.leftThigh.map(_ / value),
        rightCalf = m.rightCalf.map(_ / value),
        leftCalf = m.leftCalf.map(_ / value)
      )
  }

  def avg(m: List[Measurement]) = m.reduceOption(_ + _).map(_ / m.size.toDouble)

  def fakeMeasurements(cumSumWindow: Int, clientId: ClientId): IO[List[Measurement]] = IO {

    val queue = scala.collection.mutable.Queue[Measurement]()

    val now = ZonedDateTime.now()

    (0 to 720).reverse
      .map(dayBefore => randomMeasurement(clientId, now.minusDays(dayBefore.toLong)))
      .map { m =>
        val mean = avg(m :: queue.toList).get

        val res = mean.copy(id = m.id, clientId = m.clientId, timestamp = m.timestamp)

        queue.enqueue(res)
        if (queue.size > cumSumWindow) {
          queue.dequeue()
        }

        res

      }
      .toList

  }

  def plannedEx(trainingId: String) =
    PlannedExercise(exerciseId = Random.nextInt(4).toString, trainingId = trainingId, plannedSeries = (1 to 3).map(randomSeries).toList, doneSeries = Nil,restAfter = 180)

  def randomSeries(id: Int): Series =
    Series(id = id.toString(), reps = 5 + Random.nextInt(3), rest = 60 + Random.nextInt(60), weight = Some(40 + Random.nextInt(30)))

}

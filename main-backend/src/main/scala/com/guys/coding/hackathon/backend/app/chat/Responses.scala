package com.guys.coding.wwh.chat

import com.guys.coding.hackathon.backend.domain.training.Training
import io.codeheroes.herochat.environment.facebook.Buttons.UrlButton
import io.codeheroes.herochat.environment.facebook.FacebookResponse.{Buttons, QuickReplies, Simple}
import io.codeheroes.herochat.environment.facebook.{QuickReplies => QR}

import scala.util.Random
import com.guys.coding.hackathon.backend.domain.user.Coach
import io.codeheroes.herochat.environment.facebook.Buttons.CallButton
import java.time.ZonedDateTime

//noinspection SpellCheckingInspection
object Responses {

  val helloMessage = QuickReplies(
    "Hello do you want to exercise?\n Or maybe  you want to find personal trainier in vicinity?",
    QR.Text("Trainer 📍", "GET_MAP"),
    QR.Text("No 😥", "NOT_EXERCISE"),
    QR.Text("Next training", "GET_NEXT_TRAINING")

    // Get next training

    // QR.Location // not working
  )

  def getMapResponse() =
    Buttons(
      "Personal trainers can help you become best version of yourself 💪",
      UrlButton("Find them", s"https://wwh.codevillains.me/map")
    )

  def getTrainingInfoResponse(training: Training, coach: Coach) = {

    def supoerInt(i: Int) =
      if (i < 10) {
        s"0$i"
      } else {
        i.toString
      }

    def formatData(d: ZonedDateTime) = supoerInt(d.getDayOfMonth()) + "." + supoerInt(d.getMonthValue()) + "." + d.getYear().toString()

    val fromDate = formatData(training.dateFrom)

    val fromHour = training.dateFrom.getHour() + ":" + supoerInt(training.dateFrom.getMinute())
    val toHour   = training.dateTo.getHour() + ":" + supoerInt(training.dateTo.getMinute())

    Buttons(
      s"You have training with ${coach.name} 🏃\n It will be ${fromDate} $fromHour - $toHour 🕛 \n Want to contact your trainer? ",
      UrlButton("Log training", s"https://wwh.codevillains.me/trainings/${training.id}/log"),
      CallButton("Call him", "707043432")
    )
  }

  def getTrainingNotFoundResponse() =
    Simple("No workout found for you. You can schedule another via our app!")

  def looserResponse() =
    Simple(
      Random
        .shuffle(
          List(
            "Bro I feel you 🙁",
            "Did you drink last night? 😈",
            "Come on! ✊",
            "What can I do? 🤷"
          )
        )
        .head
    )

  def ok: Simple =
    Simple(
      Random
        .shuffle(
          List(
            "Ok",
            "Cool"
          )
        )
        .head
    )

  val sorryItsDuplicateMessage = Simple(
    "Sorry to hear that :'("
  )

  val iDoNotUnderstand = Simple("Sorry, I don't understand that 😰")

}

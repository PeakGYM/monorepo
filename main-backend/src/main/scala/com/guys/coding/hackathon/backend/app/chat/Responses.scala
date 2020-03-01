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
    "Hey, do you want to trian with your personal trainier?\n Or maybe  you want to find one somwhere newarby?",
    QR.Text("Find 📍", "GET_MAP"),
    QR.Text("I'm tired 😥", "NOT_EXERCISE"),
    QR.Text("Train? When? 😍", "GET_NEXT_TRAINING")

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
    QuickReplies(
      Random
        .shuffle(
          List(
            "Did you drink last night or what? 😈\nPush ❗",
            "Come on! ✊",
            "Really?\nI won't exercise for you 🤷"
          )
        )
        .head,
      QR.Text(
        "Ok 😢",
        "OK_I_WILL"
      ),
      QR.Text(
        "Kidding 😘",
        "OK_I_WILL"
      )
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

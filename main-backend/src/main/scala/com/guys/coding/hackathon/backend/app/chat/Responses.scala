package com.guys.coding.wwh.chat

import com.guys.coding.hackathon.backend.domain.training.Training
import io.codeheroes.herochat.environment.facebook.Buttons.{MessengerExtension, UrlButton}
import io.codeheroes.herochat.environment.facebook.FacebookResponse.{Buttons, QuickReplies, Simple}
import io.codeheroes.herochat.environment.facebook.{MessengerExtensionHeights, QuickReplies => QR}

import scala.util.Random

//noinspection SpellCheckingInspection
object Responses {

  val helloMessage = QuickReplies(
    "Hey, do you want to exercise?\n Or maybe  you want to find personal trainier in vicinity?",
    QR.Text("Show me trainers nearby ", "GET_MAP"),
    QR.Text("I don't want to exercise 😥", "NOT_EXERCISE"),
    QR.Text("What is my next training 🤔 ?", "GET_NEXT_TRAINING")

    // Get next training

    // QR.Location // not working
  )

  def getMapResponse() =
    Buttons(
      "Who can help you become the best? 💪",
      UrlButton("Nearby traininers", s"https://wwh.codevillains.me/map")
    )

  def getTrainingInfoResponse(training: Training) =
    Simple(s"Your next training will take place from ${training.dateFrom} to ${training.dateTo}.")
  def getTrainingNotFoundResponse() =
    Simple("No workout found for you. You can schedule another via our app!")

  def inProgress() =
    Simple(
      Random
        .shuffle(
          List(
            "Robert is working",
            "It will be done",
            "Developing"
          )
        )
        .head
    )
  def looserResponse() =
    Simple(
      Random
        .shuffle(
          List(
            "Bro I feel you 🙁",
            "Did you drink last night? 😈",
            "Come on! ✊"
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

  val giveMeIssueName   = Simple("What should be the name of the issue? 📝")
  val giveMeDescription = Simple("Add more description if you want 📝")
  val completedAllData  = Simple("Nice, you did it 🙃 We've gathered all data I needed to know. Do you want to create a JIRA issue?")

  def issuePreview(uri: String) = Buttons("Here's the summary of your issue.", MessengerExtension("Issue", uri, MessengerExtensionHeights.Full))

  val askIsaDuplicateMessage = QuickReplies(
    "Is it a dupliacte issue?",
    QR.Text("Yes", "DUPLICATE"),
    QR.Text("No", "NO_DUPLICATE")
  )

  val sorryItsDuplicateMessage = Simple(
    "Sorry to hear that :'("
  )

  def issueCreated(url: String): Buttons = Buttons("Issue created! Click link below to see it in JIRA", UrlButton("Jira", url))

  val couldntCreateIssue = Simple(s"Couldn't create issue :(")

  def imListeningMessage =
    Simple(
      Random
        .shuffle(
          List(
            "Fine, I'm listening...",
            "Okay, what do you have ? 🤔",
            "Nice. Show me what you've found 😉"
          )
        )
        .head
    )

  val iDoNotUnderstand = Simple("Sorry, I don't understand that 😰")

}

package com.guys.coding.bitehack.chat

import io.codeheroes.herochat.environment.facebook.Buttons.{MessengerExtension, UrlButton}
import io.codeheroes.herochat.environment.facebook.FacebookResponse.{Buttons, QuickReplies, Simple}
import io.codeheroes.herochat.environment.facebook.{FacebookResponse, MessengerExtensionHeights, QuickReplies => QR}

import scala.util.Random

//noinspection SpellCheckingInspection
object Responses {

  val helloMessage = QuickReplies(
    "Would you like to raport an issue? 🤔",
    QR.Text("Yes ✔️", "FOUND_NEW_BUG"),
    QR.Text("Nope ❌", "NOTHING_NEW")
  )

  def giveMeMoreInfo(about: String): Simple =
    Simple(
      Random
        .shuffle(
          List(
            "ala"
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

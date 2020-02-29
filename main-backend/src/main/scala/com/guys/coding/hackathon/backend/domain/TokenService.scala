package com.guys.coding.hackathon.backend.domain

import com.guys.coding.hackathon.backend.{AdminId, Token}

import scala.util.Try

trait TokenService {

  def validateToken(token: String): Try[AuthenticatedUser]

  def generateToken(userId: UserId): Token
}

package com.guys.coding.hackathon.backend

import com.guys.coding.hackathon.backend.domain.TokenService
import cats.effect.IO
import com.guys.coding.hackathon.backend.domain.gym.GymRepository

case class Services(gymRepository: GymRepository[IO], jwtTokenService: TokenService)

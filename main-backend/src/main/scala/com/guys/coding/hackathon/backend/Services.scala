package com.guys.coding.hackathon.backend

import com.guys.coding.hackathon.backend.domain.TokenService
import com.guys.coding.hackathon.backend.infrastructure.slick.training._
import cats.effect.IO
import com.guys.coding.hackathon.backend.domain.gym.GymRepository

case class Services(
    gymRepository: GymRepository[IO],
    trainingRepository: SlickTrainingRepository,
    exerciseRepository: SlickExerciseRepository,
    jwtTokenService: TokenService
)

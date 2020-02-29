package com.guys.coding.hackathon.backend.domain.gym

import com.guys.coding.hackathon.backend.domain.coach.CoachId
import com.guys.coding.hackathon.backend.domain.Location

case class GymId(value: String) extends AnyVal

case class Gym(
    id: GymId,
    name: String,
    coaches: List[GymCoach]
)

case class GymCoach(
    id: CoachId,
    location: Location
)


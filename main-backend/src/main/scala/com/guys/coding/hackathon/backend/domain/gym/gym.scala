package com.guys.coding.hackathon.backend.domain.gym

import com.guys.coding.hackathon.backend.domain.Location
import com.guys.coding.hackathon.backend.domain.UserId.CoachId

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


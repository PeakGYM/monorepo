package com.guys.coding.hackathon.backend.domain.user

import com.guys.coding.hackathon.backend.domain.UserId.CoachId

case class Coach(
    id: CoachId,
    name: String,
    pictureUrl: String
)

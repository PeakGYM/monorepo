package com.guys.coding.hackathon.backend.domain.user

import com.guys.coding.hackathon.backend.domain.UserId.{ClientId, CoachId}

case class ClientCoachCooperation(id: String,
                                  clientId: ClientId,
                                  coachId: CoachId)

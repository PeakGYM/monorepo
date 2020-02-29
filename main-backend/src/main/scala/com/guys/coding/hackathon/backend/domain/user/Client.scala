package com.guys.coding.hackathon.backend.domain.user

import com.guys.coding.hackathon.backend.domain.UserId.ClientId

case class Client(
    id: ClientId,
    name: String,
    height: Int, // height in cm
    pictureUrl: String
)

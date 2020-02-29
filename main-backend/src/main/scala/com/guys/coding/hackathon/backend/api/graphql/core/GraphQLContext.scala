package com.guys.coding.hackathon.backend.api.graphql.core

import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.domain.UserId.CoachId
import com.guys.coding.hackathon.backend.domain.user.Coach
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.{ExecutionContext, Future}

class GraphQLContext(services: Services)(implicit ec: ExecutionContext) extends StrictLogging {
  def getCoaches(ids: List[CoachId]): Future[List[Coach]] =
    services.coachRepository
      .getCoachesById(ids)
      .unsafeToFuture()
}

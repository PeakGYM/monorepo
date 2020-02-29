package com.guys.coding.hackathon.backend.infrastructure.slick

import com.vividsolutions.jts.geom.{Coordinate, GeometryFactory, Point, PrecisionModel}

object PointFactory {

  private val geometryFactory = new GeometryFactory(new PrecisionModel(), 4326)
  private val precisionModel  = geometryFactory.getPrecisionModel

  def createPoint(lat: Double, lng: Double): Point = {
    val coord = new Coordinate(lng, lat)
    precisionModel.makePrecise(coord)
    geometryFactory.createPoint(coord)
  }

}

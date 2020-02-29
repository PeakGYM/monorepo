package com.guys.coding.hackathon.backend.infrastructure

package object slick {
  val repo = hero.common.jdbc.repository(HackathonBackendPostgresProfile)
}

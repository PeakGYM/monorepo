package com.guys.coding.hackathon.backend.infrastructure.jwt

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import hero.common.crypto.KeyReaders.PublicKeyReader
import hero.common.crypto.KeyReaders.PrivateKeyReader
import com.guys.coding.hackathon.backend.domain.UserId.ClientId
import hero.common.util.IdProvider
import com.guys.coding.hackathon.backend.domain.UserId.TrainerId

class JwtServiceTest extends FlatSpec with Matchers {

  def getResource(name: String) = classOf[JwtServiceTest].getClassLoader.getResource(name).getPath

  val privateKey = PrivateKeyReader.get(getResource("private.der"))
  val publicKey  = PublicKeyReader.get(getResource("public.der"))
  val jwtService = new JwtTokenService(publicKey, privateKey)

  "JwtService" should "work for clientId" in {
    val id    = ClientId(IdProvider.id.newId())
    val token = jwtService.generateToken(id)
    val user  = jwtService.validateToken(token.value).get
    user.id shouldBe id
  }

  it should "work for trainer" in {
    val id    = TrainerId(IdProvider.id.newId())
    val token = jwtService.generateToken(id)
    val user  = jwtService.validateToken(token.value).get
    user.id shouldBe id
  }

}

object JwtServiceTest

package com.lngbk.authentication

import akka.actor.Props
import com.lngbk.api.{AuthenticationApi, LoginRequest}
import com.lngbk.authentication.actor.AuthenticationActor
import com.lngbk.commons.management.SystemManager
import com.lngbk.commons.management.bootstrap.ServiceBootstrapDirector
import org.scalatest.{MustMatchers, WordSpec}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by beolnix on 11/09/16.
  */
class AuthenticationSmokeTestMultiJvmServerNode1 extends WordSpec with MustMatchers {
  "A server node " should {
    "Successfully start" in {
      AuthenticationService.main(Array[String]())
      var count = 0
      while (count < 10) {
        Thread.sleep(1000)
        println(s"$count: .")
        count += 1
      }
    }
  }
}

class AuthenticationSmokeTestMultiJvmClientNode2 extends WordSpec with MustMatchers {
  "A client node" should {
    "Successfully resolve dependency" in {
      Thread.sleep(3000)
      AuthenticationApi
      ServiceBootstrapDirector.initService(true, true)

      val response = AuthenticationApi.login(LoginRequest("login", "password", "test1"))
      val result = Await.ready(response, Duration.Inf).value.get

      println(result)
      result must not be null
    }
  }
}
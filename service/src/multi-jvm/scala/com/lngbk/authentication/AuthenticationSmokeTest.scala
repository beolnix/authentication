package com.lngbk.authentication

import com.lngbk.api.{AuthenticationApi, LoginRequest}
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
      while (count < 5) {
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
      val api = new AuthenticationApi()
      ServiceBootstrapDirector.initService(true, true)

      val response = api.login(LoginRequest("login", "password"))
      val result = Await.ready(response, Duration.Inf).value.get

      println(result)
      result must not be null
    }
  }
}
package com.lngbk.authentication

import java.util.UUID

import akka.remote.testkit.{MultiNodeConfig, MultiNodeSpec}
import akka.testkit.ImplicitSender
import com.lngbk.api.{AuthenticationApi, LoginRequest}
import com.lngbk.commons.management.SystemManager
import com.lngbk.commons.management.bootstrap.ServiceBootstrapDirector
import com.typesafe.config.ConfigFactory
import org.scalatest.MustMatchers

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import org.scalatest.WordSpecLike

object MultiNodeAuthenticationConfig extends MultiNodeConfig {
  val serverNode = role("node1")
  val clientNode = role("node2")

  nodeConfig(clientNode) {
    ConfigFactory.parseString(
      """
        |akka {
        |  loglevel = DEBUG
        |  stdout-loglevel = DEBUG
        |}
      """.stripMargin
    )
  }
}

class MultiNodeAuthenticationSpecMultiJvmNode1 extends MultiNodeAuthentication

class MultiNodeAuthenticationSpecMultiJvmNode2 extends MultiNodeAuthentication

object MultiNodeAuthentication

/**
  * Created by beolnix on 11/09/16.
  */
class MultiNodeAuthentication extends MultiNodeSpec(MultiNodeAuthenticationConfig)
  with WordSpecLike with MustMatchers with ImplicitSender {

  import MultiNodeAuthenticationConfig._

  def initialParticipants = roles.size

  "A multinode test" must {
    "Send and receive login msg" in {
      runOn(clientNode) {
        println("Starting client")

        SystemManager.initWithSystem(system)
        enterBarrier("deployed")

        AuthenticationApi(Some(node(serverNode)))
        ServiceBootstrapDirector.initService(true, true)

        val response = AuthenticationApi.login(LoginRequest("login", "password", UUID.randomUUID().toString))
        val result = Await.ready(response, Duration.Inf).value.get
        println(result)
        result must not be null

        enterBarrier("finished")
      }

      runOn(serverNode) {
        println("Starting server")

        SystemManager.initWithSystem(system)
        AuthenticationService.main(Array[String]())
        enterBarrier("deployed")

        enterBarrier("finished")
      }
    }
  }
}

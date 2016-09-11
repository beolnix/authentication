package com.lngbk.authentication

import java.io.IOException
import java.net.{DatagramSocket, ServerSocket}

import akka.remote.testkit.{MultiNodeConfig, MultiNodeSpec, MultiNodeSpecCallbacks}
import akka.testkit.ImplicitSender
import com.example.STMultiNodeSpec
import com.lngbk.api.{AuthenticationApi, LoginRequest}
import com.lngbk.commons.management.SystemManager
import com.lngbk.commons.management.bootstrap.ServiceBootstrapDirector
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpec, WordSpecLike}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

object MultiNodeAuthenticationConfig extends MultiNodeConfig {
  val node1 = role("node1")
  val node2 = role("node2")

  nodeConfig(node2) {
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

object MultiNodeAuthentication {

}

/**
  * Created by beolnix on 11/09/16.
  */
class MultiNodeAuthentication extends MultiNodeSpec(MultiNodeAuthenticationConfig)
  with WordSpecLike with MustMatchers with ImplicitSender {

  import MultiNodeAuthenticationConfig._

  def initialParticipants = roles.size

  "A multinode test" must {
    "wait for all nodes to enter a barrier" in {
      enterBarrier("startup")
    }

    "Send and receive login msg" in {

      runOn(node2) {
        println("Starting client")
        SystemManager.initWithSystem(system)
        enterBarrier("deployed")
        val api = new AuthenticationApi(Some(node(node1)))
        ServiceBootstrapDirector.initService(true, true)

        val response = api.login(LoginRequest("login", "password"))
        val result = Await.ready(response, Duration.Inf).value.get

        println(result)
        //        result must not be null
        enterBarrier("finished")
      }

      runOn(node1) {
        println("Starting server")
        SystemManager.initWithSystem(system)
        AuthenticationService.main(Array[String]())
        enterBarrier("deployed")
        enterBarrier("finished")
      }
    }
  }

  def available(port:Int): Boolean = {
    if (port < 0 || port > 99999) {
      throw new IllegalArgumentException("Invalid start port: " + port);
    }

    var ss: ServerSocket = null;
    var ds: DatagramSocket = null;
    try {
      ss = new ServerSocket(port);
      ss.setReuseAddress(true);
      ds = new DatagramSocket(port);
      ds.setReuseAddress(true);
      return true;

    } finally {
      if (ds != null) {
        ds.close();
      }

      if (ss != null) {

        ss.close();

      }
    }

    return false;
  }
}

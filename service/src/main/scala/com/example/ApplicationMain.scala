package com.example

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object ApplicationMain extends App {
  val config = ConfigFactory.load("backend")
  val system = ActorSystem("AuthActorSystem", config)
  val pingActor = system.actorOf(PingActor.props, "pingActor")
  pingActor ! PingActor.Initialize

  Await.result(system.whenTerminated, Duration.Inf)
}
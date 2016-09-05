import sbt._
import Keys._

object MyBuild extends Build {
  
  lazy val root = project.in(file(".")).aggregate(service, api)

  val akkaVersion = "2.4.7"

  lazy val service = project
      .settings(
    libraryDependencies ++= 

Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "com.typesafe.akka" %%  "akka-remote"             % akkaVersion,
  "com.typesafe.akka" %%  "akka-multi-node-testkit" % akkaVersion % "test")
      )

  lazy val api = project

}


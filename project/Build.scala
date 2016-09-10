import sbt._
import Keys._

object MyBuild extends Build {

  lazy val root = project.in(file(".")).aggregate(service, api)

  val akkaVersion = "2.4.10"
  val commonsVersion = "0.0.8-SNAPSHOT"

  lazy val service = project
    .settings(
      libraryDependencies ++=

        Seq(
          "com.lngbk" %% "management" % commonsVersion changing(),
          "com.lngbk" %% "api" % commonsVersion changing(),
          "com.lngbk" %% "service" % commonsVersion changing(),

          "com.typesafe.akka" %% "akka-actor" % akkaVersion,
          "com.typesafe.akka" %% "akka-remote" % akkaVersion,

          "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
          "org.scalatest" %% "scalatest" % "2.2.4" % "test",
          "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion % "test"
        )
    ).dependsOn(api)

  lazy val api = project
    .settings(
      libraryDependencies ++=

        Seq(
          "com.lngbk" %% "management" % commonsVersion changing(),
          "com.lngbk" %% "api" % commonsVersion changing(),

          "com.typesafe.akka" %% "akka-actor" % akkaVersion,
          "com.typesafe.akka" %% "akka-remote" % akkaVersion,

          "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
          "org.scalatest" %% "scalatest" % "2.2.4" % "test",
          "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion % "test"
        )
    )

}


import sbt._
import Keys._
import com.typesafe.sbt.SbtMultiJvm
import com.typesafe.sbt.SbtMultiJvm.MultiJvmKeys.MultiJvm

object MyBuild extends Build {

  lazy val root = project.in(file(".")).aggregate(service, api)

  val akkaVersion = "2.4.10"
  val commonsVersion = "0.0.28-SNAPSHOT"

  lazy val service = project
    .settings(SbtMultiJvm.multiJvmSettings: _*)
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
        ),

      // make sure that MultiJvm test are compiled by the default test compilation
      compile in MultiJvm <<= (compile in MultiJvm) triggeredBy (compile in Test),
      // disable parallel tests
      parallelExecution in Test := false,
      // make sure that MultiJvm tests are executed by the default test target,
      // and combine the results from ordinary test and multi-jvm tests
      executeTests in Test <<= (executeTests in Test, executeTests in MultiJvm) map {
        case (testResults, multiNodeResults)  =>
          val overall =
            if (testResults.overall.id < multiNodeResults.overall.id)
              multiNodeResults.overall
            else
              testResults.overall
          Tests.Output(overall,
            testResults.events ++ multiNodeResults.events,
            testResults.summaries ++ multiNodeResults.summaries)
      },
      licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0")))
    ).configs(MultiJvm)
    .dependsOn(api)

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


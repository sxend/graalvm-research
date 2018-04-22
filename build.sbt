
organization := "arimitsu.sf"

name := "graalvm-research"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.5"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  Resolver.bintrayRepo("sxend", "releases"),
  Resolver.bintrayRepo("sxend", "snapshots")
)

libraryDependencies ++= {
  val akkaVersion = "2.5.12"
  val akkaHttpVersion = "10.1.1"
  val spec2Version = "4.0.4"
  Seq(
    "redis.clients" % "jedis" % "2.9.0",
    "mysql" % "mysql-connector-java" % "5.1.46",
    "org.apache.httpcomponents" % "httpcore" % "4.4.9",
    "org.apache.httpcomponents" % "httpcore-nio" % "4.4.9",
    "com.criteo.lolhttp" %% "lolhttp" % "0.9.3",
    "io.spray" %%  "spray-json" % "1.3.3",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "org.specs2" %% "specs2-html" % spec2Version % Test,
    "org.specs2" %% "specs2-junit" % spec2Version % Test,
    "org.specs2" %% "specs2-core" % spec2Version % Test
  )
}

publishMavenStyle := false

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

javacOptions ++= Seq("-source", "1.8")

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:reflectiveCalls",
  "-language:postfixOps"
)

assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if ps.last endsWith "bnd.bnd" => MergeStrategy.first
  case x => (assemblyMergeStrategy in assembly).value(x)
}

testOptions in Test += Tests.Argument(TestFrameworks.Specs2, "junitxml", "html", "console")

assemblyJarName in assembly := "main.jar"

mainClass in assembly := Some("gr.Bootstrap")

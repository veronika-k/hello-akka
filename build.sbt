name := "hello-akka-http"

version := "1.0"

scalaVersion := "2.12.1"

organization := "site.steveking"

libraryDependencies ++= {
  val akkaVersion = "2.5.3"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http"  % "10.0.9",
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "org.slf4j" % "slf4j-log4j12" % "1.7.25"
  )
}

name := "disaster-sandbox"

version := "1.0"

scalaVersion := "2.12.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.16"//,
  //"com.typesafe.akka" %% "akka-testkit" % "2.3.9",
  //"org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

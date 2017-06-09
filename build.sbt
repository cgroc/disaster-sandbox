name := "disaster-sandbox"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.16",
  //"com.typesafe.akka" %% "akka-testkit" % "2.3.9",
  //"org.scalatest" %% "scalatest" % "2.2.4" % "test"
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.6.5",
//  "com.fasterxml.jackson.core" % "jackson-core" % "2.6.5",
//  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.5",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.6.5"
)

dependencyOverrides ++= Set(
  "com.fasterxml.jackson.core" % "jackson-core" % "2.6.5",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.5"
)
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / name :=  "{{PROJECT_NAME}}"
ThisBuild / scalaVersion := "{{SCALA_VERSION}}"

lazy val root = (project in file("."))
  .settings(
    name := "{{PROJECT_NAME}}"
  )

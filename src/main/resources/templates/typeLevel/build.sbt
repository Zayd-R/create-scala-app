ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / name :=  "{{PROJECT_NAME}}"
ThisBuild / scalaVersion := "3.3.5"

lazy val catsEffectVersion          = "{{CATSEFFECT_VERSION}}"
lazy val http4sVersion              = "{{HTTP4S_VERSION}}"
lazy val doobieVersion              = "{{DOOBIE_VERSION}}"
lazy val pureConfigVersion          = "{{PURECONFIG_VERSION}}"
lazy val log4catsVersion            = "{{LOG4CATS_VERSION}}"
lazy val slf4jVersion               = "{{SLF4J_VERSION}}"
lazy val circeVersion               = "{{CIRCE_VERSION}}"

lazy val root = (project in file("."))
  .settings(
    name := name,
    libraryDependencies ++= Seq(
      "org.typelevel"         %% "cats-effect"         % catsEffectVersion,
      "org.http4s"            %% "http4s-dsl"          % http4sVersion,
      "org.http4s"            %% "http4s-core"         % http4sVersion,
      "org.http4s"            %% "http4s-ember-server" % http4sVersion,
      "org.http4s"            %% "http4s-circe"        % http4sVersion,
      "com.github.pureconfig" %% "pureconfig-core"     % pureConfigVersion,
      "io.circe"              %% "circe-generic"       % circeVersion,
      "org.tpolecat"          %% "doobie-core"         % doobieVersion,
      "org.tpolecat"          %% "doobie-hikari"       % doobieVersion,
      "org.tpolecat"          %% "doobie-postgres"     % doobieVersion,
      "org.typelevel"         %% "log4cats-slf4j"      % log4catsVersion,
      "org.slf4j"              % "slf4j-simple"        % slf4jVersion,

    )
  )

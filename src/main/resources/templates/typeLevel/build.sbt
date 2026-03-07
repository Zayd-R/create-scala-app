ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / name :=  "{{PROJECT_NAME}}"
ThisBuild / scalaVersion := "{{SCALA_VERSION}}"

//TODO: add ability to download latest stable version of the library instead of hardcoding
lazy val catsEffectVersion          = "3.6.3"
lazy val http4sVersion              = "0.23.33"

lazy val doobieVersion              = "1.0.0-RC12"
lazy val pureConfigVersion          = "0.17.10"
lazy val log4catsVersion            = "2.7.1"
lazy val tsecVersion                = "0.4.0"
lazy val scalaTestVersion           = "3.2.12"
lazy val scalaTestCatsEffectVersion = "1.4.0"
lazy val testContainerVersion       = "1.17.3"
lazy val logbackVersion             = "1.4.0"
lazy val slf4jVersion               = "2.0.17"
lazy val javaMailVersion            = "1.6.2"
lazy val stripeVersion              = "22.12.0"
lazy val circeVersion               = "0.14.15"


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

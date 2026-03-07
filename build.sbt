ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.5"

lazy val root = (project in file("."))
  .settings(
    name := "create-scala-app",
    libraryDependencies ++=  Seq(
      "com.lihaoyi" %%% "os-lib" % "0.11.3" // Check for the latest version on Maven
  ),

  )
.enablePlugins(ScalaNativePlugin)
nativeConfig ~= {
  _.withEmbedResources(true)
}

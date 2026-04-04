ThisBuild / name := "create-scala-app"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.5"

lazy val root = (project in file("."))
  .settings(
      name := "create-scala-app",
      Compile / mainClass := Some("App"),
      libraryDependencies ++=  Seq(
        "com.lihaoyi" %% "os-lib" % "0.11.8",
        "com.softwaremill.sttp.client4" %% "core" % "4.0.19",
        "com.lihaoyi" %% "ujson" % "4.4.3",
        "com.monovore" %% "decline" % "2.6.1"
    ),
  )
  .enablePlugins(NativeImagePlugin)

nativeImageGraalHome := file(sys.env.getOrElse("JAVA_HOME", "/home/zero/.sdkman/candidates/java/21.0.2-graal")).toPath
// generating manifest.txt file from templates to walk through the template tree
// os.resourcePath in "os-lib" was added in  a version incompatible with scala native

import scala.sys.process.*
lazy val generateTemplateManifests = taskKey[Seq[File]]("Generate manifest files for templates")

generateTemplateManifests := {
  val log = streams.value.log
  val resourcesDir = (Compile / resourceDirectory).value / "templates"

  if (!resourcesDir.exists()) {
    log.warn(s"Templates directory not found: $resourcesDir")
    Seq.empty
  } else {
    val templateDirs = resourcesDir.listFiles()
    log.info(s"✓ templateDirs for '${templateDirs.mkString("Array(", ", ", ")")}' ")

    templateDirs.flatMap { templateDir =>
      val templateName = templateDir.getName
      val manifestFile = templateDir / "manifest.txt"

      // Get all .template files in this directory
      val templateFiles = (templateDir ** "*.*").get
        .map(_.relativeTo(templateDir).get.getPath)
        .sorted

      // Write manifest
      IO.writeLines(manifestFile, templateFiles)

      log.info(s"Generated manifest for '$templateName' with ${templateFiles.size} files")

      Some(manifestFile)
    }.toSeq
  }
}

Compile / resourceGenerators += generateTemplateManifests.taskValue

nativeImageOptions ++= Seq(
  "--no-fallback",                    // fail if it cant fully compile, dont fall back to JVM
  "--initialize-at-build-time",       // analyze everything at compile time
  "-H:+ReportExceptionStackTraces",   // helpful during development
  "--install-exit-handlers",
  "-H:+UnlockExperimentalVMOptions",
  "-H:IncludeResources=.*",
  "-H:Log=registerResource:3"
)


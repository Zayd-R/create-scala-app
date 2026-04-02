//package modules
//
//import os.Path
//import Data.{ResourceLoader, Template}
//
//import scala.io.StdIn
//import scala.util.Try
//
// case object BuildFiles extends ResourceLoader {
//
//   private def fillSBTCommon(content: String, name: String, version: String) = {
//     content
//       .replace("{{PROJECT_NAME}}", name)
//       .replace("{{SCALA_VERSION}}", version)
//   }
//
//   // TODO: walk throw the file template and edit version
//   private def sbtTypelevel(): String = ???
//
//   def buildSbt(projectPath: Path, template: Template, appName: String): Try[String] = {
//     println("? Scala version: (3.8.2) HIT ENTER FOR DEFAULT VERSION")
//     val scalaVersion = Option(StdIn.readLine()).filter(_.nonEmpty).getOrElse("3.3.5")
//
//     template match {
//       case Template.BasicT =>
//         loadTemplate("basic", "build.sbt")
//           .map(fillSBTCommon(_, appName, scalaVersion))
//       case Template.TypeLevel =>
//         loadTemplate("typeLevel", "build.sbt")
//           .map(fillSBTCommon(_, appName, scalaVersion))
//       case Template.Unknown => ???
//     }
//   }
//
//   private def buildProperties: Try[String] = Try("sbt.version=1.12.3") //TODO: mae it retrive latest stable version
//
//   private def plugins: Try[String] = Try("""addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.4.17")""")
//
//   private def dckerComposeFile: Try[String] = loadTemplate("typeLevel", "docker-compose.yml")
//
//   override def write(projectPath: Path, template: Template, appName: String = ""): Try[Unit] = template match {
//     case Template.BasicT => writeCommon(projectPath, template, appName)
//     case Template.TypeLevel => writeTypeLevel(projectPath, template, appName)
//     case Template.Unknown => ???
//
//   }
//
//   private def writeCommon(projectPath: Path, template: Template, appName: String = "") = {
//     for {
//       sbtContent <- buildSbt(projectPath, template, appName)
//       buildPropertiesC <- buildProperties
//       pluginsContent <- plugins
//     } yield {
//       generatingFiles(projectPath, "build.sbt", sbtContent)
//       generatingFiles(projectPath / "project", "build.properties", buildPropertiesC)
//       generatingFiles(projectPath / "project", "plugins.sbt", pluginsContent)
//       ()
//     }
//   }
//
//   private def writeTypeLevel(projectPath: Path, template: Template, appName: String = "") = for {
//     _                <- writeCommon(projectPath, template, appName)
//     dockerContent    <- dckerComposeFile
//   } yield {
//     generatingFiles(projectPath, "docker-compose.yml", dockerContent)
//     ()
//   }
//
// }

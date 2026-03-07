package modules

import Data.{ResourceLoader, Template}
import os.Path

import scala.util.{Failure, Try}

case object SourceFiles extends ResourceLoader {

//TODO: error prone string , must switch to type like enum




  override def write(path: Path, template: Template, appName: String = ""): Try[Unit] = template match {
    case Template.BasicT => writeBasic(path, template)
    case Template.TypeLevel => writeTypeLevel(path, template)
    case Template.Unknown => ???
  }

  private def writeBasic(path: Path, template: Template): Try[Unit] = for {
    mainAppContent <- loadTemplate("basic", "App.scala.template")
  } yield {
    generatingFiles(path / "src" / "main" / "scala", "App.scala", mainAppContent)
  }


  private def writeTypeLevel(path: Path, template: Template): Try[Unit] = for {
    mainAppContent  <- loadTemplate("typeLevel", "App.scala.template")

    httpAPiContent  <- loadTemplate("typeLevel/modules", "HttpApi.scala.template")
    dataBaseContent <- loadTemplate("typeLevel/modules", "Database.scala.template")

    routesContent   <- loadTemplate("typeLevel/routes/http", "HealthRoutes.scala.template")

    appConfig       <- loadTemplate("typeLevel/config", "AppConfig.scala.template")
    DataBaseConfig  <- loadTemplate("typeLevel/config", "DatabaseConfig.scala.template")
    ServerConfig    <- loadTemplate("typeLevel/config", "ServerConfig.scala.template")

    resources <- loadTemplate("typeLevel/resources", "application.conf")

  } yield  {
    generatingFiles(path / "src" / "main"/ "resources", "application.conf", resources)

    val sourceFilesPath = path / "src" / "main" / "scala"

    generatingFiles(sourceFilesPath, "App.scala", mainAppContent)
    generatingFiles(sourceFilesPath / "modules", "HttpApi.scala", httpAPiContent)
    generatingFiles(sourceFilesPath / "modules", "Database.scala", dataBaseContent)

    generatingFiles(sourceFilesPath / "routes" / "http", "HealthRoutes.scala", routesContent)

    generatingFiles(sourceFilesPath / "config", "AppConfig.scala", appConfig)
    generatingFiles(sourceFilesPath / "config", "DatabaseConfig.scala", DataBaseConfig)
    generatingFiles(sourceFilesPath / "config", "ServerConfig.scala", ServerConfig)

  }
   
}



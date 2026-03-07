package Data

import Data.Template.printSuccess
import modules.{BuildFiles, SourceFiles}
import os.Path

import scala.io.StdIn
import scala.util.{Failure, Success, Try}

sealed trait Template {
  def retrieveT(): Try[Unit] = {
    val result = for {
      (appName, projectPath) <- settingUp()
      _ <- createDirectories(projectPath)
      _ <- BuildFiles.write(projectPath, this, appName)
      _ <- SourceFiles.write(projectPath, this)
    } yield {
      os.move(projectPath, os.pwd / appName)
      printSuccess(os.pwd / appName, appName)
    }
    result.recoverWith {
      case e: Throwable =>
        println(s"✗ Failed to create project: ${e.getMessage}")
        e.printStackTrace() // Show full stack trace for debugging
        Failure(e)
    }
  }

  private final def settingUp(): Try[(String, Path)] = Try {
    println("? Project name: ")
    val name = StdIn.readLine()
    val projectPath = os.temp.dir(prefix = name)
    println(s"Creating a new Scala project in ${os.pwd / name }")
    (name, projectPath)
  }

  def createDirectories(projectPath: Path): Try[Unit] = Try {
    print(s"✓ Generating common Directories ")
    os.makeDir.all(projectPath / "src" / "main" / "scala")
    os.makeDir.all(projectPath / "project")
    println("✓")
  }
}


object Template {
  case object BasicT extends Template

  case object TypeLevel extends Template:

    override def createDirectories(projectPath: Path): Try[Unit] = Try{
      super.createDirectories(projectPath)
      print(s"✓ Generating typeLevel Directories ")
      val rootPath = projectPath / "src" / "main"/ "scala"
      
      os.makeDir.all(projectPath / "src" / "main" / "resources")
      os.makeDir.all(rootPath / "config")
      os.makeDir.all(rootPath / "http" / "routes")
      os.makeDir.all(rootPath / "modules")
      println("✓")
    }


  case object Unknown extends Template:
    override def retrieveT(): Try[Unit] = {
      println(s"✗ Failed to create project: {Unkown template}")
      Failure(new IllegalArgumentException("Unkown template"))
    }


  def printSuccess(projectPath: Path, projectName: String): Unit = {
    println(Console.GREEN + Console.BOLD + "Success!" + Console.RESET + s" Created $projectName at $projectPath")
    println()
    println("Inside that directory, you can run:")
    println()
    println(Console.CYAN + "  sbt compile" + Console.RESET)
    println("    Compiles your project")
    println()
    println(Console.CYAN + "  sbt run" + Console.RESET)
    println("    Runs your application")
    println()
    println(Console.CYAN + "  sbt test" + Console.RESET)
    println("    Runs tests")
    println()

    println("We suggest that you begin by typing:")
    println()
    println(Console.CYAN + s"  cd $projectName" + Console.RESET)
    println(Console.CYAN + "  sbt run" + Console.RESET)
    println()
    println("Happy coding! 🚀")
    println()
  }
  private val templates: Map[String, Template] = Map(
    "basic" -> BasicT,
    "typelevel" -> TypeLevel
  )

  def apply(templateName: String): Template =
    templates.get(templateName.toLowerCase) match {
      case Some(value) => value
      case None => Unknown
    }
}
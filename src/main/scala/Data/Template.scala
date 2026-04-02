package Data

import Data.Template.printSuccess
import Data.TemplateContext
import modules.{ProjectCompiler, ProjectWriter, ResourceLoader, TemplatesProcessor, VersionResolver}
import os.Path

import scala.io.StdIn
import scala.util.{Failure, Success, Try}

sealed trait Template {

  val name: String
  val description: String
  val dependencies: Map[String, (String, String, String)] // name -> (group, artifact, fallBack Version)
  def generate(build: String, fresh: Boolean, compile: Boolean): Try[Unit] = {
    val result = for {
      (appName, projectPath) <- settingUp()
      templateFileListPaths <- ResourceLoader
        .genTemplateFiles(name)
        .recover { case e: Throwable =>
          throw new RuntimeException(s"Could not find template '$name', is it a valid template name?", e)
        }
      updateDependencies <- VersionResolver.resolveVersions(dependencies, fresh)
      templateContext = buildTemplateContext(appName, projectPath = projectPath, updateDependencies)
      _ <- processAllFiles(templateFileListPaths, templateContext)
      _ <- ProjectWriter.writeFinal(projectPath,
                                    os.pwd / appName
      ) // TODO: either prompt for path or as argument during intializing
      _ <- ProjectCompiler.compileProject(os.pwd / appName, compile)
    } yield {
      printSuccess(os.pwd / appName, appName)
    }
    result.recoverWith { case e: Throwable =>
      println(s"✗ Failed to create project: ${e.getMessage}")
      e.printStackTrace() // Show full stack trace for debugging
      Failure(e)
    }
  }

  def settingUp(): Try[(String, Path)] = Try {
    println("? Project name: ")
    val name = StdIn.readLine()
    if (name.isEmpty) throw new RuntimeException("Project name cannot be empty")
    val projectPath = os.temp.dir(prefix = name)
    println(s"Creating a new Scala project in ${os.pwd / name}")
    (name, projectPath)
  }

  def processAllFiles(
    templateFileListPaths: List[String],
    templateContext: TemplateContext
  ): Try[Unit] = {

    templateFileListPaths.foldLeft(Try(())) { case (acc, pathString) =>
      acc.flatMap(_ => processFile(pathString, templateContext))
    }
  }
  def processFile(pathString: String, templateContext: TemplateContext): Try[Unit] = {
    for {
      templateContent <- ResourceLoader.loadTemplate(name, pathString)
      fileContent <- TemplatesProcessor.processTemplatesFile(templateContent, templateContext)
      _ <- ProjectWriter.writeFile(pathString, templateContext.projectPath, fileContent)
    } yield ()
  }

  def buildTemplateContext(appName: String,
                           organization: String = "",
                           projectPath: Path,
                           dependencies: Map[String, String]
  ): TemplateContext = {
    // override for any tempalte that require more context adding it to placeholder variable
    val placeHolder = Map("organization" -> organization, "project_Name" -> appName)
    TemplateContext(projectPath, dependencies, placeHolder)
  }
}

object Template {

  val all: List[Template] = List(BasicT, TypeLevel)

  case object BasicT extends Template {
    override val name: String = "basic"
    override val description: String = "basic sbt project with main file"
    override val dependencies: Map[String, (String, String, String)] = Map.empty

  }

  case object TypeLevel extends Template {
    override val name: String = "typeLevel"
    override val description: String = "Typelevel stack with http4s, cats, doobie"
    override val dependencies: Map[String, (String, String, String)] = Map(
      "http4s" -> ("org.http4s", "http4s-core_3", "0.23.33"),
      "catsEffect" -> ("org.typelevel", "cats-effect_3", "3.6.3"),
      "doobie" -> ("org.tpolecat", "doobie-core_3", "1.0.0-RC12"),
      "pureConfig" -> ("com.github.pureconfig", "pureconfig-cor_3", "0.17.10"),
      "log4cats" -> ("org.typelevel", "log4cats-slf4j_3", "2.7.1"),
      "slf4j" -> ("org.slf4j", "slf4j-simple", "2.0.17"),
      "circe" -> ("io.circe", "circe-generic_3", "0.14.14")
    )
  }

  case object Unknown extends Template {
    override def generate(build: String, fresh: Boolean, compile: Boolean): Try[Unit] = {
      println(s"✗ Failed to create project: {Unkown template}")
      Failure(new IllegalArgumentException("Unkown template"))
    }

    override val name: String = "Unkown template"
    override val description: String = "Wrong template name fallback"
    override val dependencies: Map[String, (String, String, String)] = Map.empty
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
      case None        => Unknown
    }
}

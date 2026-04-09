package modules

import Data.BuildTool
import os.Path

import scala.util.Using
import scala.util.{Failure, Try}

object ResourceLoader {
  def loadTemplate(templateName: String, pathName: String): Try[String] = {
    Using(getClass.getResourceAsStream(s"/templates/$templateName/${pathName}")) { stream =>
      println(s"=== Loading Template ===")
      println(s"Template name: $templateName")
      println(s"File name: $pathName")
      println(s"Resource path: /templates/$templateName/${pathName}")
      scala.io.Source.fromInputStream(stream).mkString
    }
      .recoverWith { case e: Throwable =>
        println(s"✗ ERROR loading $templateName $pathName: ${e.getMessage}")
        Failure(new RuntimeException(s"Failed to load template $pathName", e))
      }
  }

  def genTemplateFiles(templateName: String, buildTool: BuildTool): Try[List[String]] = Try {
    val manifestStream = getClass.getResourceAsStream(s"/templates/$templateName/manifest.txt")
    scala.io.Source
      .fromInputStream(manifestStream)
      .getLines()
      .map(_.trim)
      .filter(line => line.nonEmpty &&  ( line.startsWith(buildTool.toString.toLowerCase) || line.startsWith("shared")))
      .toList
  }.recoverWith { case e: Throwable =>
    println(s"✗ ERROR loading-------------${e.getMessage} ---------- templte $templateName")
    Failure(new RuntimeException(s"Failed to load template in genTemplate ----------------------", e))
  }
//
//  def writeFile(path: Path, fileName: String, content: String) = Try {
//    print(s"✓ Generated $fileName   ---")
//    os.write(path / fileName, content)
//    println(" ✓ --- ")
//  }

  // def write(path: Path, template: Template, appName: String = ""): Try[Unit]

}

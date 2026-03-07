package Data

import os.Path

import scala.util.{Failure, Try}

trait ResourceLoader {
  def loadTemplate(templateName: String, fileName: String): Try[String] = {
    val resources = getClass.getResourceAsStream(s"/templates/$templateName/${fileName}")


    println(s"=== Loading Template ===")
    println(s"Template name: $templateName")
    println(s"File name: $fileName")
    println(s"Resource path: /templates/$templateName/${fileName}")
    Try(scala.io.Source.fromInputStream(resources).mkString)
      .recoverWith {
        case e: Throwable =>
          println(s"✗ ERROR loading $fileName: ${e.getMessage}")
          Failure(new RuntimeException(s"Failed to load template $fileName", e))
      }
  }

  def generatingFiles(path: Path, fileName: String, content: String) = Try {
    print(s"✓ Generated $fileName   ---")
    os.write(path / fileName, content)
    println(" ✓ --- ")
  }

  def write(path: Path, template: Template, appName: String = ""): Try[Unit]

}

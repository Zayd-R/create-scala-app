package modules

import Data.Template
import Data.TemplateContext
import os.Path

import scala.util.{Failure, Try}

object TemplatesProcessor {

  def processTemplatesFile(fileContent: String, templateContext: TemplateContext): Try[String] = Try {
    // combine all place-holders in one map
    val allPlaceHolders = {
      templateContext.dependencies.map((key, value) => s"{{${key.toUpperCase}_VERSION}}" -> value) ++
        templateContext.placeHolderContext.map((key, value) => s"{{${key.toUpperCase}}}" -> value)
    }
    // traverse the template content and repalce every occurence of a key
    val result = allPlaceHolders.foldLeft(fileContent) { case (accContent, (key, value)) =>
      accContent.replace(key, value)
    }

    // catch unfilled placeholders before writing to disk
    val remaining = "\\{\\{[^}]+\\}\\}".r.findAllIn(result).toList

    if (remaining.nonEmpty)
      throw new RuntimeException(s"Unresolved placeholders: ${remaining.mkString(", ")}")

    result
  }

}

//import os.*
//import sttp.client4.quick.*
//import sttp.client4.Response
//
//object Playground {
//
//  def main(args: Array[String]): Unit = {
//    testpalcehodler()
//  }
//
//  def testPAths() = {
//    val path = os.pwd
//    val pathString = "src/main/resources/application.conf"
//    os.RelPath(pathString)
//  }
//
//  def testpalcehodler(): Unit = {
//    val content = {
//      """lazy val catsEffectVersion          = "{{CATSEFFECT_VERSION}}"
//       lazy val http4sVersion              = "{{HTTP4S_VERSION}}"
//       lazy val doobieVersion              = "{{DOOBIE_VERSION}}
//    """"
//    }
//    val replacements = Map[String, String]("catsEffect" -> "123", "http4s" -> "321", "doobie" -> "222")
//
//    val result = replacements.foldLeft(content) { case (accContent, (key, value)) =>
//      accContent.replace(s"{{${key.toUpperCase}_VERSION}}", value)
//    }
//
//    println(result)
//
//  }
//
////  def main(args: Array[String]): Unit = {
////    val url = uri"https://libraries.io/api/maven/org.typelevel:cats-effect_3"
////
////    val response: Response[String] = quickRequest.get(url).send()
////    if (response.code.code == 200) {
////      import ujson.*
////
////      val json = ujson.read(response.body)
////      val ok = json("latest_stable_release_number")
////      println(ok)
////    }
////  }
//
////
////  def processTemplates(
////                        templateDir: Path,
////                        targetDir: Path,
////                        replacements: Map[String, String] = Map.empty,
////                        httpClient: Option[String => String] = None // e.g., function to fetch version
////                      ): Unit = {
////
////    // Ensure target exists
////    os.makeDir.all(targetDir)
////    scala.io.Source.fromInputStream(getClass.getResourceAsStream(s"/templates/basic/"))
////
////    for (src <- os.walk(templateDir)) {
////      println(s"the source is $src before if")
////      if (os.isFile(src)) {
////        // Compute relative path from template root
////        val rel = src.relativeTo(templateDir)
////
////        println(s"the relative is $rel")
////        println(s"the source is $src")
////
//////        // Remove .template extension if present
//////        val destRel = if (rel.last.endsWith(".template")) {
//////          rel / os.up / rel.last.stripSuffix(".template")
//////        } else rel
//////
//////        val dest = targetDir / destRel
////      }
////    }
////  }
//
//}

package modules

import scala.util.Try
import cats.syntax.all.*
import sttp.client4.quick.*
import sttp.client4.Response
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.*
import ujson.read
// TODO: this is an expermintal draft , still thinking if its better use future or add another layer of dependency for more async functionality
object VersionResolver {

  def getLatestStableVersions(group: String, artifact: String): Try[String] = {
    val url = uri"https://libraries.io/api/maven/${group}:${artifact}"
    Try(quickRequest.get(url).send())
      .filter(response => response.code.code == 200)
      .map(response => ujson.read(response.body).str)
  }

  def resolveVersions(dependencies: Map[String, (String, String, String)], fresh: Boolean): Try[Map[String, String]] =
    Try {
      if (!fresh) {
        dependencies.map { case (library, (group, artifact, fallBackV)) =>
          library -> fallBackV
        }
      } else {
        val allRequest = Future
          .traverse(dependencies.toSeq) { case (library, (group, artifact, fallBackV)) =>
            println(s"Fetching latest $library ...")
            Thread.sleep(1000)
            Future {
              val version = getLatestStableVersions(group, artifact).getOrElse {
                println(s"Failed to fetch new version for $library, falling back to defautl $fallBackV")
                fallBackV
              }
              library -> version
            }
          }
          .map(_.toMap)
        println("Fetching latest dependency versions...")
        Await.result(allRequest, 30.second)
      }
    }
      .recover { case e: Throwable =>
        println(s"Error while fetching latest version realsese due to ${e.getMessage}")
        println("falling back to default versions")
        dependencies.map { case (library, (group, artifact, fallBackV)) =>
          library -> fallBackV
        }
      }
}

package modules

import os.Path

import scala.util.Try

object ProjectCompiler {

  def compileProject(path: Path, compileFlag: Boolean): Try[Unit] = Try {
    os.proc("sbt", "compile")
      .call(
        cwd = path,
        stdout = os.ProcessOutput { (buffer, length) =>
          println(new String(buffer, 0, length))
        }
      )
  }
}

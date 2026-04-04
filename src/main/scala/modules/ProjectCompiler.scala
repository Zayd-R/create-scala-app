package modules

import Data.BuildTool
import os.Path

import scala.util.Try

object ProjectCompiler {

  def compileProject(path: Path, compileFlag: Boolean, build: BuildTool): Try[Unit] = Try {
    if (compileFlag) {
      os.proc( build.compileCommand )
        .call(
          cwd = path,
          stdout = os.ProcessOutput { (buffer, length) =>
            println(new String(buffer, 0, length))
          }
        )
    }
  }

}

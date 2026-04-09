package modules
import os.Path

import scala.util.Try
object ProjectWriter {

  def writeFile(filePathString: String, projectPath: Path, fileContent: String): Try[Unit] = Try {
    print(s"✓ Generated ${filePathString}   ---")
    os.write.over(projectPath / os.RelPath(filePathString.replaceFirst("^[^/]+/", "").replace(".template", "")), fileContent, createFolders = true)
    println(" ✓ --- ")
  }

  def writeFinal(projectPath: Path, destination: Path): Try[Unit] = Try {
    if (os.exists(destination)) {
      throw new RuntimeException(s"Directory already exists: $destination")
    } else {
      os.move(projectPath, destination)
    }
  }

}

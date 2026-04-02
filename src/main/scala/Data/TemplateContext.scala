package Data

import os.Path

case class TemplateContext(
  projectPath: Path,
  dependencies: Map[String, String],
  placeHolderContext: Map[String, String]
)

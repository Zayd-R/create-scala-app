package Data

enum BuildTool {
  case Sbt
  case Mill
  case Cli

  def compileCommand: Seq[String] = this match {
    case Sbt => Seq("sbt", "compile")
    case Mill => Seq("./mill", "compile")
    case Cli => Seq("scala-cli", "???")
  }
}
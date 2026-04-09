import Data.Template
import com.monovore.decline.*
import cats.syntax.all.*
import Data.BuildTool

object App {
  def main(args: Array[String]): Unit = {

//   The set of all available templates
    val validTemplates = Template.all.map(tempalte =>
      (tempalte.name.toLowerCase -> tempalte.description) ).toMap

    val listTemplates = Opts.flag("list", help = "List available templates")
    val stacks = Opts
      .argument[String]("template")
      .validate(s"unknown template name. Valid templates are: ${validTemplates.mkString(", ")}") { s =>
        println(s"template is $s  $validTemplates")
        validTemplates.contains(s)
      }

    // Available build tools TODO:
    val sbtFlag = Opts.flag("sbt", help = "Generate using sbt build").as(BuildTool.Sbt)
    val millFlag = Opts.flag("mill", help = "Generate using mill build").as(BuildTool.Mill)
    val cliFlag = Opts.flag("cli", help = "Generate using cli build").as(BuildTool.Cli)
    val buildOption: Opts[BuildTool] = sbtFlag.orElse(millFlag).orElse(cliFlag).withDefault(BuildTool.Sbt)

    // Fresh dependecies flag
    val fresh = Opts.flag("fresh", help = "Fetch latest stable dependencies").orFalse
    // compile the project
    val compile = Opts.flag("compile", help = "Compile the generated project").orFalse

    val tailCommand = Command(
      name = "create-scala-app",
      header = "Happy coding!!!"
    ) {
      (stacks, buildOption, fresh, compile).tupled orElse listTemplates
    }

    val customUsage = "Usage: create-scala-app <template> [--sbt | --mill | --cli] [--fresh] [--compile]"

    tailCommand.parse(args.toSeq) match {
      case Left(help) if help.errors.isEmpty =>
        // help was requested by the user, i.e.: `--help`
        println(help.toString.replaceFirst("(?m)^Usage:.*$", customUsage))

      case Left(help) =>
        // user needs help due to bad/missing arguments
        println(s"PARSING ERROR ${args.mkString("Array(", ", ", ")")}")
        System.err.println(help.toString.replaceFirst("(?m)^Usage:.*$", customUsage))

      case Right((template, build, freshDep, compileOp)) =>
        Template(template).generate(build, freshDep, compileOp)

      case Right(()) =>
        // user used --list command

        // ANSI codes
        val bold    = "\u001b[1m"
        val cyan    = "\u001b[36m"
        val dim     = "\u001b[2m"
        val reset   = "\u001b[0m"

        println()
        println(s"${bold}Available templates:${reset}")
        println()

        validTemplates.foreach { case (name, desc) =>
          val paddedName = name.padTo(14, ' ')  // aligns descriptions
          println(s"  ${cyan}${bold}${paddedName}${reset}  ${dim}${desc}${reset}")
        }

        println()
        println(s"  ${dim}Usage: create-scala-app <template> [--sbt | --mill | --cli] [--fresh] [--compile]${reset}")
        println()
    }

  }

}

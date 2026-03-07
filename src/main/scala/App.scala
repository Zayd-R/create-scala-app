import Data.Template

object App {
  def main(args: Array[String]): Unit =  {

    println("------------------------------------------------------------")
    val array: Array[String] = Array("basic", "typeLevel")

    args.toList.map(_.toLowerCase) match {
      case "-templates" :: empty  if empty.isEmpty => array.foreach(println)
      case template :: empty  if empty.isEmpty => Template(template).retrieveT()
      case _ => println("Usage: create-react-app [Template] [Name of project]")
    }
  }
  
  
   
}

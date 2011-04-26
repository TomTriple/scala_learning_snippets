object callbyname {

  def main(args: Array[String]): Unit = {


    var domainTld = ".de"
    def test(domainName: => String) = {
      println(domainName)
      domainTld = ".com"
      println(domainName)
    } 

    test("tomhoefer"+domainTld) 



    System exit -1


    object ControlFlow {

      // nur hier sind call-by-name parameter also lazy evaluation 
      def IfNot(exp: => Boolean)(trueBlock: => Unit) = new {
        def Otherwise(falseBlock: => Unit) = exp match {
          case true => falseBlock
          case false => trueBlock 
        }
      }

      // typ von T wird vom typ des block parameters abgeleitet...
      def ForEvery[T](block: T => Unit) = new {
        def In(list:Traversable[T]) = list foreach block
      }
    }
    
    
    import ControlFlow._

    val booleanExpr = true
    val items = Traversable("Scala", "Ruby", "Java")
    val builder = new java.lang.StringBuilder

    IfNot(booleanExpr) {
      ForEvery { item:String => 
        builder append item+" "
      } In items
      println("Result: "+builder)
    } Otherwise {
      println("no result available as booleanExpr is true") 
    }
  }
}

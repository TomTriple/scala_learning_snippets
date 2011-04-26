object patternmatching {


  def main(args: Array[String]): Unit = {


    

    /*
    val f:PartialFunction[Any, String] = {
      case it:Int => "found the number "+it
      case _:String => "another simple string"
    }

    f isDefinedAt "to be defined or not to be" // true
    f(2.3) // MatchError
    f("that is the question")

     System exit -1
    */ 

    
    object ValidEmail {
      private val reEmail = """([a-zA-Z0-9_-[.]]+)@([a-zA-Z0-9-]+).([\w]+)\z""".r 
      def unapply(str:String) = {
        str match {
          case reEmail(user, host, tld) => Some(user, host, tld)
          case _ => None
        }
      }
    } 

    case class Person(vorname:String, nachname:String, age:Int) 

    val list = List("thomas.hoefer@hs-augsburg.de", ("de", "Deutschland"), Person("max", "mustermann", 39), List(1,2,3,4)) 
    list foreach {
      case ValidEmail(user, host, tld) => println("username: "+user+", host: "+host+", tld: "+tld)
      case Person(vor, nach, alter) => println("Person: "+vor+", "+nach+", "+alter+" jahre")
      case List(_:Int, _:Int, 3, _*) => println("got a 3 at the third index")
      case x :: xs => println("list-head: "+x+", elements tail: "+xs.length)
    }



    val reEmail = """([a-zA-Z0-9_-[.]]+)@([a-zA-Z0-9-]+).([\w]+)\z""".r
    list foreach { it => 
      if(it.isInstanceOf[Person]) { 
        val person = it.asInstanceOf[Person]
        println("Person: "+person.vorname+", "+person.nachname+", "+person.age+" jahre") 
      } else if(it.isInstanceOf[String]) {
        val reEmail(user, host, tld) = it.asInstanceOf[String]
        println("username: "+user+", host: "+host+", tld: "+tld) 
      } else if(it.isInstanceOf[Pair[String,String]]) {
        val pair = it.asInstanceOf[Pair[String,String]] 
        val k = pair._1
        val v = pair._2
        println("matched Tuple: ("+k+", "+v+")")
      } else if(it.isInstanceOf[List[Int]]) {
        val liste = it.asInstanceOf[List[Int]]
        val x = liste.head
        val xs = liste.tail
        println("list-head: "+liste.head+", elements tail: "+liste.tail.length)
      }
    }

    



  }
  
}

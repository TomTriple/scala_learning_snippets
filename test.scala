object test {


  def main(args: Array[String]): Unit = {




    val liste = List("a", "b", "c", "d", "e")
    println(liste.take(2).toList.reverse)
    println(liste.take(2).reverse.toList)



    /*
    sealed case class Test(val str:String, val age:Int)
    case class TestA(override val str:String, override val age:Int) extends Test(str, age)
    case class TestB(override val str:String, override val age:Int) extends Test(str, age)

    val t = List(TestA("thomas", 29), TestB("tom", 22))
    
    t foreach {
      case TestA(_, _) => println("A")
      case TestB(_, _) => println("B")
      case Test(_, _) => println("---")
    }
    */


    /*
    class Tom(var data:List[String])
    object Tom {
      def apply(arg:String*) = new Tom(arg.toList)
      def unapply(a:Tom) = Some(a.data)
    }

    val tom = Tom("frohes", "neues", "jahr")
    tom match {
      case Tom(liste:List[String]) => println(liste.size)
      case _ => println("eerror.......")
    }
    */


/*
    def test(block: => AnyVal) {
      block
      block
    }

    test {
      println("hallo")
    }
*/





    /*
    class Location(
      var lat:Float,
      var long:Float
    )

    

    var list = List(new Location(1,1), new Location(2,2), new Location(3,3), new Location(4,4))
    var min = 10.2f
    var max = 20.3f

    def test(min:Float, max:Float, list:List[Location]) {
      list(0).lat = 99
    }

    var sublist = list.take(2)
    println(sublist(0).lat)
    test(min, max, sublist)
    println(sublist(0).lat)
    */



    /*
    import concurrent.ops._
    val q = new java.util.concurrent.LinkedBlockingQueue[String]


    spawn {
      println("in: ") 
      while(true) {
        val in = readLine
        q.add(in) 
      }
    }


    while(true) {
      val el = q.take
      println("die eingabe war: "+el)
    }
    */




    /*
    trait Observer {
      protected type EventHandler
      protected val observers = new collection.mutable.Queue[EventHandler]
      def onUpdate(observer:EventHandler) = observers += observer
      def notifyListeners(block:EventHandler => Unit) = observers foreach {block(_)} 
    } 
    

    
    class A {
      type EventHandler = (String, Int) => Unit
      val listener = new Observer { type EventHandler = String => Unit }
      listener onUpdate { str =>
        println("str: "+str) 
      }
      def test = listener.notifyListeners {_("asdf")}
    }
    

    val a = new A 
    a.test
    */


  }


}

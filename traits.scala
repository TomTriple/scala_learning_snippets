object traits {

  def main(args: Array[String]): Unit = {


    
    import collection.mutable.Queue

    trait ObservableTrait {
      private type Listener = () => Unit
      private val queue = new Queue[Listener]()
      def addObserver(observer: => Unit) = queue.enqueue(observer _)
      def notifyObservers() = queue foreach {_()}
    }

    class Component(val id:Int, var name:String)

    val comp = new Component(1, "Mustermann") with ObservableTrait 
    comp addObserver { println("first event handler...") }
    comp addObserver { println("second event handler...") }
    comp notifyObservers

    System exit -1
    



    // Traits -> Aspektorientierung, Objektdekoration, Querschnittsbelange
    // TODO: als case class gehts ned...?!



    trait Service {
      def executePayment(amount:Float):Boolean
    }

    class PaymentService(forename:String, surename:String) extends Service {
      def executePayment(amount:Float):Boolean = {
        // process payment and mimic successful execution by returning true 
        true 
      }
    }

    trait PaymentLogger extends Service {
      abstract override def executePayment(amount:Float) = {
        log("starting payment for "+amount+" Euro")
        val result = super.executePayment(amount) 
        result match {
          case true => log("success!")
          case false => log("failure...")
        }
        result 
      }

      def log(msg:String) = println(msg) 
    }

    val service = new PaymentService("max", "mustermann") with PaymentLogger
    service.executePayment(899) 


    /*
    type UserData = {
      def username:String
      def password:String
    }

    trait PaymentAuth extends Service {
      this:UserData =>
      abstract override def connect = {
        log("performing db-lookup for user "+username+"...")
        val result = performDbLookup
        result match {
          case true => log("user "+username+" authenticated!"); super.connect
          case false => log("login failed"); throw new IllegalArgumentException("Invalid login data")
        }
      }
      private def performDbLookup = username == "max" && password == "mustermann"
    }
    */






    

  }

}

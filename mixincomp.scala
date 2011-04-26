
object mixincomp {

  def main(args: Array[String]): Unit = {

    
    // mixin composition am beispiel des cakepattern (DI)
    // http://jonasboner.com/2008/10/06/real-world-scala-dependency-injection-di.html

    
    object Country extends Enumeration {
      val GER = Value("Germany")
      val AUS = Value("Austria")
      val SWI = Value("Switzerland")
    } 

    case class Product(val id:Int, val name:String)
    case class Customer(val name:String, street:String, zip:Int, city:String, country:Country.Value)

    trait OrderMailerComponent { 
      // val orderMailer wird zu einem componenten-namespace
      val orderMailer:OrderMailer
      class OrderMailer { 
        def send = {
          println("Sent mail to customer...")
        }
      }
    }

    trait OrderPersistenceComponent {
      val orderPersistence:OrderPersistence
      class OrderPersistence {
        def save = {
          println("Saved order for products in database...")
        }
      }
    } 

    abstract class OrderSystem(val products:Traversable[Product], val customer:Customer) extends OrderPersistenceComponent
       with OrderMailerComponent

    val products = List(Product(1, "Programing in Scala"), Product(2, "Advanced Scala"))
    val customer = Customer("max mustermann", "musterstrasse 12", 12345, "musterhausen", Country.GER)

    // default versions are production dependencies 
    val orderSystem = new OrderSystem(products, customer) {
      val orderMailer = new OrderMailer
      val orderPersistence = new OrderPersistence 
    }

    // inject mocked versions of system dependencies 
    val mockedOrderSystem = new OrderSystem(products, customer) {
      val orderMailer = new OrderMailer {
        override def send = {
          println("mocked sending a real mail...")
        }
      }
      val orderPersistence = new OrderPersistence {
        override def save = {
          println("mocked saving an order...")
        }
      }
    }

    // use in production environment -> Konkret mit ScalaTest implementieren oder so ok?! 
    println("production env: ")
    orderSystem.orderPersistence.save
    orderSystem.orderMailer.send

    // use in test system
    println("test env: ") 
    mockedOrderSystem.orderPersistence.save
    mockedOrderSystem.orderMailer.send

    
  }

}

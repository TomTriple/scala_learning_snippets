object di {


  def main(args: Array[String]): Unit = {





    trait OrderPersistenceComponent { 
      // orderPersistence becomes a component-namespace in the OrderSystem
      val orderPersistence:OrderPersistence
      abstract class OrderPersistence {
        def save:Unit
      }
    }
    
    trait OrderMailerComponent {
      // orderMailer also becomes a component-namespace in the OrderSystem
      val orderMailer:OrderMailer
      class OrderMailer {
        def send:Unit
      }
    }

    abstract class OrderSystem extends OrderMailerComponent with OrderPersistenceComponent

    val orderSystem = new OrderSystem {
      // inject production version of dependencies... 
      val orderMailer = new OrderMailer {
        override def send = println("Sent mail to customer...") 
      }
      val orderPersistence = new OrderPersistence {
        override def save = println("Saved order for products in database...")
      }
    }

    // inject mocked versions of system dependencies by overriding their "critical" method
    val mockedOrderSystem = new OrderSystem {
      val orderMailer = new OrderMailer {
        override def send = println("mocked sending a real mail...")
      }
      val orderPersistence = new OrderPersistence {
        override def save = println("mocked saving an order...")
      }
    }

    val a = new OrderMailer

    println("production environment: ")
    orderSystem.orderPersistence.save
    orderSystem.orderMailer.send

    println("test environment: ")
    mockedOrderSystem.orderPersistence.save
    mockedOrderSystem.orderMailer.send


/*
    println(„production env: „)
    orderSystem.orderPersistence.save
    orderSystem.orderMailer.send

    println(„test env: „)
    mockedOrderSystem.orderPersistence.save
    mockedOrderSystem.orderMailer.send

    > production env:
    > Saved order for products in database...
    > Sent mail to customer...
    > test env:
    > mocked saving an order...
    > mocked sending a real mail...

*/




  }

}

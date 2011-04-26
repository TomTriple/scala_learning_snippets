object selftypeanno {

  def main(args: Array[String]): Unit = {

    trait AbstractPaymentService {
      def executePayment(amount:Float):Boolean
    }

    trait PaymentService extends AbstractPaymentService {
      // use opened socket from "connect"-method for example and assume that payment was successful by simply returning true
      def executePayment(amount:Float) = {
        println("payed "+amount+" Euro")
        true 
      }
    }
 
    class PaymentGateway(val username:String, val password:String) { this: AbstractPaymentService =>
      def pay(amount:Float) = executePayment(amount) 
    }

    val gateway = new PaymentGateway("max", "mustermann") with PaymentService
    gateway.pay(450.0f)

  }

}

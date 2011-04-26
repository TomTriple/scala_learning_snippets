object implicitconversion {

  def main(args: Array[String]): Unit = {

    class Person(vorname:String, nachname:String) {
      def aaa = "aaa"
    }
    class RichPerson(person:Person) {
      def texte = "hallo"; this
    }

    implicit def person2RichPerson(person:Person) = new RichPerson(person)

    val p = new Person("thomas", "hoefer") 
    println(p.aaa)
    val rich = p.texte 
    println(p.aaa) 

    
  }

/*
  class RichInt(private val i:Int) {
    def daysFromNow = {
      import java.text.SimpleDateFormat
      import java.util.{GregorianCalendar, Calendar => C}
      val gc = new GregorianCalendar
      gc add(C.DAY_OF_YEAR, i)
      val sdf = new SimpleDateFormat("dd.MM.yyyy")
      sdf format gc.getTime 
    }
  }

  object RichInt {
    def apply(i:Int) = new RichInt(i)
  } 

  implicit def int2RichInt(i:Int) = RichInt(i)


  def main(args: Array[String]): Unit = {
    println(2 daysFromNow)
    // built-in conversions for String
    println("scala " * 5) 
    println("true".toBoolean.asInstanceOf[AnyRef].getClass) 
  }
*/
}

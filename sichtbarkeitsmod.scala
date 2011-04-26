package components { 

  class ComponentA {
    protected def method = "myMethod"
  }

  class AnotherComponent {
    private val compA = new ComponentA
    // would be possible in java as AnotherComponent and ComponentA belong to the same package
    //override protected def handle = compA.method 
  }

  protected[components] class A {
    
  }

}


object sichtbarkeitsmod {


  def main(args:Array[String]) { 


    val button = new javax.swing.JButton 
    button addMouseListener new java.awt.event.MouseAdapter {
      override def mouseClicked(e:java.awt.event.MouseEvent) = {
        println("clicked...") 
      }
    }


  }

}



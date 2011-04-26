package Library { 
  object Module {
    class C1
    def printOut(str:String) = println(str)
    val func = {str:String => printOut(str)}
    object Submodule {
      val funcUppercase = {str:String => printOut(str.toUpperCase)} 
    }
  }
}


object imports {

  def main(args: Array[String]): Unit = {
    
    import Library.{Module => Mod}
    import Mod._
    import Mod.Submodule.funcUppercase 

    val instanceOfC1 = new C1
    func("called first function") 
    funcUppercase("called second function")


    class A(str:String) {
      val name = "thomas"
      def test = str.toUpperCase+" "+name 
    }

    val a = new A("hoefer")
    val fff = a.name
    val m = a.test _ 
  }

}

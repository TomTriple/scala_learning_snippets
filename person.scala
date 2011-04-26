object person {
  
  class Person {
    private var _vorname = ""
    private var _nachname = ""

    def vorname = _vorname
    def nachname = _nachname
    def vorname_=(_vorname:String) = this._vorname = _vorname.toUpperCase
    def nachname_=(_nachname:String) = this._nachname = _nachname.toUpperCase 
  } 

  def main(args: Array[String]): Unit = {
    val p = new Person
    p.vorname = "max"
    p.nachname = "mustermann"

    println(p.vorname+", "+p.nachname)
  }

}
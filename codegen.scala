object codegen {

  class Person(var vorname:String, var nachname:String)

  class Person2(_vorname:String, _nachname:String) {

    def vorname() = _vorname
    def vorname_=(vorname:String) = _vorname = vorname

    def nachname() = _nachname
    def nachname_=(nachname:String) = _nachname = nachname

  }


  val p1 = new Person("thomas", "hoefer")
  val p2 = new Person2("t", "h")

  println(p1.vorname+", "+p1.nachname)
  println(p2.vorname+", "+p2.nachname)


  p1.vorname = "hans"
  p1.nachname = "hansinger"

  p2.vorname = "flo"
  p2.nachname = "flogginger"
  println(p1.vorname+", "+p1.nachname)
  println(p2.vorname+", "+p2.nachname)

}

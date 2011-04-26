object option_some_none {

  def main(args:Array[String]) {


    
    val values = List(Some("result1"), Some("result2"), None, Some("result3"), None)
    val s = for(v <- values) yield v.get 
    println(s) 

    


    /*
    val valSome = Some(5)
    val valNone = None
    for(option <- List(valSome, valNone)) {
      println(option)
    }
    */
    


/*
    public String noneReturn() {
      // some calculcation
      return null;
    }

    String value = noneReturn();
    if(value == null)
      value = "--default--";

    System.out.println(value)




    def noneReturn = {
      // some calculation...
      null
    }


    var result = noneReturn
    if(result == null)
      result = "--default--"
    

    //println(noneReturn.getOrElse("-- default --"))





    // ohne Options
    def trad = None


    var tradwert = trad
    if(tradwert == null) {
      // send error mail to administrator
      // ...and add corresponding error-message to log
      // ...and return the default value:
      tradwert = "bla" 
    }
    */
  }
}

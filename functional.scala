object funktional {


  def main(args:Array[String]) {


    /*
    case class Student(val vorname:String, val nachname:String, val age:Int, val subjects:String)


    val students = List(
      Student("alvin", "chipmunk", 23, "geschichte;politik;informatik"),
      Student("theodor", "chipmunk", 19, "sport;informatik;mathematik"),
      Student("bill", "musician", 37, "musik")
    )
    val onlyScientists = { stud:Student => stud.subjects.split(";").contains("informatik") }
    val printName = { stud:Student => println(stud.vorname+" "+stud.nachname) }

    students filter onlyScientists foreach printName
    */

    




    // aufteilung in studenten, die das beliebteste fach studieren und die, die es nicht studieren
    
    case class Student(val vorname:String, val nachname:String, val age:Int, val subjects:String)

    val students = List(
      Student("alvin", "chipmunk", 23, "geschichte;politik;informatik"),
      Student("simon", "chipmunk", 22, "sport;informatik;elektrotechnik"),
      Student("theodor", "chipmunk", 19, "sport;informatik;mathematik"),
      Student("bill", "musician", 37, "musik")
    )
    
    val resPair = students.flatMap {
      _.subjects split(";")
    }.foldLeft(Map.empty[String, Int].withDefaultValue(0)) { (acc, it) =>
      acc.updated(it, acc(it) + 1)
    } reduceLeft { (acc, it) => if(it._2 > acc._2) it else acc } 

    val result = students.filter { _.subjects.split(";").contains(resPair._1) }.foldLeft(List[String]()) { (acc, it) =>
      it.vorname+" "+it.nachname :: acc 
    }.mkString(", ")
    
    println(result+" studieren allesamt "+resPair._1)
    


  }

}



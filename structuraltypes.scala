object structuraltypes {

  def main(args: Array[String]): Unit = {

    import scala.io.Source
    
    type Readable = { 
      def read:String
    }

    case class FileReader(val file:String) {
      private lazy val source = Source.fromFile(file)
      def read = source.mkString 
    }

    object stringReader {
      def read = "string-reader read"
    }

    // sources weiß nur das die Objekte die read-Methode unterstützen...
    // die vererbungshierarchie der XYZReader bleibt sauber und von dieser Tatsache
    // verschont 
    object sources extends Traversable[Readable] { 
      private var readers = List.empty[Readable]
      def <<(reader:Readable) = readers ::= reader
      def foreach[B](f:Readable => B):Unit = readers foreach f  
    } 

    sources << new { 
      def read = Source.fromURL("http://www.google.de").mkString
    }
    sources << FileReader("structuraltypes.scala")
    sources << stringReader

    sources foreach { reader =>
      println("inhalt: " + reader.read)
    }
  }
} 
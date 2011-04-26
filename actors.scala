    // actors:
    // - share-nothing, message-passing model
    // - thread-ähnliche einheit die unabhängig voneinander sind, mit einer mailbox, über die messages (objekte) empfangen werden
    // - actoren kommunizieren untereinander, indem sie sich gegenseitig messages schicken (ohne lock und shared memory):
    //     - send    = asynchron
    //     - receive = wird nicht unterbrochen
    //     - gesendete nachrichten verbleiben in der mailbox des empfänger-actors, bis dieser receive aufruft
    //     - receive nimmt eine partialfunction entgegen um nachrichten zu filtern, die der actor behandeln kann
    //     - eine receive oder receiveWithin-Aufruf behandelt eine einzige message
    // - actor-subsystem verwaltet intern mehrere threads für die eigene benutzung (pro actor ein thread)
    // - aktueller thread als actor: Actor.self (actor <-> testen/spielen in console
    // - receive blockt so lange, bis messages verfügbar sind -> receiveWithin(waitTime), wartet nur "waitTime" Millisekunden
    //
    // allgemeines zu threading
    // - jvm oft millionen von objekten kann aber nur ein paar tausend threads haben bis speicher überläuft (thread-objekte teuer)
    // - context-switch zwischen threads braucht oft hundert bis tausend prozessor-zyklen
    // - folge -> thread-erzeugung & context-switch so sparsam wie möglich, daher alternative zu receive, nämlich react
    // - actor-subsystem berücksichtigt intern die anzahl der kerne bei der thread-erzeugung
    //
    // receive & react
    // - gemeinsamkeit: beide nehmen eine partialfunction an
    // - unterschied: react arbeitet message-handler ab, kehrt aber nicht zurück (return-type: Nothing da interne Exception)
    // - folge: react bevorzugen, da kein return nötig. intern actor-bibliothek den aktuellen thread für nächsten actor verwenden
    //
    // actor-style
    // - actoren sollten nicht blocken, da dann keine neuen anfragen entgegen bearbeitet werden können
    // - aktoren sollten nur über messages miteinander kommunizieren - jede andere art geht mit altbekannten threading-problemen einher.
    //   allerdings ist es durchaus möglich actoren mit concurrent-utilities zu verwenden (ConcurrentHashMap shared mit mehreren actors)
    // - bevorzuge immutable-datenstrukturen und -objekte
    //   + innerhalb von act kann sequentiell gedacht werden. act gehört nur zu einem einzigen thread (stimmt bei react zwar nur
    //     bedingt, aber wird intern über ausgeglichen)
    //   + shared data bei actors sind aber evtl in den message-objekten enthalten!
    //   + diese daher: immutable, nur kopieren der originaldaten oder diese werden in zukunft weder gelesen noch geschrieben
    //   + immutable daten in parallelen systemen sind i.d.R. zu bevorzugen da diese einfach implementiert & threadsafe sind
    // - messages sollten selbstbeschreibend sein -> case-classes für jede art von message einsetzen





object actors {

  import scala.actors.Actor
  import Actor._
  import concurrent.ops._

  def main(args: Array[String]): Unit = {


    // the request-messages 
    case class Length()
    case class Get(key:Symbol) 
    case class Set(key:Symbol, value:Any)
    // the response messages
    case class LengthResponse(size:Int)
    case class GetResponse(key:Symbol, value:Option[Any])

    class KvStore extends Actor {
      private[this] val store = collection.mutable.Map.empty[Symbol, Any]
      def act() = reactWithin(500) {
        case Length() => reply(LengthResponse(store.size)); act()
        case Get(key:Symbol) => reply(GetResponse(key, store.get(key))); act()
        case Set(key:Symbol, value:Any) => store.put(key, value); act(); 
        case _ => new RuntimeException("unknown message sent to actor") 
      }
    }
    
    val kvStore = new KvStore
    kvStore.start 
    spawn {
      kvStore ! Set('name, "thomas hoefer")
      kvStore ! Set('city, "augsburg")
    }
    kvStore ! Length()
    kvStore ! Get('name)
    kvStore ! Get('city)

    while(true) {
      self.receive {
        case LengthResponse(length) => println("size of store: "+length)
        case GetResponse(key, value) => println("got "+value.getOrElse("- nothing -")+" for "+key)
      }
    }
    



    
  /*
    spawn { 
      kvStore !? Get('name) match {
        case Some(name) => println("name: "+name)
        case None => println(":name not found")
      }
      kvStore !? Get('city) match {
        case Some(city) => println("ort: "+city)
        case None => println(":city not found")
      }
    }
    */


  }

}

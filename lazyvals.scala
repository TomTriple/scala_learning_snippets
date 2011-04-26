object lazyvals {

  def main(args: Array[String]): Unit = {

    /**
     *
     * class NetworkService {
     *    private static String service = null;
     *
     *
     *    public static NetworkService getInstance() {
     *      if(service == null) {
     *        // opening database/socket/service that may block
     *        service = "Expensive resource creation..."; 
     *      }
     *      return service;
     *    }
     * }
     *
     *
     * NetworkService service = NetworkService.getInstance(); 
     *
     */ 


    // abstrahiert java-muster mit sprachmitteln weg 
    class NetworkService {
      lazy val expensiveResource = {
        // opening database/socket/service that may block
        "Expensive resource creation..."
      }
    }

    val service = new NetworkService
    // Expensive resource currently not created
    // Until now...
    println(service.expensiveResource) 

  }

}

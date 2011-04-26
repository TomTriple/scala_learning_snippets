object dsl {

  object CrawlerDSL { 

    import io.Source
    import java.util.concurrent.{Executors, TimeUnit}
    import java.io.File
    import java.net.{URL, URI}
    import javax.imageio.ImageIO
    private val SEP = File.separator


    protected var whitelist:Set[String] = null
    protected val threadPool = Executors.newFixedThreadPool(10)
    protected var configs = List.empty[Config]

    protected var config:Config = null
    protected var relativeItemPath:String = null

    
    protected def waitForWorkers = {
      threadPool.shutdown 
      threadPool.awaitTermination(365, TimeUnit.DAYS) 
    }

    protected def spawnWorker(block: => Unit) = threadPool execute new Runnable {
      override def run() = block 
    }

    protected def startDownload = {
      spawnWorker {
        val item = PageItem(config.url, relativeItemPath)
        val bi = ImageIO read item.itemUrl
        ImageIO.write(bi, item.itemFormat, new File(config.folder+SEP+item.itemFilename))
        println("Finished: "+item.itemUrl)
      }
    }

    protected def createDownloadFolderIfNeeded = {
      val file = new File(config.folder) 
      if(! file.exists) 
        file.mkdir
    }

    protected def formats = ((whitelist union config.formats).mkString("|"))

    def download = this
    def always(media:String*) = { whitelist = media.toSet } 
    def from(block: => Unit) = {
      block
      configs foreach { config =>
        this.config = config
        createDownloadFolderIfNeeded 
        val wanted = formats
        val isWantedItem = (""".* src="(.*."""+wanted+""")" .*""").r
        Source.fromURL(config.url).getLines.foreach {
          case isWantedItem(relativeItemPath) => this.relativeItemPath = relativeItemPath; startDownload
          case _ => ("no match found!")
        }
      }
      waitForWorkers 
    }
    def << (my:Config) = configs ::= my


    protected case class PageItem(val url:URL, val path:String) {
      def itemUrl = URI.create(url.toString).resolve(path).toURL
      def itemFormat = itemFilename.split("[.]").reverse(0) 
      def itemFilename = itemUrl.toString.split("/").reverse(0)
    }

    
    protected class Config(var url:URL) {
      
      var _include:Set[String] = null
      var _convert:String = null
      var _folder:String = null
      
      def formats(media:String*) = { _include = media.toSet; this }
      def convert(convert:String) = { _convert = convert; this }
      def folder(folder:String) = { _folder = folder; this } 
      def formats = _include
      def convert = _convert 
      def folder = _folder
      
      CrawlerDSL << this 
    }

    implicit def string2Config(str:String) = new Config(new URL(str)) 

  }


  def main(args: Array[String]): Unit = { 

    // Regex geht noch nicht!

    import CrawlerDSL._

    download always ("png") 
    from {
      "http://www.ard.de" formats ("jpg", "gif") folder "ard-images"
      "http://www.zdf.de" formats ("bmp") folder "zdf-images"
    }
  } 
}

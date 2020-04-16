//TCP Weblog Server
//This notebook simulates a log producer able to send web server logs to a client connected through a TCP connection.


// This is the location of the unpackaged files. Update accordingly
val serverPort = 9999
val logsDirectory = "/Users/la-hien.nguyen/Desktop/github/hadoop-projects/processNASAWebServerLogs/NASA-weblogs/nasa_dataset_july_1995"

import java.sql.Timestamp
case class WebLog(host:String, 
                  timestamp: Timestamp, 
                  request: String, 
                  http_reply:Int, 
                  bytes: Long
                 )

// val connectionWidget = ul(5)
// val dataWidget = ul(20)
//A Simple TCP server implementation

// Simple multithreaded server
import java.net._
import java.io._
import java.sql.Timestamp
import scala.concurrent.Future
import scala.annotation.tailrec
import scala.collection.JavaConverters._
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import scala.concurrent.ExecutionContext.Implicits.global

class SocketHandler(sparkSession: SparkSession, port: Int, data: Dataset[WebLog]) {
  val logDelay = 500 // millis
  @volatile var active = false 
  
  // non blocking start of the socket handler
  def start() : Unit = {
    active = true
    new Thread() {
      override def run() { 
        // connectionWidget.append("Server starting...")
        acceptConnections()
        // connectionWidget.append("Server stopped")
      }
    }.start()
  } 
  
  def stop() {
    active = false
  }
  
  @tailrec
  final def acceptConnections(): Unit = {
    val server: ServerSocket = new ServerSocket(port)
    val socket = server.accept()
    // connectionWidget.append("Accepting connection from: " + socket)
    serve(socket)
    if (active) {
      acceptConnections() 
    } else {
      () // finish recursing for new connections
    }
  }
  
  // 1-thread per connection model for example purposes.
  def serve(socket: Socket) = {
    import sparkSession.implicits._
    val minTimestamp  = data.select(min($"timestamp")).as[Timestamp].first
    val now = System.currentTimeMillis
    val offset = now - minTimestamp.getTime()
    val offsetData = data.map(weblog => weblog.copy(timestamp = new Timestamp(weblog.timestamp.getTime+ offset)))
    val jsonData = offsetData.toJSON
    val iter = jsonData.toLocalIterator
    // .asScala
    new Thread() {
      override def run() {
        val out = new PrintStream(socket.getOutputStream())
        // connectionWidget.append("Starting data stream for: " + socket.getInetAddress() + "]")
        while(iter.hasNext && active) {
          val data = iter.next()
          out.println(data)
        //   dataWidget.append(s"[${socket.getInetAddress()}] sending: ${data.take(40)}...")
          out.flush()
          Thread.sleep(logDelay)
        }
        out.close()
        socket.close()
      }
    }.start()
  }
}




val rawLogs = session.read.json(logsDirectory)

import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType
val preparedLogs = rawLogs.withColumn("http_reply", $"http_reply".cast(IntegerType))
val weblogs = preparedLogs.as[WebLog]

val server = new SocketHandler(session, serverPort, weblogs)






//connectionWidget

//dataWidget
// Start the server accept process

server.start()
//Stop the server
//After experimenting with the TCP stream, execute the close method below to stop the data stream.

//DO NOT stop the server right after starting it. The command is commented out to prevent accidental execution. Uncomment and execute to stop this producer.


//server.stop()


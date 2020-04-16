//Simple Weblog Analytics - The Streaming Way


//This notebook requires a local TCP server that simulates the Web server sending data.

//Please start the weblogs_TCP_Server notebook before running this one.

//To connect to a TCP source, we need the host and the port of the TCP server.



val host = "localhost"
val port = 9999
//We use the TextSocketSource in Structured Streaming to connect to the TCP server and consume the text stream.
//This Source is called socket as the short name we can use as format to instantiate it.

//The options needed to configure the socket Source are host and port to provide the configuration of our TCP server.


val stream = session.readStream
  .format("socket")
  .load()


import java.sql.Timestamp
import org.apache.spark.sql.{Encoder,Encoders}
case class WebLog(host:String, 
                  //timestamp: Timestamp, 
                  //request: String, 
                  http_reply:Int, 
                  bytes: Long
                 )
// We convert the raw data to structured logs
// In the batch analytics case we could load the data directly as JSON records. In the case of the Socket source, that data is received as plain text. To transform our raw data to WebLog records, we first require a schema. The schema provides the necessary information to parse the text to a JSON object. It's the 'structure' when we talk about 'structured' streaming.

// After defining a schema for our data, we will:

// Transform the text value to JSON using the JSON support built in the structured API of Spark
// Use the Dataset API to transform the JSON records to WebLog objects
// As result of this process, we will obtain a Streaming Dataset of WebLog records.


val webLogSchema = Encoders.product[WebLog].schema

val jsonStream = stream.select(from_json($"value", webLogSchema) as "record")

val webLogStream: Dataset[WebLog] = jsonStream.select("record.*").as[WebLog]
//We have a structured stream.
//The webLogStream we just obtained is of type Dataset[WebLog] like we had in the batch analytics job. The difference between this instance and the batch version is that webLogStream is a streaming Dataset.
//
//We can observe this by querying the object.


webLogStream.isStreaming
//Operations on Streaming Datasets
//At this point in the batch job, we were creating the first query on our data: How many records are contained in our dataset? This is a question that we can answer easily when we have access to all the data. But how to count records that are constantly arriving? The answer is that some operations we consider usual on a static Dataset, like counting all records, do not have a defined meaning on a streaming Dataset.
//



// expect this call to fail!
val count = webLogStream.count()
//What are popular URLs? In what timeframe?
//Now that we have immediate analytic access to the stream of weblogs we don't need to wait for a day or a month to have a rank of the popular URLs. We can have that information as trends unfold on much shorter windows of time.


// A regex expression to extract the accessed URL from weblog.request 
val urlExtractor = """^GET (.+) HTTP/\d.\d""".r
//val allowedExtensions = Set(".html",".htm", "")
//
val contentPageLogs: String => Boolean = url => {
  val ext = url.takeRight(5).dropWhile(c => c != '.')
  allowedExtensions.contains(ext)
}

val urlWebLogStream = webLogStream.flatMap{ weblog => 
  weblog.request match {                                        
    case urlExtractor(url) if (contentPageLogs(url)) => Some(weblog.copy(request = url))
    case _ => None
  }
}



val rankingURLStream = urlWebLogStream.groupBy($"request", window($"timestamp", "5 minutes", "1 minute")).count()
//Start the stream processing
//All the steps we have followed so far have been to define the process that the stream will undergo but no data has been processed yet.
//


val query = rankingURLStream.writeStream
  .queryName("urlranks")
  //.outputMode("complete")
  //.format("memory")
  .start()
//The memory sink outputs the data to a temporary table of the same name given in the queryName option.

session.sql("show tables").show()
//Exploring the Data
//The memory sink outputs the data to a temporary table of the same name given in the queryName option. We can create a DataFrame from that table to explore the results of the stream process.
//

val urlRanks = session.sql("select * from urlranks")
//Before we can see any materialized results, we need to wait for the window to complete.
//Given that we are accelerating the log timeline on the producer side, after few seconds, we can execute the next command to see the result of the first windows.
//

urlRanks.select($"request", $"window", $"count").orderBy(desc("count"))
//
//
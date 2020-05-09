
// tar xvf datasets/NASA-weblogs/nasa_dataset_july_1995.tgz -C /tmp/data/
val logsDirectory = "/tmp/data/nasa_dataset_july_1995"
val rawLogs = sparkSession.read.json(logsDirectory)




import java.sql.Timestamp


case class WebLog(host:String, 
                  timestamp: Timestamp, 
                  request: String, 
                  http_reply:Int, 
                  bytes: Long
                 )

// We convert the raw data to structured logs

import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType
val preparedLogs = rawLogs.withColumn("http_reply", $"http_reply".cast(IntegerType))

val weblogs = preparedLogs.as[WebLog]

val recordCount = weblogs.count

val topDailyURLs = weblogs.withColumn("dayOfMonth", dayofmonth($"timestamp"))
                          .select($"request", $"dayOfMonth")
                          .groupBy($"dayOfMonth", $"request")
                          .agg(count($"request").alias("count"))
                          .orderBy(desc("count"))

// topDailyURLs.show()

// topDailyURLs.take(10)


val urlExtractor = """^GET (.+) HTTP/\d.\d""".r
val allowedExtensions = Set(".html",".htm", "")
val contentPageLogs = weblogs.filter {log => 
  log.request match {                                        
    case urlExtractor(url) => 
      val ext = url.takeRight(5).dropWhile(c => c != '.')
      allowedExtensions.contains(ext)
    case _ => false 
  }
}

val topContentPages = contentPageLogs.withColumn("dayOfMonth", dayofmonth($"timestamp"))
                          .select($"request", $"dayOfMonth")
                          .groupBy($"dayOfMonth", $"request")
                          .agg(count($"request").alias("count"))
                          .orderBy(desc("count"))
// In [ ]:
// topContentPages


// In [ ]:

// +----------+----------------------------------------+-----+
// |dayOfMonth|                                 request|count|
// +----------+----------------------------------------+-----+
// |        13|GET /images/NASA-logosmall.gif HTTP/1.0 |12476|
// |        13|GET /htbin/cdt_main.pl HTTP/1.0         | 7471|
// |        12|GET /images/NASA-logosmall.gif HTTP/1.0 | 7143|
// |        13|GET /htbin/cdt_clock.pl HTTP/1.0        | 6237|
// |         6|GET /images/NASA-logosmall.gif HTTP/1.0 | 6112|
// |         5|GET /images/NASA-logosmall.gif HTTP/1.0 | 5865|
        

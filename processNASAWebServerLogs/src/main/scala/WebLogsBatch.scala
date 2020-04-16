Simple Weblog Analytics - The Batch Way
In this notebook, we are going to quickly visit a batch process of a series of weblog files to obtain the top trending pages per day.

In [ ]:
// This is the location of the unpackaged files. Update accordingly
// You can unpack the provided dataset with:
// tar xvf datasets/NASA-weblogs/nasa_dataset_july_1995.tgz -C /tmp/data/
val logsDirectory = "/tmp/data/nasa_dataset_july_1995"
val rawLogs = sparkSession.read.json(logsDirectory)
We define a schema for the data in the logs
Following the formal description of the dataset (at: NASA-HTTP ), the log is structured as follows:

The logs are an ASCII file with one line per request, with the following columns:

host making the request. A hostname when possible, otherwise the Internet address if the name could not be looked up.
timestamp in the format "DAY MON DD HH:MM:SS YYYY", where DAY is the day of the week, MON is the name of the month, DD is the day of the month, HH:MM:SS is the time of day using a 24-hour clock, and YYYY is the year. The timezone is -0400.
request given in quotes.
HTTP reply code.
bytes in the reply.
The dataset provided for this exercise offers this data in JSON format

In [ ]:
import java.sql.Timestamp
case class WebLog(host:String, 
                  timestamp: Timestamp, 
                  request: String, 
                  http_reply:Int, 
                  bytes: Long
                 )
We convert the raw data to structured logs
In [ ]:
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType
val preparedLogs = rawLogs.withColumn("http_reply", $"http_reply".cast(IntegerType))
In [ ]:
val weblogs = preparedLogs.as[WebLog]
Now, we have the data in a structured format and we can start asking the questions that interest us.
We have imported the data and transformed it using a known schema. We can use this 'structured' data to create queries that provide insights in the behavior of the users.

As a first step, we would like to know how many records are contained in our dataset.
In [ ]:
val recordCount = weblogs.count
A common question would be, what was the most popular URL per day?
We first reduce the timestamp to the day of the year. We then group by this new 'day of year' column and the request url and we count over this aggregate. We finally order using descending order to get this top URLs first.

In [ ]:
val topDailyURLs = weblogs.withColumn("dayOfMonth", dayofmonth($"timestamp"))
                          .select($"request", $"dayOfMonth")
                          .groupBy($"dayOfMonth", $"request")
                          .agg(count($"request").alias("count"))
                          .orderBy(desc("count"))
In [ ]:
topDailyURLs.show()
In [ ]:
topDailyURLs.take(10)
Top hits are all images. What now?
It's not unusual to see that the top URLs are images commonly used across a site.

Our true interest lies in the content pages generating most traffic. To find those, we first filter on html content and then proceed to apply the top aggregation we just learned.

The request field is a quoted sequence of [HTTP_VERB] URL [HTTP_VERSION]. We will extract the url and preserve only those ending in .html, .htm or no extension (directories). This is a simplification for the purpose of the exercise.

In [ ]:
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
In [ ]:
val topContentPages = contentPageLogs.withColumn("dayOfMonth", dayofmonth($"timestamp"))
                          .select($"request", $"dayOfMonth")
                          .groupBy($"dayOfMonth", $"request")
                          .agg(count($"request").alias("count"))
                          .orderBy(desc("count"))
In [ ]:
topContentPages
We can see that the most popular page that month was liftoff.html corresponding to the coverage of the launch of the Discovery shuttle, as documented on the NASA archives: https://www.nasa.gov/mission_pages/shuttle/shuttlemissions/archives/sts-70.html.

It's closely followed by countdown/ the days prior ot the launch.

In [ ]:
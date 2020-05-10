import java.sql.Timestamp
case class Rate(timestamp: Timestamp, value: Long)


val rate = sparkSession.readStream.format("rate").load().as[Rate]


val uids = List("d1e46a42", "d8e16e2a", "d1b06f88", 
                "d2e710aa", "d2f731cc", "d4c162ee", 
                "d4a11632", "d7e277b2", "d59018de", 
                "d60779f6" )


val locationGenerator: () => (Double, Double) = {
  // Europe bounds 
  val longBounds = (-10.89,39.82)
  val latBounds = (35.52,56.7)

  def pointInRange(bounds:(Double, Double)): Double = {
    val (a, b) = bounds
    Math.abs(scala.util.Random.nextDouble())*b+a
  }
  () => (pointInRange(longBounds), pointInRange(latBounds))
}
// Location generator 
// locationGenerator: () => (Double, Double) = <function0>

def pickOne[T](list: List[T]): T = list(scala.util.Random.nextInt(list.size))


val pressureGen: () => Double = () => scala.util.Random.nextDouble + 101.0
val tempGen: () => Double = () => scala.util.Random.nextDouble * 60 - 20
// pressureGen: () => Double = <function0>
// tempGen: () => Double = <function0>

import java.sql.Timestamp

case class WeatherEvent(stationId: String, timestamp: Timestamp, location:(Double,Double), pressure: Double, temp: Double)
import java.sql.Timestamp
// defined class WeatherEvent

val weatherEvents = rate.map{case Rate(ts, value) => WeatherEvent(pickOne(uids), ts, locationGenerator(), pressureGen(), tempGen())}
// weatherEvents: org.apache.spark.sql.Dataset[WeatherEvent] = [stationId: string, timestamp: timestamp ... 3 more fields]

import scala.collection.immutable.Queue
case class FIFOBuffer[T](capacity: Int, data: Queue[T] = Queue.empty) extends Serializable {
  def add(element: T): FIFOBuffer[T] = this.copy(data = data.enqueue(element).take(capacity))
  def get: List[T] = data.toList
  def size: Int = data.size
}
import scala.collection.immutable.Queue


import java.sql.Timestamp
//
case class WeatherEventAverage(stationId: String, 
                               startTime: Timestamp, 
                               endTime:Timestamp, 
                               pressureAvg: Double, 
                               tempAvg: Double)
import java.sql.Timestamp


import org.apache.spark.sql.streaming.GroupState

def mappingFunction(key: String, values: Iterator[WeatherEvent], state: GroupState[FIFOBuffer[WeatherEvent]]): WeatherEventAverage = {
  val ElementCountWindowSize = 10
  
  val currentState = state.getOption.getOrElse(new FIFOBuffer[WeatherEvent](ElementCountWindowSize))
  
  val updatedState = values.foldLeft(currentState){case (st, ev) => st.add(ev)}
  
  state.update(updatedState)
  
  val data = updatedState.get
  if (data.size > 2) {
    val start = data.head
    val end = data.last
    val pressureAvg = data.map(event => event.pressure).sum/data.size
    val tempAvg = data.map(event => event.temp).sum/data.size
    WeatherEventAverage(key, start.timestamp, end.timestamp, pressureAvg, tempAvg)
  } else {
    WeatherEventAverage(key, new Timestamp(0), new Timestamp(0), 0.0, 0.0)
  }
}


// Group State 
import org.apache.spark.sql.streaming.GroupState
// mappingFunction: (key: String, values: Iterator[WeatherEvent], state: org.apache.spark.sql.streaming.GroupState[FIFOBuffer[WeatherEvent]])WeatherEventAverage



import org.apache.spark.sql.streaming.GroupStateTimeout
val weatherEventsMovingAverage = weatherEvents.groupByKey(record => record.stationId)
.mapGroupsWithState(GroupStateTimeout.ProcessingTimeTimeout)(mappingFunction)
import org.apache.spark.sql.streaming.GroupStateTimeout
weatherEventsMovingAverage: org.apache.spark.sql.Dataset[WeatherEventAverage] = [stationId: string, startTime: timestamp ... 3 more fields]



val outQuery = weatherEventsMovingAverage.writeStream
  .format("memory")
  .queryName("weatherAverage")
  .outputMode("update")
  .start()


// outQuery.stop()

val table  = sparkSession.sql("select * from weatherAverage where pressureAvg == 0.0")
// table: org.apache.spark.sql.DataFrame = [stationId: string, startTime: timestamp ... 3 more fields]

// table.show(truncate= false)
// +---------+-------------------+-------------------+-----------+-------+
// |stationId|startTime          |endTime            |pressureAvg|tempAvg|
// +---------+-------------------+-------------------+-----------+-------+
// |d2e710aa |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d1e46a42 |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d4a11632 |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d4c162ee |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d1e46a42 |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d60779f6 |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d2f731cc |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d1b06f88 |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d8e16e2a |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d8e16e2a |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d4c162ee |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d1b06f88 |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d60779f6 |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d7e277b2 |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d2f731cc |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d2e710aa |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d4a11632 |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d7e277b2 |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d59018de |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// |d59018de |1970-01-01 01:00:00|1970-01-01 01:00:00|0.0        |0.0    |
// +---------+-------------------+-------------------+-----------+-------+


// outQuery.stop

val m5 = 1000*60*5
val next5MinSlot = ((System.currentTimeMillis + m5)/m5)*m5
val m5Date = new java.sql.Timestamp(next5MinSlot)


import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._


val perMinuteAvg = weatherDF
    .withWatermark("timestamp","0 minutes")
    .groupBy(window($"timestamp","15 minute", "15 minute", "0 minute"))
    //.agg(avg($"pressure") as "pressureAvg", avg($"temp") as "tempAvg")
    .agg(count($"stationId"))





import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._


// perMinuteAvg.printSchema
// root
//  |-- window: struct (nullable = true)
//  |    |-- start: timestamp (nullable = true)
//  |    |-- end: timestamp (nullable = true)
//  |-- count(stationId): long (nullable = false)

val query = perMinuteAvg.writeStream.format("memory").outputMode("append").queryName("tenMinAvg").start()


val df = sparkSession.sql("select * from tenMinAvg")


// df.show(truncate=false)
// +---------------------------------------------+----------------+
// |window                                       |count(stationId)|
// +---------------------------------------------+----------------+
// |[2018-06-24 23:45:00.0,2018-06-25 00:00:00.0]|587             |
// +---------------------------------------------+----------------+


// query.stop
// df.show
// +--------------------+---------+------------------+-------------------+
// |              window|stationId|       pressureAvg|            tempAvg|
// +--------------------+---------+------------------+-------------------+
// |[1970-01-18 00:00...| d7e771cc|101.01088217991148|-5.8995974443145744|
// |[1970-01-18 00:00...| d7e76a42|101.84763550125572| 16.365236842957728|
// |[1970-01-18 00:00...| d7e76f88| 101.9718887595217| 34.596594920941314|
// |[1970-01-18 00:00...| d7e77672|101.15962690464693| 18.331916524151467|
// |[1970-01-18 00:00...| d7e77672|101.33512527465564| 14.472673136283124|
// |[1970-01-18 00:00...| d7e770aa|101.40406589851482| 12.631897019107264|
// |[1970-01-18 00:00...| d7e778de|101.65262104046391| 30.251680016296675|
// |[1970-01-18 00:00...| d7e772ee|101.49646793425526| 10.075008485762066|
// |[1970-01-18 00:00...| d7e76a42|101.58496517622028| 1.5693482754141428|
// |[1970-01-18 00:00...| d7e779f6|  101.603830921723| 20.764354974202504|
// |[1970-01-18 00:00...| d7e76e2a|101.47581528098183| 28.273156326472247|
// |[1970-01-18 00:00...| d7e771cc|101.22096012488818| -2.723741134194815|
// |[1970-01-18 00:00...| d7e777b2|101.20686544720449| 16.600885095664438|
// |[1970-01-18 00:00...| d7e77672|101.43296921555992|  8.578856993449158|
// |[1970-01-18 00:00...| d7e771cc|101.30579140600571| -6.249646394577954|
// |[1970-01-18 00:00...| d7e77672|101.45101113969656| 2.9677124712538143|
// |[1970-01-18 00:00...| d7e770aa|101.48617036734416|  8.896303222320139|
// |[1970-01-18 00:00...| d7e779f6| 101.3411174056723| 25.249754767701123|
// |[1970-01-18 00:00...| d7e76e2a|101.44798061812338|  32.45599638820388|
// |[1970-01-18 00:00...| d7e772ee|101.52360800080652|  1.858552829842818|
// +--------------------+---------+------------------+-------------------+
// only showing top 20 rows


// df.printSchema
// root
//  |-- window: struct (nullable = true)
//  |    |-- start: timestamp (nullable = true)
//  |    |-- end: timestamp (nullable = true)
//  |-- pressureAvg: double (nullable = true)
//  |-- tempAvg: double (nullable = true)


val q2 = weatherEvents.writeStream.format("memory").queryName("raw2").outputMode("update").start()


// sparkSession.sql("select * from raw2")


// rate.writeStream.format("memory").queryName("rate").start


// sparkSession.sql("select * from rate")


val ts = System.currentTimeMillis


val sqlTs = new java.sql.Timestamp(ts)


val rates = (1 to 10).map(i => Rate(ts, i))


val spark = sparkSession
import spark.implicits._
val ratesDS= rates.toDS
val tsDS = rates.map{case Rate(ts, value) => (ts, new java.sql.Timestamp(ts))}.toDF("ts", "sqlts")

import spark.implicits._
// ratesDS: org.apache.spark.sql.Dataset[Rate] = [timestamp: bigint, value: bigint]
// tsDS: org.apache.spark.sql.DataFrame = [ts: bigint, sqlts: timestamp]

val query = tsDS.select($"ts", $"sqlts", $"sqlts".cast("Long"))
// query: org.apache.spark.sql.DataFrame = [ts: bigint, sqlts: timestamp ... 1 more field]

// query.show
// +-------------+--------------------+----------+
// |           ts|               sqlts|     sqlts|
// +-------------+--------------------+----------+
// |1529247155834|2018-06-17 16:52:...|1529247155|
// |1529247155834|2018-06-17 16:52:...|1529247155|
// |1529247155834|2018-06-17 16:52:...|1529247155|
// |1529247155834|2018-06-17 16:52:...|1529247155|
// |1529247155834|2018-06-17 16:52:...|1529247155|
// |1529247155834|2018-06-17 16:52:...|1529247155|
// |1529247155834|2018-06-17 16:52:...|1529247155|
// |1529247155834|2018-06-17 16:52:...|1529247155|
// |1529247155834|2018-06-17 16:52:...|1529247155|
// |1529247155834|2018-06-17 16:52:...|1529247155|
// +-------------+--------------------+----------+

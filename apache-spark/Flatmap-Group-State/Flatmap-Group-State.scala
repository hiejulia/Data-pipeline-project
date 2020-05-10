import java.sql.Timestamp
// Rate
case class Rate(timestamp: Timestamp, value: Long)

val ElementCountWindowSize = 10

val rate = sparkSession.readStream.format("rate").load().as[Rate]

val uids = List("d1e46a42", "d8e16e2a", "d1b06f88", 
                "d2e710aa", "d2f731cc", "d4c162ee", 
                "d4a11632", "d7e277b2", "d59018de", 
                "d60779f6" )


// Location generator 
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



def pickOne[T](list: List[T]): T = list(scala.util.Random.nextInt(list.size))


// Pressure generator 
val pressureGen: () => Double = () => scala.util.Random.nextDouble + 101.0
// Temp generator 
val tempGen: () => Double = () => scala.util.Random.nextDouble * 60 - 20
import java.sql.Timestamp

// Weather Event 
case class WeatherEvent(stationId: String, timestamp: Timestamp, location:(Double,Double), pressure: Double, temp: Double)


val weatherEvents = rate.map{case Rate(ts, value) => WeatherEvent(pickOne(uids), ts, locationGenerator(), pressureGen(), tempGen())}

import scala.collection.immutable.Queue

case class FIFOBuffer[T](capacity: Int, data: Queue[T] = Queue.empty) extends Serializable {
  def add(element: T): FIFOBuffer[T] = this.copy(data = data.enqueue(element).take(capacity))
  def get: List[T] = data.toList
  def size: Int = data.size
}


import java.sql.Timestamp
case class WeatherEventAverage(stationId: String, 
                               startTime: Timestamp, 
                               endTime:Timestamp, 
                               pressureAvg: Double, 
                               tempAvg: Double)


// State to average 
def stateToAverageEvent(key: String, data: FIFOBuffer[WeatherEvent]):Iterator[WeatherEventAverage] = {
  if (data.size == ElementCountWindowSize) {
      val events = data.get
      val start = events.head
      val end = events.last
      val pressureAvg = events.map(event => event.pressure).sum/data.size
      val tempAvg = events.map(event => event.temp).sum/data.size
      Iterator(WeatherEventAverage(key, start.timestamp, end.timestamp, pressureAvg, tempAvg))
    } else {
      Iterator.empty
    }
}


import org.apache.spark.sql.streaming.GroupState

// Flatmap function
def flatMappingFunction(key: String, values: Iterator[WeatherEvent], state: GroupState[FIFOBuffer[WeatherEvent]]): 
Iterator[WeatherEventAverage] = {
  if (state.hasTimedOut) {
    assert(values.isEmpty, "When the state has a timeout, the values are empty")
    val result = stateToAverageEvent(key, state.get)
    // evict the timed-out state
    state.remove()
    // emit the result of transforming the current state into an output record
    result
  } else {
    
    // Get current state || Create new if no prev state
    val currentState = state.getOption.getOrElse(new FIFOBuffer[WeatherEvent](ElementCountWindowSize))
    
    val updatedState = values.foldLeft(currentState){case (st, ev) => st.add(ev)}
    
    state.update(updatedState)
    state.setTimeoutDuration("30 seconds")
    
    // Create WeatherEventAverage 
    stateToAverageEvent(key, updatedState)
  }
}


import org.apache.spark.sql.streaming.{GroupStateTimeout, OutputMode}
val weatherEventsMovingAverage = weatherEvents
.withWatermark("timestamp", "2 minutes")
.groupByKey(record => record.stationId)
.flatMapGroupsWithState(OutputMode.Update, GroupStateTimeout.ProcessingTimeTimeout)(flatMappingFunction)



val outQuery = weatherEventsMovingAverage.writeStream
  .format("memory")
  .queryName("weatherAverage")
  .outputMode("update")
  .start()



// outQuery.stop()

val table  = sparkSession.sql("select * from weatherAverage")


table.show(truncate= false)


// +---------+-----------------------+-----------------------+------------------+------------------+
// |stationId|startTime              |endTime                |pressureAvg       |tempAvg           |
// +---------+-----------------------+-----------------------+------------------+------------------+
// |d4c162ee |2018-07-15 19:39:39.681|2018-07-15 19:40:08.681|101.55667628745744|-0.698490270514989|
// |d4c162ee |2018-07-15 19:39:39.681|2018-07-15 19:40:08.681|101.55667628745744|-0.698490270514989|
// |d4c162ee |2018-07-15 19:39:39.681|2018-07-15 19:40:08.681|101.55667628745744|-0.698490270514989|
// |d59018de |2018-07-15 19:39:41.681|2018-07-15 19:40:42.681|101.45304706385005|14.724579048505515|
// |d4c162ee |2018-07-15 19:39:39.681|2018-07-15 19:40:08.681|101.55667628745744|-0.698490270514989|
// |d59018de |2018-07-15 19:39:41.681|2018-07-15 19:40:42.681|101.45304706385005|14.724579048505515|
// |d59018de |2018-07-15 19:39:41.681|2018-07-15 19:40:42.681|101.45304706385005|14.724579048505515|
// |d4c162ee |2018-07-15 19:39:39.681|2018-07-15 19:40:08.681|101.55667628745744|-0.698490270514989|
// |d4c162ee |2018-07-15 19:39:39.681|2018-07-15 19:40:08.681|101.55667628745744|-0.698490270514989|
// |d59018de |2018-07-15 19:39:41.681|2018-07-15 19:40:42.681|101.45304706385005|14.724579048505515|
// |d4c162ee |2018-07-15 19:39:39.681|2018-07-15 19:40:08.681|101.55667628745744|-0.698490270514989|
// |d4c162ee |2018-07-15 19:39:39.681|2018-07-15 19:40:08.681|101.55667628745744|-0.698490270514989|
// |d2e710aa |2018-07-15 19:39:51.681|2018-07-15 19:41:04.681|101.47805380773234|6.026307192727087 |
// |d2e710aa |2018-07-15 19:39:51.681|2018-07-15 19:41:04.681|101.47805380773234|6.026307192727087 |
// |d59018de |2018-07-15 19:39:41.681|2018-07-15 19:40:42.681|101.45304706385005|14.724579048505515|
// |d2e710aa |2018-07-15 19:39:51.681|2018-07-15 19:41:04.681|101.47805380773234|6.026307192727087 |
// |d1b06f88 |2018-07-15 19:39:38.681|2018-07-15 19:41:14.681|101.48914104278731|12.304328428450534|
// |d2e710aa |2018-07-15 19:39:51.681|2018-07-15 19:41:04.681|101.47805380773234|6.026307192727087 |
// |d4c162ee |2018-07-15 19:39:39.681|2018-07-15 19:40:08.681|101.55667628745744|-0.698490270514989|
// |d60779f6 |2018-07-15 19:40:04.681|2018-07-15 19:41:21.681|101.40461400392056|13.830357123999761|
// +---------+-----------------------+-----------------------+------------------+------------------+



// outQuery.stop



outQuery.lastProgress
// res18: org.apache.spark.sql.streaming.StreamingQueryProgress =
// {
//   "id" : "8fb47565-5bb7-4990-b8df-280767911647",
//   "runId" : "e425e9a5-1fb9-4706-8c99-ceaf3b7a0003",
//   "name" : "weatherAverage",
//   "timestamp" : "2018-07-08T20:58:58.535Z",
//   "numInputRows" : 0,
//   "inputRowsPerSecond" : 0.0,
//   "durationMs" : {
//     "getOffset" : 0,
//     "triggerExecution" : 0
//   },
//   "stateOperators" : [ {
//     "numRowsTotal" : 10,
//     "numRowsUpdated" : 0
//   } ],
//   "sources" : [ {
//     "description" : "RateSource[rowsPerSecond=1, rampUpTimeSeconds=0, numPartitions=8]",
//     "startOffset" : 60,
//     "endOffset" : 60,
//     "numInputRows" : 0,
//     "inputRowsPerSecond" : 0.0
//   } ],
//   "sink" : {
//     "description" : "MemorySink"
//   }
// }

// { "id" : "8fb47565-5bb7-4990-b8df-280767911647", "runId" : "e425e9a5-1fb9-4706-8c99-ceaf3b7a0003", "name" : "weatherAverage", "timestamp" : "2018-07-08T20:58:58.535Z", "numInputRows" : 0, "inputRowsPerSecond" : 0.0, "durationMs" : { "getOffset" : 0, "triggerExecution" : 0 }, "stateOperators" : [ { "numRowsTotal" : 10, "numRowsUpdated" : 0 } ], "sources" : [ { "description" : "RateSource[rowsPerSecond=1, rampUpTimeSeconds=0, numPartitions=8]", "startOffset" : 60, "endOffset" : 60, "numInputRows" : 0, "inputRowsPerSecond" : 0.0 } ], "sink" : { "description" : "MemorySink" } }
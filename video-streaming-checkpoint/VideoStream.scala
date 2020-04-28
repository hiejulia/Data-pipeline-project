// Data Structures
case class VideoPlayed(videoId: String, clientId: String, timestamp: Long)
case class VideoPlayCount(videoId: String, day: Date, count: Long)

// State Tracking Function
def trackVideoHits(videoId: String,
                   timestamp:Option[Long],
                   runningCount: State[VideoPlayCount]
                   ): Option[VideoPlayCount]




// Streaming checkpoint 


import org.apache.spark.streaming.State
import org.apache.spark.streaming._

val streamingContext = new StreamingContext(spark.sparkContext, Seconds(10))
streamingContext.checkpoint("/tmp/streaming")


// Checkpoint video played DStream 
val checkpointedVideoPlayedDStream = videoPlayedDStream.checkpoint(Seconds(60))


// create the mapWithState spec
val videoHitsCounterSpec = StateSpec.function(trackVideoHits _)
                                    .timeout(Seconds(3600))

// Stateful stream of videoHitsPerHour
val statefulVideoHitsPerHour = checkpointedVideoPlayedDStream.map(videoPlay =>
  (videoPlay.videoId, videoPlay.timestamp)
).mapWithState(videoHitsCounterSpec)

// remove the None values from the state stream by "flattening" the DStream
val videoHitsPerHour = statefulVideoHitsPerHour.flatMap(elem => elem)

// print the top-5 highest values
videoHitsPerHour.foreachRDD{ rdd =>
  val top5 = rdd.top(5)(Ordering[Long].on((v: VideoPlayCount) => v.count))
  top5.foreach(videoCount => println(videoCount))
}

streamingContext.start()



// Top 5 at time 2019-03-17 21:44:00.0
// =========================
// video-935, 2019-03-17 23:00:00.0, 18
// video-981, 2019-03-18 00:00:00.0, 18
// video-172, 2019-03-18 00:00:00.0, 17
// video-846, 2019-03-18 00:00:00.0, 17
// video-996, 2019-03-18 00:00:00.0, 17


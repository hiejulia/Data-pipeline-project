import scala.util.Random
// 100K sensors in our system
val sensorId: () => Int = () =>  Random.nextInt(100000)
val data: () => Double = () => Random.nextDouble
val timestamp: () => Long = () => System.currentTimeMillis

val recordFunction: () => String = { () =>
  if (Random.nextDouble < 0.9) {
    Seq(sensorId().toString, timestamp(), data()).mkString(",")
  } else {
    "!!~corrupt~^&##$"
  }
}


// Sensor data generator 
    // Spark context 
val sensorDataGenerator = sparkContext.parallelize(1 to 100)
                                      .map(_ => recordFunction)

val sensorData = sensorDataGenerator.map(recordFun => recordFun())



// sensorData.take(5)



// Streaming Context

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds

val streamingContext = new StreamingContext(sparkContext, Seconds(2))


// Raw stream 
import org.apache.spark.streaming.dstream.ConstantInputDStream
val rawDStream  = new ConstantInputDStream(streamingContext, sensorData)


// Define schema 
case class SensorData(sensorId: Int, timestamp: Long, value: Double)

import scala.util.Try
// Schema stream for raw stream
val schemaStream = rawDStream.flatMap{record =>
  val fields = record.split(",")
  // this Try captures exceptions related to corrupted input values
  Try {
    SensorData(fields(0).toInt, fields(1).toLong, fields(2).toDouble)
  }.toOption
}


// Save to dataframe

import org.apache.spark.sql.SaveMode.Append

schemaStream.foreachRDD{rdd =>
  val df = rdd.toDF()
  // Write to parquet 
  df.write.format("parquet").mode(Append).save("/tmp/iotstream.parquet")
}

// ----- Start streaming process 
streamingContext.start()




// timestamp of desination 
def ts: String = ((time.milliseconds - timeOrigin)/(3600 * 1000)).toString
df.write.mode(SaveMode.Append).format("parquet").save(s"${outputPath}-$ts")



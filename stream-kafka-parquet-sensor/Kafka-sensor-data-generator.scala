// Kafka
val kafkaBootstrapServer = "172.17.0.2:9092"
val targetTopic = "iot-data"
val workDir = "/tmp/sensor-data"

val sensorCount = 100000


// Schema

case class SensorData(sensorId: Int, timestamp: Long, value: Double)
object SensorData {
  import scala.util.Random // random generator 
  def randomGen(maxId:Int) = {
    SensorData(Random.nextInt(maxId), System.currentTimeMillis, Random.nextDouble())
  }
}

case class Rate(timestamp: Long, value: Long)


val baseStream = sparkSession.readStream.format("rate").option("recordsPerSecond", 100).load()

val sensorValues = baseStream.as[Rate].map(_ => SensorData.randomGen(sensorCount))

import org.apache.spark.sql.kafka010._

val query = sensorValues.writeStream.format("kafka")
  .queryName("kafkaWriter")
  .outputMode("append")
  .option("kafka.bootstrap.servers", kafkaBootstrapServer) // comma-separated list of host:port
  .option("topic", targetTopic)
  .option("checkpointLocation", workDir+"/generator-checkpoint")
  .option("failOnDataLoss", "false") // use this option when testing
  .start()


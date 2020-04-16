// // Structured Streaming API 

// //We will the Kafka source to consume the iot-data topic.
// //We use a file sink to store the data into a Parquet file.

// import java.io.File
// val topic = "iot-data"
// val workDir = "/tmp/streaming-with-spark"
// val referenceFile = "sensor-records.parquet"
// val targetFile = "structured_enrichedIoTStream.parquet"
// val targetPath = new File(workDir, targetFile).getAbsolutePath
// val unknownSensorsTargetFile = "unknownSensorsStream.parquet"
// val unknownSensorsTargetPath = new File(workDir, unknownSensorsTargetFile).getAbsolutePath
// val kafkaBootstrapServer = "127.0.0.1:9092"
// import java.io.File
// topic: String = iot-data
// workDir: String = /tmp/streaming-with-spark
// referenceFile: String = sensor-records.parquet
// targetFile: String = structured_enrichedIoTStream.parquet
// targetPath: String = /tmp/streaming-with-spark/structured_enrichedIoTStream.parquet
// unknownSensorsTargetFile: String = unknownSensorsStream.parquet
// unknownSensorsTargetPath: String = /tmp/streaming-with-spark/unknownSensorsStream.parquet
// kafkaBootstrapServer: String = 127.0.0.1:9092


// val rawData = sparkSession.readStream
//       .format("kafka")
//       .option("kafka.bootstrap.servers", kafkaBootstrapServer)
//       .option("subscribe", topic)
//       .option("enable.auto.commit", true)
//       .option("group.id", "iot-data-consumer")
//       .option("startingOffsets", "earliest")
//       .load()
// rawData: org.apache.spark.sql.DataFrame = [key: binary, value: binary ... 5 more fields]


// rawData.isStreaming
// res7: Boolean = true

// true

// rawData.printSchema()
// root
//  |-- key: binary (nullable = true)
//  |-- value: binary (nullable = true)
//  |-- topic: string (nullable = true)
//  |-- partition: integer (nullable = true)
//  |-- offset: long (nullable = true)
//  |-- timestamp: timestamp (nullable = true)
//  |-- timestampType: integer (nullable = true)



// case class SensorData(sensorId: Int, timestamp: Long, value: Double)
// defined class SensorData


// val iotData = rawData.select("value").as[String].map{r =>
//   val Array(id, timestamp, value) = r.split(",")
//   SensorData(id.toInt, timestamp.toLong, value.toDouble)
// }
// iotData: org.apache.spark.sql.Dataset[SensorData] = [sensorId: int, timestamp: bigint ... 1 more field]

// Load the reference data from a parquet file¶
// We also cache the data to keep it in memory and improve the performance of our steaming application


// val sensorRef = sparkSession.read.parquet(s"$workDir/$referenceFile")
// sensorRef.cache()
// org.apache.spark.sql.AnalysisException: Path does not exist: file:/tmp/learningsparkstreaming/sensor-records.parquet;
//   at org.apache.spark.sql.execution.datasources.DataSource$$anonfun$14.apply(DataSource.scala:360)
//   at org.apache.spark.sql.execution.datasources.DataSource$$anonfun$14.apply(DataSource.scala:348)
//   at scala.collection.TraversableLike$$anonfun$flatMap$1.apply(TraversableLike.scala:241)
//   at scala.collection.TraversableLike$$anonfun$flatMap$1.apply(TraversableLike.scala:241)
//   at scala.collection.immutable.List.foreach(List.scala:381)
//   at scala.collection.TraversableLike$class.flatMap(TraversableLike.scala:241)
//   at scala.collection.immutable.List.flatMap(List.scala:344)
//   at org.apache.spark.sql.execution.datasources.DataSource.resolveRelation(DataSource.scala:348)
//   at org.apache.spark.sql.DataFrameReader.load(DataFrameReader.scala:178)
//   at org.apache.spark.sql.DataFrameReader.parquet(DataFrameReader.scala:559)
//   at org.apache.spark.sql.DataFrameReader.parquet(DataFrameReader.scala:543)
//   ... 63 elided

// val query = iotData.writeStream
//   .outputMode("append")
//   .format("parquet")
//   .option("path", workDir)
//   .option("checkpointLocation", "/tmp/checkpoint")
//   .start()
// queryDef: org.apache.spark.sql.streaming.DataStreamWriter[SensorData] = org.apache.spark.sql.streaming.DataStreamWriter@58df37bb


// query.recentProgress
// res38: Array[org.apache.spark.sql.streaming.StreamingQueryProgress] =
// Array({
//   "id" : "ce29c1eb-bebc-45cb-abd8-3e6437c16518",
//   "runId" : "dcfd946a-097a-41c1-b810-f848baaddc10",
//   "name" : null,
//   "timestamp" : "2017-08-09T11:52:44.721Z",
//   "numInputRows" : 21421,
//   "processedRowsPerSecond" : 56819.62864721486,
//   "durationMs" : {
//     "addBatch" : 236,
//     "getBatch" : 4,
//     "getOffset" : 109,
//     "queryPlanning" : 2,
//     "triggerExecution" : 377,
//     "walCommit" : 13
//   },
//   "stateOperators" : [ ],
//   "sources" : [ {
//     "description" : "KafkaSource[Subscribe[iot-data]]",
//     "startOffset" : {
//       "iot-data" : {
//         "0" : 3468807
//       }
//     },
//     "endOffset" : {
//       "iot-data" : {
//         "0" : 3490228
//       }
//     },
//     "numInputRows" : 21421,
//     "processedRowsPerSecond" : 5...

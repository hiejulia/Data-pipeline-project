val rawData = sparkSession.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", kafkaBootstrapServer)
      .option("subscribe", topic)
      .option("startingOffsets", "earliest")
      .load()


// rawData


// Kafka schema

// root
//  |-- key: binary (nullable = true)
//  |-- value: binary (nullable = true)
//  |-- topic: string (nullable = true)
//  |-- partition: integer (nullable = true)
//  |-- offset: long (nullable = true)
//  |-- timestamp: timestamp (nullable = true)
//  |-- timestampType: integer (nullable = true)



// rawData.printSchema

// IoTData schema
val iotData = rawData.select($"value").as[String].flatMap{record =>
  val fields = record.split(",")
  Try {
    SensorData(fields(0).toInt, fields(1).toLong, fields(2).toDouble)
  }.toOption
}

// parquet location

val sensorRef = sparkSession.read.parquet(s"$workDir/$referenceFile")

sensorRef.cache()

val sensorWithInfo = sensorRef.join(iotData, Seq("sensorId"), "inner")

val knownSensors = sensorWithInfo
  .withColumn("dnvalue", $"value"*($"maxRange"-$"minRange")+$"minRange")
  .drop("value", "maxRange", "minRange")


// Write a streaming sink
val knownSensorsQuery = knownSensors.writeStream
  .outputMode("append")
  .format("parquet")
  .option("path", targetPath)
  .option("checkpointLocation", "/tmp/checkpoint")
  .start()


// Query progress

//knownSensorsQuery.recentProgress
// location of destination node 
val sensorCount = 100000
val workDir = "/tmp/stream-parquet/"
val referenceFile = "sensor-records.parquet"
val targetFile = "enrichedIoTStream.parquet"


// Read 
val sensorRef = sparkSession.read.parquet(s"$workDir/$referenceFile")
sensorRef.cache()


// Enrich stream 
val stableSparkSession = sparkSession
import stableSparkSession.implicits._
import org.apache.spark.sql.SaveMode.Append

schemaStream.foreachRDD{ rdd =>
  val sensorDF = rdd.toDF()
  val sensorWithInfo = sensorDF.join(sensorRef, "sensorId")

  val sensorRecords =
    sensorWithInfo.withColumn(
      "dnvalue", $"value"*($"maxRange"-$"minRange")+$"minRange"
    ).drop("value", "maxRange", "minRange")
    // Write 
  sensorRecords.write
               .format("parquet")
               .mode(Append)
               .save(s"$workDir/$targetFile")
}

val enrichedRecords = sparkSession.read.parquet(s"$workDir/$targetFile")

// enrichedRecords.count



val stableSparkSession = sparkSession
import stableSparkSession.implicits._
import org.apache.spark.sql.SaveMode.Append

schemaStream.foreachRDD{ rdd =>
  val sensorDF = rdd.toDF()
  // broadcast
  val sensorWithInfo = sensorRef.join(
    broadcast(sensorDF), Seq("sensorId"), "rightouter"
  )

  val sensorRecords =
    sensorWithInfo.withColumn(
      "dnvalue", $"value"*($"maxRange"-$"minRange")+$"minRange"
    ).drop("value", "maxRange", "minRange")

  sensorRecords.write
               .format("parquet")
               .mode(Append)
               .save(s"$workDir/$targetFile")
}
import org.apache.spark.rdd.RDD

val emptyRDD: RDD[Int] = sparkContext.emptyRDD

val refreshDStream  = new ConstantInputDStream(streamingContext, emptyRDD)

// Cache ref dataset 
val refreshIntervalDStream = refreshDStream.window(Seconds(60), Seconds(60))
refreshIntervalDStream.foreachRDD{ rdd =>
  sensorRef.unpersist(false)
  sensorRef = sparkSession.read.parquet(s"$workDir/$referenceFile")
  sensorRef.cache()
}
import org.apache.spark.sql.streaming.Trigger

val stream = spark.readStream
    .format("rate")
    .option("rowsPerSecond", "5")
    .load()




import org.apache.spark.ml._

val pipelineModel = PipelineModel.read.load(modelFile)


// Sensor data stream 
val scoredStream = pipeline.transform(sensorDataStream)

// Inspect schema of resulting DataFrame 
//scoredStream.printSchema

import org.apache.spark.sql.streaming.Trigger
val query = scoredStream.writeStream
        .format("memory")
        .queryName("occ_pred")
        .start()

sparkSession.sql("select id, timestamp, occupancy, prediction from occ_pred")
            .show(10, false)


// +---+-----------------------+---------+----------+
// |id |timestamp              |occupancy|prediction|
// +---+-----------------------+---------+----------+
// |211|2018-08-06 00:13:15.687|1        |1.0       |
// |212|2018-08-06 00:13:16.687|1        |1.0       |
// |213|2018-08-06 00:13:17.687|1        |1.0       |
// |214|2018-08-06 00:13:18.687|1        |1.0       |
// |215|2018-08-06 00:13:19.687|1        |1.0       |
// |216|2018-08-06 00:13:20.687|1        |0.0       |
// |217|2018-08-06 00:13:21.687|1        |0.0       |
// |218|2018-08-06 00:13:22.687|0        |0.0       |
// |219|2018-08-06 00:13:23.687|0        |0.0       |
// |220|2018-08-06 00:13:24.687|0        |0.0       |
// +---+-----------------------+---------+----------+



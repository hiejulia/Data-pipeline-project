## Schema
- ASCII file 
- Columns : hostname, timestamp, req, http res code, res bytes


## Connect to a Stream 
- TextSocketSource to connect to the server by TCP socket

## Structured streaming 
- Structured streaming API (is doing )
    - Kafka source to consume the iot-data topic
    - a file sink to store the data into a Parquet file


## Streaming on dataset
- sink 
- output model 


## batch weblogs 


## How to run 
- Run Spark with Scala 
    - Spark API 
    - Scala, sbt 
    - build.sbt 
         - `libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.5"`
    - `sbt package`
    - Use spark-submit to run your application
        - YOUR_SPARK_HOME/bin/spark-submit \
  --class "SimpleApp" \
  --master local[4] \
  target/scala-2.12/simple-project_2.12-1.0.jar`

- Run Spark with Scala with Spark Notebook for Scala 

<a href="https://imgur.com/bM8mbZD"><img src="https://i.imgur.com/bM8mbZD.png" title="source: imgur.com" /></a>
<a href="https://imgur.com/JfiZBOC"><img src="https://i.imgur.com/JfiZBOC.png" title="source: imgur.com" /></a>
<a href="https://imgur.com/hXbPRFZ"><img src="https://i.imgur.com/hXbPRFZ.png" title="source: imgur.com" /></a>
<a href="https://imgur.com/1HrdMHS"><img src="https://i.imgur.com/1HrdMHS.png" title="source: imgur.com" /></a>

## Resource 

- http://ita.ee.lbl.gov/html/contrib/NASA-HTTP.html

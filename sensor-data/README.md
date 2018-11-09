# Sensor - big data application 
Water weather 

## Tech stack 
+ Kafka : real time processing 
+ Flume : collect and aggregate data from various system - get data from Kafka and write it to HDFS and Open TSDB simultaneously
+ HDFS : distributed file storage system for effective data analytics 
+ Hive : SQL dialect for Hadoop 
+ Open TSDB : scalable time series database- use HBase as it storage engine - APIs to write time series data in an effective manner - while store data - it converts it into Hex format for efficient storage and query capabilities 
+ HBase : Hadoop database - NoSQL database- store key-value pair data 
+ Grafana : query and visualize time series data from various sources such as ElasticSearch, Open TSDB,Graphite, InfluxDB 



## Steps 
1. Load data into HDFS using Streaming mode: Kafka 
2. Data analysis using Hive
3. Data visualization using Grafana and Open TSDB

+ Real time and batch analytics 

## Batch data analysis

+ Load streams of sensor data from Kafka topics to HDFS 
+ Using Hive to perform analytics on inserted data 
+ Start zookeeper and kafka 



## Streams data analytics 

+ Load streams of home weather sensor data 
    + Install Kafka, OpenTSDB, Grafana 
    + Load data from Kafka to HDFS 
    + Extra data from Kafka - use Flume Kafka source  
    + Config Flume agent 
    + Kafka - source memory channel and OpenTSDB  = sink 
        + get data - parse JSON - insert in database : POST message 
    + Start Flume agent 
    + kafka topic get data - Flume agent will fetch message - transform it and send it to Open TSDB 
    + Fetch data from Kafka topic and write it into HDFS 
    + Hive to perform analytics on inserted data 
        + create hive table 
        + 
+ Data visualization using Grafana 
    + Install Grafana - query data from various source such as Open TSDB - ElasticSearch 


+ Create topic "sensor"
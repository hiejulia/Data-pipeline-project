# Building Data Lake 
+ Store clean data - structured relational tables 
+ import data in hadoop 
+ Query data 
+ data from structured data sources - CRM system - inventory management system - semi structured data - web logs - unstructured data from emails or call center transcripts 
+ Storage - SLA, back up , restrict access to a certain dataset 
+ Data lake building blocks 
    + ingestion tier
        +  multiple sources 
        + data feed : real time, micro batches, large batch file - ingest data from different sources 
        + data ingestion tool 
    + storage tier 
        + HDFS - raw form 
    + insights tier 
        + tool = query data stored in the datalake - create insights - sql based / 3 GL
+ Hadoop security model     
    + HDFS permissions model 
    + File permission 
    + File-grained permissions with HDFS ACLs 

+ Apache Ranger - Apache Flume - Apache Zeppelin - Set up data lake with Hadoop 
    + Apache Ranger : policies to control the data access in the hadoop - web based - audit log - analytics data 0 
    + Install Apache Ranger
    + Test connection with Hadoop cluster 
    + Check audit log 
+ Apache Flume : handle massive volume of incoming data and store it on HDFS - distributed - data - web logs - sensor data - stored in HDFS for analysis and distribution 
+ Design of Flume 
    + Source
    + Channel 
    + Sink 
    + Data ingestion 
    + Event = payload with optional string header 
    + Install Apache Flume 
    + Running Apache Flume 
+ Apache Zeppelin 
    + web based tool - data analytics and visualization 
    + read data from many data back ends - Hive - Postgre - Cassandra 
    + Test driving Zeppelin - create note in notebook 
    + Shell commands
    + Project : 
        + Gold price movement table in Hive 
+ Tech stack for Data lake 
    + Ingestion tier : Apache Flume, HDFS, Apache sqoop
    + Storage tier : HDFS 
    + Insights tier : Apache Zeppelin , Hive QL 
    + Operations tier : Apache Ranger, HDFS 
+ Design data pipeline : 
    + Store Hive table - Sqoop and run it using Crob job once per night to refresh data in the data lake 
    + Ranger = restrict data table permission 
    + CRM system - sqoop job - Hive - HDFS - Ranger - Unix groups/users - user sync - hive server - zeppelin - beeline 
    + Building the data pipeline 
    + Set up access control - Test data pipeline - Schedule data loading 
    + Growing data lake 
    + Cron  = scheduling tool - manage multiple scheduled jobs to load data in Data Lake - Scheduling tool: Oozie - log - audit - retry function
    + Add stock feed in the Data Lake 
    + Fetch data from Yahoo service
    + Config Flume 
    + Run Flume as Stock feeder to Data lake 
    + Transform the data in Data Lake 
    + Growing data lake 
        + integrate more data sources in the Data Lake 
        + Complex transformation 
        + Data science tool : R or Spark 
        + Simple scheduling tool - cron 
        + Need enterprise grade scheduler - run various ETL jobs 
    

### Analyze sensor data using Hadoop 
+ Create solution outline 
    + Load data into HDFS using batch mode - Flume
    + Load data into HDFS using streaming mode - Kafka 
    + Data analysis Hive 
    + Data visualization - Grafana - Open TSDB 
    + Sensor - Kafka - Flume - Agent - HDFS sink - Flume - Flume agent - Custom HTTP Open TSDB Sink - HDFS - Hive - Open TSDB - Time series - Grafana visualization - HBase (Storage)

    + Real time - batch analytics - collect data into kafka topics - Flume agents to write data to HDFS + Open TSDB (series database that uses HBase as its storage engine) - Grafana for the time series 
+ Tech stack 
    + Kafka
        + pub sub messaging service 
        + distributed - reliable - scalable - create topic - pub message to them - define subscribers to these topic 
    + Flume 
    + HDFS 
        + Hive
        + Open TSDB
            + Scalable time series database - use HBase = storage engine 
    + HBase 
        + Hadoop database 
        + NoSQL - key value pairs
        + HBase = storage engine for OpenTSDB 
    + Grafana 
        + Query and visualize time series data from various source : ES, Open TSDB , Graphite, InfluxDB - time series data visualization 
        + Services are installed and run well on cluster 
chua hoc het 
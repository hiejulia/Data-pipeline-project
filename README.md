# Data pipeline projects 
(I am maintaining this project and add more demos for Hadoop distributed mode, Hadoop deployment on cloud, etc. Please give me some stars as support.)

### Architect big data applications 
- Data input : Apache Sqoop, Apache Flume 

### Hadoop 
+ Tools : Pig, Hive, 
+ Hadoop streaming 
    + process HTTP server log script 
    + stream MapReduce job
    + Linux shell utitlity program as Mapper and Reducer 
+ Hadoop to custom metrics 

### Spark 
- Architecture 


### Apache Storm 
- Storm cluster mode (distributed mode)
    - multi machine Storm cluster 
- Zookeeper, Nimbus, and Supervisor
- Storm client 


##### Start Apache Storm
- Start Zookeeper process
    - `../zookeeper/bin/./zkServer.sh start`
    - `../zookeeper/bin/./zkServer.sh status`
    - `../zookeeper/bin/./zkServer.sh stop`
- Nimbus master daemon 
    - `./storm nimbus`
    - `./storm supervisor`
    - `./storm ui`
    - `./storm logviewer`

- JZMQ
- Netty 
- `./storm jar <path-to-topology-jar> <class-with-the-main> <arg1> … <argN>`

- More configs : https://github.com/apache/storm/blob/master/conf/defaults.yaml

- Topology 
    Worker
    Executor
    Task

- Tuning paralel in Storm 

- Manage Storm 
    - Storm administration over a cluster
    - supervisord
    - Supervisord installation and configuration
    - http://supervisord.org/
        - supervisorctl
        - Web server
        - http://localhost:9001/
        - XML-RPC interface
        - Machines
            - For ex. 2 EC2 machine 

        - Storm and Zookeeper setup
            - Start zk in cluster mode (zoo.cfg)
            

#### Twitter streaming with Storm 
- Implement a spout that reads from Twitter
- Build topology components based on third-party Python libraries
- Compute statistics and rankings over rolling time periods
- Read custom configuration settings from topology.yaml
- Use "tick tuples" to execute logic on a schedule






### Structured streaming with Spark 
- Spark SQL 
- Source stream with defined schema
- Stream of events 
- query 
- create output stream of processed events
- dataset : public NASA-weblogs http://ita.ee.lbl.gov/html/contrib/NASA-HTTP.html. (source code & demo : processNASAWebServerLogs)
- Batch Analytics : intermezzo 



- Tech stack 
    - Spark Notebook : https://github.com/spark-notebook/spark-notebook
    - Spark notebok based on IPython 
    - support spark using scala 
    - `export NOTEBOOKS_DIR=`pwd`/notebooks`
    - `./bin/spark-notebook`

- Structured streaming API (is doing )
    - Kafka source to consume the iot-data topic
    - a file sink to store the data into a Parquet file

### Spark SQL 
- cache data to improve performance of streaming application 
- Spark Session concurrently with the running Spark Streaming job
- Join optimization 
- broadcast optimization with an outer join 



### Hadoop set up 
+ Run single node Hadoop cluster 
<a href="https://imgur.com/aiAxe1g"><img src="https://i.imgur.com/aiAxe1g.png" title="source: imgur.com" /></a>
+ /usr/local/Celler/hadoop

+ Check : https://www.slideshare.net/SunilkumarMohanty3/install-apache-hadoop-on-mac-os-sierra-76275019
+ http://zhongyaonan.com/hadoop-tutorial/setting-up-hadoop-2-6-on-mac-osx-yosemite.html
+ Go to :`http://localhost:50070/dfshealth.html#tab-overview`
+ Start : hstart

+ Hadoop command: 
    + `hadoop fs -ls`
    + `hadoop fs -mkdir /hbp`
    + Upload a file in HDFS `hadoop fs -put <localsrc> ... <HDFS_dest_Path>`
    + go to : `http://localhost:50070/explorer.html#/hbp/ibm-stock`

+ For development :https://github.com/kiwenlau/hadoop-cluster-docker
+ HDFS 
    + DataNode - NameNode 
    + Each server - create a dir - Store HDFS data 
    + `http://{NAMENODE}:50070/`

+ Distributed mode 
    + Set up Hadoop Yarn in distributed cluster (multiple machines)
        + Choose master node 
        + set up yarn resource manager+ node manager 
        + `yarn-site.xml`
        + Start yarn daemon : ` $HADOOP_HOME/sbin/start-yarn.sh`
        + Start NameNode > find slave > SSH to start DataNode in remote server at start up 

+ Distributed providers  : HDP, Cloudera 


### Dataset 
+ `head`
+ date - opening stock quote - high - low - traded volume - closing price  
+ Clean dataset with command : `awk`,`sed`,`grep`    

### Run the program 
+ Copy jar to Hadoop 
+ Run the program on Hadoop system: `hadoop jar /hbp/ibm-stock/ibm-stock-1.0-SNAPSHOT.jar /hbp/ibm-stock/ibm-stock.csv /hbp/ibm-stock/output`
+ Check output dir :  `hadoop fs -ls /hbp/ibm-stock/output`
+ Copy file from HDFS to local file system : `hadoop fs -get /hpb/ibm-stock/output/part-r-00000 home/Users/hien/results.csv`
+ Check `head home/Users/hien/results.csv`


# Benchmark 
+ DFSIO
    + `hadoop jar \
$HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-client-jobclient-*-tests.jar \
TestDFSIO -write -nrFiles 32 –fileSize 1000`

+ Terasort 
    + ` hadoop jar \
$HADOOP_HOME/share/Hadoop/mapreduce/hadoop-mapreduce-examples-*.jar \
teragen 10000000 tera-in`
    + MR computation BM : `hadoop jar \
$HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-examples-*.jar \
terasort tera-in tera-out`
    + validate benchmark : `hadoop jar \
$HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-examples-*.jar \
teravalidate tera-out tera-validate`

2. Customer Analysis 
+ Collect data 
    + Customer master data : MySQL
    + Logs : text file
    + Twitter feeds : JSON
+ Load data from data sources in HDFS 
+ Mug data 
+ Create table in Hive to store data in format
+ Query and join tables 
+ Export data 

+ Set up stack: 
    + Hortonwork data platform HDP
    + Install HDP sandbox: 
        + HDP 2.3 
        + HDP : hive, squoop ,



3. Fraud Detection system 
+ Clean dataset 
+ Create model 
+ Using: Spark and Hadoop 
+ Problem: predict payment transaction is suspect 
+ Build model : 
    + Find relevant field: 



# Apache Spark 2
+ Spark ecosystem : 
    + Spark core 
    + Spark streaming
    + Spark SQL 
    + MLlib
    + GraphX 
    + Spark-R

+ Apache Spark component: 
+ RDD 
+ SparkSQL


+ navigate to : `localhost:4040`
+ run spark-shell : `$SPARK_HOME/bin/spark-shell`

+ Word count 
    + Create pairRDD : `valpairRDD=stringRdd.map( s => (s,1))`
    + Run reducebykey to count the occurency of each word : `alwordCountRDD=pairRDD.reduceByKey((x,y) =>x+y)`
    + Run the collect to see the result : `valwordCountList=wordCountRDD.collect`

+ Find the sum of integers 
    + Create RDD of even number from integers : `valintRDD = sc.parallelize(Array(1,4,5,6,7,10,15))`
    + Filter even numbers from RDD : `valevenNumbersRDD=intRDD.filter(i => (i%2==0))`
    + Sum the even numbers from RDD : `val sum =evenNumbersRDD.sum`

+ Count the number of words in file : 
    + Read txt file : `cat people.txt`
    + Read file from Apache Spark shell : `val file=sc.textFile("/usr/local/spark/examples/src/main/resources/people.txt")`
    + Flaten the file, prcess and split , with each word : `valflattenFile = file.flatMap(s =>s.split(", "))`
    + Check the content of RDD : `flattenFile.collect`
    + Count all words from RDD : `val count = flattenFile.count`


+ Working with Data and Storage 

## Run Spark in a cluster 
+ Spark manager master : Yarn, Mesos
+ Package Spark application 
    + Local : spark-submit script - jar to local Spark cluster 
    + Run Spark application on a Cluster : spark-submit - upload jar to linux cluser > run `spark-submit` script on the cluster 


## Optimize and tune Apache Spark jobs 
+ Partition 
+ Caching 
+ Persist RDDs 
+ Scale up : Yarn cluster + Amazon EMR
+ Broadcast across different nodes on Apache Spark 
+ 


## Test project with 

1. IBM stock project 
+ Get IBM stock dataset 
+ Clean the dataset 
+ Load dataset on the HDFS 
+ Build MapReduce program 
+ Process/ Analyse result



### Spark streaming HBase 
- Run Spark, HBase with Docker 
- Create HBase table to write to 
- Run streaming job 
- 


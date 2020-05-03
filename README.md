# Data pipeline projects 
(I am maintaining this project and add more demos for Hadoop distributed mode, Hadoop deployment on cloud, Spark high performance, Spark streaming application demos, Spark distributed cluster etc. Please give me some stars as support.)

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
<a href="https://imgur.com/B11fAX6"><img src="https://i.imgur.com/B11fAX6.png" title="source: imgur.com" /></a>

- Cluster manager : YARN, Mesos, Kuberneste

### MapReduce

### Distributed Stream Processing 

### Stateful Stream Processing in a Distributed System 

### Datasource 
- kafka, FLume, TCP socket,etc


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



### Checkpoint 

- streaming job for video data 
    - videoPlayed events process the timestamp embedded in the event to determine the time-based aggregation 

    - `VideoPlayed(video-id, client-id, timestamp)`
    - `DStream[VideoPlayed]`
    - `trackVideoHits function`

- `http://<host>:4040/jobs/job/?id=0`

### Project 
#### Generated sequences of signals, like the measurement of a sensor or GPS signals from moving vehicles


#### Device monitoring



#### Fault detection








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

### performance tuning 
- Limiting the Data Ingress with Fixed-Rate Throttling
- Backpressure
    - Tuning the Backpressure PID
    - Custom Rate Estimator
- Dynamic Throttling
- Caching
- Speculative Execution


### Monitor spark streaming 
- Metrics Subsystem


### Sliding Windows 




### Ref 
- Book 
    - Hadoop 
    - Introduction to hadoop 
    - Apache Spark 
    - Streaming processing with Apache Spark 
    - Structured Streaming: A declarative API for real time application in Apache Spark 
    - Optimize spark streaming to efficiently process amazon kinesis stream 
    - Spark : The definitive guide 
    - Benchmarking streaming computation engines at Yahoo
    - Deep dive with spark streaming 
    - Adaptive stream processing using dynamic batch sizing 
    - Improve fault tolerance and zero data loss in spark streaming 
    - MapReduce: Simplified data processing on large cluster 
    - On the minimal synchronism needed for distributed consensus 
    - MapReduce : Simplified data processing on large cluster 
    - Venkat, B., P. Padmanabhan, A. Arokiasamy, and R. Uppalapati. “Can Spark Streaming survive Chaos Monkey?” The Netflix Tech Blog, March 11, 2015. http://bit.ly/2WkDJmr.
    - Dean, Jeff, and Sanjay Ghemawat. “MapReduce: Simplified Data Processing on Large Clusters,” OSDI San Francisco, December, 2004. http://bit.ly/15LeQej.
    - Das, Tathagata. “Improved Fault Tolerance and Zero Data Loss in Spark Streaming,” Databricks Engineering blog. January 15, 2015. http://bit.ly/2HqH614.
    - Das, Tathagata, and Yuan Zhong. “Adaptive Stream Processing Using Dynamic Batch Sizing,” 2014 ACM Symposium on Cloud Computing. http://bit.ly/2WTOuby.
    - Das, Tathagata. “Deep Dive With Spark Streaming,” Spark meetup, June 17, 2013. http://bit.ly/2Q8Xzem.
    - Chintapalli, S., D. Dagit, B. Evans, R. Farivar, T. Graves, M. Holderbaugh, Z. Liu, K. Musbaum, K. Patil, B. Peng, and P. Poulosky. “Benchmarking Streaming Computation Engines at Yahoo!” Yahoo! Engineering, December 18, 2015. http://bit.ly/2bhgMJd.
    




    - Vavilapalli, et al. “Apache Hadoop YARN: Yet Another Resource Negotiator,” ACM Symposium on Cloud Computing, 2013. http://bit.ly/2Xn3tuZ.
    - Nasir, M.A.U. “Fault Tolerance for Stream Processing Engines,” arXiv.org:1605.00928, May 2016. http://bit.ly/2Mpz66f.
    - Lyon, Brad F. “Musings on the Motivations for Map Reduce,” Nowhere Near Ithaca blog, June, 2013, http://bit.ly/2Q3OHXe.
    - Lin, Jimmy, and Chris Dyer. Data-Intensive Text Processing with MapReduce. Morgan & ClayPool, 2010. http://bit.ly/2YD9wMr.
    - Kreps, Jay. “Questioning the Lambda Architecture,” O’Reilly Radar, July 2, 2014. https://oreil.ly/2LSEdqz.
    - Kleppmann, Martin. “A Critique of the CAP Theorem,” arXiv.org:1509.05393, September 2015. http://bit.ly/30jxsG4.
    - Halevy, Alon, Peter Norvig, and Fernando Pereira. “The Unreasonable Effectiveness of Data,” IEEE Intelligent Systems (March/April 2009). http://bit.ly/2VCveD3.
    -  Gibbons, J. “An unbounded spigot algorithm for the digits of π,” American Mathematical Monthly 113(4) (2006): 318-328. http://bit.ly/2VwwvH2.

    -  Fischer, M. J., N. A. Lynch, and M. S. Paterson. “Impossibility of distributed consensus with one faulty process,” Journal of the ACM 32(2) (1985): 374–382. http://bit.ly/2Ee9tPb.

    - Dósa, Gÿorgy. “The Tight Bound of First fit Decreasing Bin-Packing Algorithm Is FFD(I)≤(11/9)OPT(I)+6/9.” In Combinatorics, Algorithms, Probabilistic and Experimental Methodologies. Springer-Verlag, 2007.
    - Doley, D., C. Dwork, and L. Stockmeyer. “On the Minimal Synchronism Needed for Distributed Consensus,” Journal of the ACM 34(1) (1987): 77-97. http://bit.ly/2LHRy9K.

    - Dünner, C., T. Parnell, K. Atasu, M. Sifalakis, and H. Pozidis. “High-Performance Distributed Machine Learning Using Apache Spark,” December 2016. http://bit.ly/2JoSgH4.



    - Koeninger, Cody, Davies Liu, and Tathagata Das. “Improvements to Kafka Integration of Spark Streaming,” Databricks Engineering blog, March 30, 2015. http://bit.ly/2Hn7dat.

    - Miller, H., P. Haller, N. Müller, and J. Boullier “Function Passing: A Model for Typed, Distributed Functional Programming,” ACM SIGPLAN Conference on Systems, Programming, Languages and Applications: Software for Humanity, Onward! November 2016: (82-97). http://bit.ly/2EQASaf.

    - Lamport, Leslie. “The Part-Time Parliament,” ACM Transactions on Computer Systems 16(2): 133–169. http://bit.ly/2W3zr1R.

    - Kestelyn, J. “Exactly-once Spark Streaming from Apache Kafka,” Cloudera Engineering blog, March 16, 2015. http://bit.ly/2EniQfJ.

    - Valiant, L.G. “Bulk-synchronous parallel computers,” Communications of the ACM 33:8 (August 1990). http://bit.ly/2IgX3ar.


    - Sharp, Alexa Megan. “Incremental algorithms: solving problems in a changing world,” PhD diss., Cornell University, 2007. http://bit.ly/2Ie8MGX. 

    - Maas, Gérard. “Tuning Spark Streaming for Throughput,” Virdata Engineering blog, December 22, 2014. http://www.virdata.com/tuning-spark/.



    - Shapira, Gwen. “Building The Lambda Architecture with Spark Streaming,” Cloudera Engineering blog, August 29, 2014. http://bit.ly/2XoyHBS.





    - Venkataraman, S., P. Aurojit, K. Ousterhout, A. Ghodsi, M. J. Franklin, B. Recht, and I. Stoica. “Drizzle: Fast and Adaptable Stream Processing at Scale,” Tech Report, UC Berkeley, 2016. http://bit.ly/2HW08Ot.


    - [Zaharia2011] Zaharia, Matei, Mosharaf Chowdhury, et al. “Resilient Distributed Datasets: A Fault-Tolerant Abstraction for In-Memory Cluster Computing,” UCB/EECS-2011-82. http://bit.ly/2IfZE4q.
    - Zaharia, Matei, Tathagata Das, et al. “Discretized Streams: A Fault-Tolerant Model for Scalable Stream Processing,” UCB/EECS-2012-259. http://bit.ly/2MpuY6c.


- Confluent docs 
- Research paper 



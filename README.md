# hadoop-projects

1. IBM stock project 
+ Get IBM stock dataset 
+ Clean the dataset 
+ Load dataset on the HDFS 
+ Build MapReduce program 
+ Process/ Analyse result



### Hadoop set up 
+ Run single node Hadoop cluster 
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
    + 

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
    + 







Chua hoc
+ 4 (RDD transformation),
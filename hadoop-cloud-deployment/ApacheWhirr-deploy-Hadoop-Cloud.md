# Apache Whirr 
+ Deploy cluster on the cloud

# How to run 
+ Add hadoop yarn properties in Whirr installation 
+ point hadoop - yarn ec2 properties - 
+ `bin/whirr launch-cluster --config hadoop-yarn-ec2.properties`
+ Provision EC2 Hadoop cluster 
    + script - start proxy 
    + `hadoop-proxy.sh`

+ Create dir : input data - in HDFS - update data to the dir 
    `hdfs dfs –mkdir /user/<user_name>
$ hdfs  dfs -mkdir wc-input-data
$ hdfs dfs -put sample.txt wc-input-data`

+ Run Hadoop cluster - `hadoop jar test-hadoop.jar WordCount wc-input-data wc-out`
+ View out data 
    + `hadoop fs -ls wc-out`
    + `hadoop fs -cat wc-out/part-* | more`

+ Shut down cluster 
    `bin/whirr destroy-cluster --config hadoop.properties`


    
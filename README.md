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

+ Hadoop command: 
    + `hadoop fs -ls`
    + `hadoop s -mkdir /hbp`
    + Upload a file in HDFS `hadoop fs -put <localsrc> ... <HDFS_dest_Path>`
    + go to : `http://localhost:50070/explorer.html#/hbp/ibm-stock`
    




### Dataset 
+ `head`
+ date - opening stock quote - high - low - traded volume - closing price  
+ Clean dataset with command : `awk`,`sed`,`grep`    
+




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


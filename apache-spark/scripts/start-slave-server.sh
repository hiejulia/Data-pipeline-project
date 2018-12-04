#!/usr/bin/env bash




# Start Apache Spark slave server 

echo "Start Apache Spark slave server ...."


cd $SPARK_HOME
sbin/start-slave.sh spark://spark-master:7077


echo "Slave server Apache Spark is started"
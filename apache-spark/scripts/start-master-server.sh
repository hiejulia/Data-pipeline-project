#!/usr/bin/env bash


# Start Apache Spark master server 

echo "Start apache spark master server...."


cd $SPARK_HOME 



sbin/start-master.sh



echo "Apache spark master server is started"
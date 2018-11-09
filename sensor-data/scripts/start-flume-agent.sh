#!/bin/bash

# Start Flume agent - fetch data from Kafka topic and write it to HDFS 
flume-ng agent -n flume1 -c /usr/hdp/current/flume-server/conf
-f /Users/hien/Documents/desktop/sp/project/hadoop-projects/sensor-data/config/kafka.conf -Dflume.root.logger=INFO,console
#!/usr/bin/env bash



echo "Running Spark shell in Yarn client mode ...."




cd $SPARK_HOME 




./bin/spark-shell -master yarn -deploy-mode client


echo "----------"



echo "Done run Spark shell in Yarn client mode.."

#!/usr/bin/env bash



echo "Start spark shell yarn cluster mode ....."



cd $SPARK_HOME 



./bin/spark-submit --master yarn --deploy-mode cluster --class org.apache.spark.examples.SparkPi examples/jars/spark-examples_2.11-2.1.1.jar


# bin spark-submit - master - yarn - deploy mode - cluster - class sparkpi - with class - 

echo "Stop spark shell yarn cluster mode ...."
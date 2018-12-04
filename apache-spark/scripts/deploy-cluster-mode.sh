#!/usr/bin/env bash



echo "Deploying to Apache spark cluster node ....."

cd $SPARK_HOME 


./bin/spark-submit --master spark://spark-master:7077--deploy-mode cluster --class org.apache.spark.examples.SparkPi examples/jars/spark-examples_2.11-2.1.1.jar 




echo "Done deploy to Apache spark cluster mode ...."
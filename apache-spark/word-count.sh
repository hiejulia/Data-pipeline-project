#!/usr/bin/env bash



echo "Word count..." 

# val pair rdd - map 
val valpairRDD=stringRdd.map( s => (s,1));


# reduce - reduce by key - x, y -> x+y 
val valwordCountRDD=valpairRDD.reduceByKey((x,y) =>x+y);

# collect the value 
val valwordCountList=valwordCountRDD.collect;


# show word count list 
valwordCountList;


echo "Word count..."





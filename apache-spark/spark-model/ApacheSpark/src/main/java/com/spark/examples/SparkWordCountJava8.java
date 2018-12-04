package com.spark.examples;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

/*

Spark word count java 8 - class

 */
public class SparkWordCountJava8
{
    public static void main(String[] args) {

        SparkConf conf =new
                SparkConf().setMaster("local").setAppName("WordCount");
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);
        JavaRDD<String> inputData =
                javaSparkContext.textFile("path_of_input_file");

        JavaPairRDD<String, Integer> flattenPairs =
                inputData.flatMapToPair(text -> Arrays.asList(text.split("
                        ")).stream().map(word ->new Tuple2<String,Integer>(word,1)).iterator());

                        JavaPairRDD<String, Integer> wordCountRDD = flattenPairs.reduceByKey((v1,
                                                                                              v2) -> v1+v2);
        wordCountRDD.saveAsTextFile("path_of_output_file");
    }

}



// CSV file - no ngoi no code ca 1 tieng dong ho - no khong muon bi xao nhan
// the thoi





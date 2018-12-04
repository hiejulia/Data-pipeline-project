package com.spark.examples;


/**
 * Class SparkWordCount
 *
 */
public class SparkWordCount
{
    public static void main(String[] args) {
        SparkConf conf =new
                SparkConf().setMaster("local").setAppName("WordCount");
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);
        JavaRDD<String> inputData =
                javaSparkContext.textFile("path_of_input_file");

        JavaPairRDD<String, Integer> flattenPairs =
                inputData.flatMapToPair(new PairFlatMapFunction<String,
                        String, Integer>() {
                    @Override
                    public Iterator<Tuple2<String, Integer>> call(String text) throws
                            Exception {
                        List<Tuple2<String,Integer>> tupleList =new ArrayList<>();
                        String[] textArray = text.split(" ");
                        for (String word:textArray) {
                            tupleList.add(new Tuple2<String, Integer>(word, 1));
                        }
                        return tupleList.iterator();
                    }
                });
        JavaPairRDD<String, Integer> wordCountRDD = flattenPairs.reduceByKey(new
                                                                                     Function2<Integer, Integer, Integer>() {
                                                                                         @Override
                                                                                         public Integer call(Integer v1, Integer v2) throws Exception {
                                                                                             return v1+v2;
                                                                                         }
                                                                                     });
        wordCountRDD.saveAsTextFile("path_of_output_file");
    }
}

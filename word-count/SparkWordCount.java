public class SparkWordCount { 
    public static void main(String[] args) { 
   
    
     SparkConf conf = new SparkConf().setMaster("local").setAppName("WordCount");
     JavaSparkContext javaSparkContext = new JavaSparkContext(conf); 
     JavaRDD<String> inputData = javaSparkContext.textFile("./people.txt");

     // Flatten the text file
     JavaPairRDD<String, Integer> flattenPairs = 
     inputData.flatMapToPair(text -> Arrays.asList(text.split(" ")).stream().map(word ->new Tuple2<String,Integer>(word,1)).iterator());

    // Reduce by key 
   
    JavaPairRDD<String, Integer> wordCountRDD = flattenPairs.reduceByKey((v1,
    v2) -> v1+v2); 
    wordCountRDD.saveAsTextFile("./result.txt"); 

    } 
  } 
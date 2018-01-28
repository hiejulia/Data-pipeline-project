import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class WordCount {
    public static class TokenizerMapperWordCount extends Mapper<Object, Text, Text, IntWritable> {

        public final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
        // MAP

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            Configuration conf = context.getConfiguration();
            String filterString = conf.get("filterString");
            System.out.println(value.toString());

            if(value.toString().startsWith(filterString)){
                while(itr.hasMoreElements()){
                    word.set(itr.nextToken());
                    context.write(word,one);
                }

            }

        }


    }

    // REDUCER
    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            int count = 0 ;

            for(IntWritable val:values){
                sum += val.get();
                count ++;
            }
            result.set(sum);
            result.set(count);

            context.write(key,result);
        }
    }



    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("filterString", args[2]);
        Job job = Job.getInstance(conf, "Frequency count");
        job.setJarByClass(WordCount.class);
        job.setMapperClass(TokenizerMapperWordCount.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));// input path
        FileOutputFormat.setOutputPath(job, new Path(args[1]));// output path

        //WordCount.filterString = args[2];
        System.exit(job.waitForCompletion(true) ? 0 : 1);


    }




}

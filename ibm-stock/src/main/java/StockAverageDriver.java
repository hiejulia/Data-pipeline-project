import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * DRIVER CLASS - Driver class 
 * @author hien 
 */

public class StockAverageDriver {
    // void main - what the fuck cai kieu cua no - 
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        // Init Job 
        Job job = Job.getInstance();

        job.setJarByClass(StockAverageMapper.class);
        job.setJobName("IBM-Stock");

        
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // configure job  - mapperclass- reducer - 
        job.setMapperClass(StockAverageMapper.class);
        job.setReducerClass(StockAverageReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        boolean isSuccess = job.waitForCompletion(true);
        System.exit(isSuccess ? 0 : 1);
    }
}

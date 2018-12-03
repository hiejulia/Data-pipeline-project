
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * REDUCER CLASS
 * @author hien
 */


public class StockAverageReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
    // reduce function
    public void reduce(Text key, Iterable<DoubleWritable> values, Context context)throws IOException, InterruptedException {
//        double average = 0 ;
        double total = 0;

        int count = 0;

        for(DoubleWritable value:values){
            total += value.get();
            System.out.print("Reducer "+key+" "+ total);
            count ++;
        }
//        average = total/count;
        context.write(key,new DoubleWritable(total));


        // avg = 0 
        // set int count = 0 
        // for value value : values 

        // avg += value.get 
        // save data 
        // context.write 


    }

}



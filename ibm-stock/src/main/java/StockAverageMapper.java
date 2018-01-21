import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Mapper class
 * @author hien
 */
public class StockAverageMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    private DoubleWritable quote = new DoubleWritable(1);
    private Text word = new Text();
    // map function
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //Extract the tokens from the line text
        String line = value.toString();
        String[] tokens = line.split(",");

        //Extract the year value from date
        String year = tokens[0].split("-")[0];

        //Extract the stock quote and convert it into a number
        String quoteStr = tokens[1];
        String quoteStr1 = tokens[2];
        double quoteVal = Double.parseDouble(quoteStr);
        double quoteVal1 = Double.parseDouble(quoteStr1);

        //Set the key
        word.set(year);

        //Set the value
//        quote.set(quoteVal);
        quote.set(quoteVal1);


        context.write(word, quote);

    }


}

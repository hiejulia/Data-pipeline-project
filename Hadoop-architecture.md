FSDataOutputStream out=fs.create(outFile,true);


String msg=”Test to create a file on HDFS”;
out.writeUTF(msg);
Close FSDataOutputStream using the close() method.
out.close();
As another example, read from an input file and write the file to the HDFS.
byte[] buffer = new byte[BUFFER_SIZE];
Path inFile = new Path("input.txt");
Path outFile = new Path("output.txt");
FSDataInputStream in = fs.open(inFile);
FSDataOutputStream out=fs.create(outFile);
int bytesRead = 0;
         while ((bytesRead = in.read(buffer)) > 0) {
           out.write(buffer, 0, bytesRead);       
}
in.close();
out.close();
The input, output paths may be specified as command-line arguments.
Path inFile = new Path(argv[0]);
Path outFile = new Path(argv[1]);
In addition to the create(Path) method, the FileSystem class provides several other create methods to obtain a FSDataOutputStream to a path on the HDFS.
An example application to read and write to the HDFS is as follows.
    import java.io.IOException;

// no nhin con be nay lien tuc - the thoi - no ngoi cai kieu 









import org.apache.hadoop.conf.Configuration;
    import org.apache.hadoop.fs.FileSystem;
    import org.apache.hadoop.fs.FSDataInputStream;
    import org.apache.hadoop.fs.FSDataOutputStream;
    import org.apache.hadoop.fs.Path;
    public class HDFSReadWriteFile {
      public static void main (String[] argv) throws IOException {
        Configuration conf = new Configuration();
       FileSystem fs = FileSystem.get(conf);
        Path inFile = new Path("input.txt");
        Path outFile = new Path("output.txt");
       FSDataInputStream in = fs.open(inFile);
        FSDataOutputStream out = fs.create(outFile);
       byte buffer[] = new byte[512];
        try {
          int bytesRead = 0;
          while ((bytesRead = in.read(buffer)) > 0) {
           out.write(buffer, 0, bytesRead);
          }
        } catch (IOException e) {
          System.err.println("Error while reading or writing file");
        } finally {
         in.close();
          out.close();
        }
      }
    }




no van an ma - chua den gio - no nhin caon be nay mai luon ay - 




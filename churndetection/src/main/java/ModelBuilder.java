import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.StringTokenizer;



/**
 * MODEL BUILDER
 */

public class ModelBuilder {
    private HashMap<String, Integer> freqYesHmp = null;
    private HashMap<String, Integer> freqNoHmp = null;
    private HashMap<String, Double> modelYesHmp = null;
    private HashMap<String, Double> modelNoHmp = null;

    private int totalRows = 0;

    public HashMap<String, Integer> getFreqYesHmp() {
        return freqYesHmp;
    }

    public void setFreqYesHmp(HashMap<String, Integer> freqYesHmp) {
        this.freqYesHmp = freqYesHmp;
    }

    public HashMap<String, Integer> getFreqNoHmp() {
        return freqNoHmp;
    }

    public void setFreqNoHmp(HashMap<String, Integer> freqNoHmp) {
        this.freqNoHmp = freqNoHmp;
    }

    public HashMap<String, Double> getModelYesHmp() {
        return modelYesHmp;
    }

    public void setModelYesHmp(HashMap<String, Double> modelYesHmp) {
        this.modelYesHmp = modelYesHmp;
    }

    public HashMap<String, Double> getModelNoHmp() {
        return modelNoHmp;
    }

    public void setModelNoHmp(HashMap<String, Double> modelNoHmp) {
        this.modelNoHmp = modelNoHmp;
    }

    /**
     * MACHINE LEARNING ALGORITHM
     */








    // BUILD MODEL FUNCTION

    public void buildModel(){
        totalRows = freqNoHmp.get("yes") + freqNoHmp.get("no");

    }





    public static void main(String[] args){
        ModelBuilder mb = new ModelBuilder();

    }
}

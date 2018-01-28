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

    public HashMap<String,Double> calculateProbability(HashMap<String, Integer> frequency, String target){
        HashMap<String, Double> model = new HashMap<String, Double>();
        double targetCount = frequency.get(target);
        for (String feature : frequency.keySet()) {
            if (!target.equals(feature)) {
                String key = "P" + target + feature;
                //System.out.println(key + frequency.get(feature) + frequency.get(target));
                double featureFrequency = frequency.get(feature);
                model.put(key, featureFrequency / targetCount);
            } else {
                String key = "P" + target;
                //System.out.println(key + frequency.get(feature) + frequency.get(target));
                model.put(key, targetCount / totalRows);
            }
        }
        System.out.println("Model==>"+ model);
        return model;

    }

    public  HashMap<String, Integer> initFrequencyHmp(String fileName) {
        BufferedReader in;
        HashMap<String, Integer> freqHmp = new HashMap<String, Integer>();
        try {
            in = new BufferedReader(new FileReader(fileName));
            String line = "";
            while ((line = in.readLine()) != null) {
                String parts[] = line.split("\t");
                freqHmp.put(parts[0], Integer.parseInt(parts[1]));
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(freqHmp.toString());
        return  freqHmp;
    }



    // BUILD MODEL FUNCTION

    public void buildModel(){
        totalRows = freqNoHmp.get("yes") + freqNoHmp.get("no");
        modelNoHmp = calculateProbability(freqNoHmp, "no");
        modelYesHmp = calculateProbability(freqYesHmp, "yes");

    }
    public void predict(String row) {

        StringTokenizer st = new StringTokenizer(row, " ");
        double yesLikelyhood = modelYesHmp.get("Pyes");
        double noLikelyhood = modelNoHmp.get("Pno");

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            String yesKey = "P" + "yes" + token;
            String noKey = "P" + "no" + token;
            yesLikelyhood = yesLikelyhood * modelYesHmp.get(yesKey);
            noLikelyhood = noLikelyhood * modelNoHmp.get(noKey);
        }

        System.out.println("YesLikely=" + yesLikelyhood + " NoLikely=" + noLikelyhood);

        if (yesLikelyhood > noLikelyhood )
        {
            System.out.println(row + " CHURN");
        }
        else
        {
            System.out.println(row + " NOCHURN");
        }
    }



    public static void main(String[] args) {
        ModelBuilder mb = new ModelBuilder();
        mb.setFreqYesHmp(mb.initFrequencyHmp("out-yes/part-r-00000"));
        mb.setFreqNoHmp(mb.initFrequencyHmp("out-no/part-r-00000"));
        mb.buildModel();
        mb.predict("High  Old  Less  Normal  IOS  Regular");
    }

}

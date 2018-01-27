import java.io.IOException;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Locale;
import java.text.NumberFormat;
import java.text.ParseException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class ResponsePrediction {

    /**
     MAP CLASS -> DRIVER CLASS CONTAINS THE MAIN SERVE AS THE ENTRY POINT FOR MAPREDUCE JOB
     */
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
        static enum Counters {
            INPUT_LINES
        }

        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)
                throws IOException {


            String lines = value.toString();
            String[] data = lines.split(",");
            output.collect(value,new Text(response(data)));
            reporter.incrCounter(Counters.INPUT_LINES, 1);

        }


    }


    /***
     REDUCER CLASS
     @extends MapReduceBase
     */
    public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, IntWritable> {
        private long numRecords = 0;

        public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, IntWritable> output,
                           Reporter reporter) throws IOException {
            int sum = 0;
            Text prediction = null;
            while (values.hasNext()) {
                sum += 1;
                prediction = values.next();
            }
            output.collect(new Text(key + "," + prediction), new IntWritable(sum));
            if ((++numRecords % 100) == 0) {
                reporter.setStatus("Finished processing " + numRecords + " lines " + "to the output directory.");
            }
        }

    }

    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(ResponsePrediction.class);
        conf.setJobName("responseprediction");// set job name
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(Text.class);
        conf.setMapperClass(Map.class);// set mapper class
        conf.setReducerClass(Reduce.class);// set reducer class
        conf.setInputFormat(TextInputFormat.class);// set input format
        conf.setOutputFormat(TextOutputFormat.class);// set output format
        FileInputFormat.setInputPaths(conf, new Path("file:///Users/hien/Desktop/hadoop-projects/marketing-analysis/data/inputdata.csv"));// set input paths
        FileOutputFormat.setOutputPath(conf, new Path("file:///Users/hien/Desktop/hadoop-projects/marketing-analysis/output"));// set output path
        JobClient.runJob(conf);// run job
    }

    public static String response(String[] data) {
        HashMap<String, Object> inputData = new HashMap<String, Object>();
        //custid is not used by the predictor
        inputData.put("custid", (data[0] == null) ? null : new Double(parseLocale(data[0]).doubleValue()));
        inputData.put("age", (data[1] == null) ? null : new Double(parseLocale(data[1]).doubleValue()));
        inputData.put("income", (data[2] == null) ? null : new Double(parseLocale(data[2]).doubleValue()));
        inputData.put("gender", data[3]);

        inputData.put("folder", (data[4] == null) ? null : new Double(parseLocale(data[3]).doubleValue()));

        String response = predictResponse(inputData);// call predictResponse with input data
        return response;
    }

    public static Number parseLocale(String item) {
        try {
            Number number = NumberFormat.getNumberInstance(Locale.US).parse(item);
            return number;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }
    // predictResponse (inputData)
    public static String predictResponse(HashMap<String, Object> inputData) {

        Double age = (Double) inputData.get("age");
        Double income = (Double) inputData.get("income");
        String gender = (String) inputData.get("gender");
        Double folder = (Double) inputData.get("folder");


        if (age == null) {
            return "0";
        } else if (age > 43) {
            if (income == null) {
                return "0";
            } else if (income > 120875) {
                return "1";
            } else if (income <= 120875) {
                if (folder == null) {
                    return "0";
                } else if (folder > 1) {
                    if (income > 31949) {
                        if (age > 65) {
                            if (income > 86446) {
                                return "0";
                            } else if (income <= 86446) {
                                if (income > 82692) {
                                    return "0";
                                } else if (income <= 82692) {
                                    if (income > 33658) {
                                        if (income > 41092) {
                                            if (age > 89) {
                                                return "1";
                                            } else if (age <= 89) {
                                                if (age > 88) {
                                                    return "0";
                                                } else if (age <= 88) {
                                                    if (income > 43452) {
                                                        if (income > 81796) {
                                                            return "0";
                                                        } else if (income <= 81796) {
                                                            if (income > 80495) {
                                                                return "1";
                                                            } else if (income <= 80495) {
                                                                if (folder > 4) {
                                                                    if (age > 66) {
                                                                        if (age > 72) {
                                                                            if (income > 65450) {
                                                                                return "0";
                                                                            } else if (income <= 65450) {
                                                                                return "0";
                                                                            }
                                                                        } else if (age <= 72) {
                                                                            return "0";
                                                                        }
                                                                    } else if (age <= 66) {
                                                                        return "0";
                                                                    }
                                                                } else if (folder <= 4) {
                                                                    if (age > 86) {
                                                                        return "0";
                                                                    } else if (age <= 86) {
                                                                        if (age > 78) {
                                                                            if (income > 52694) {
                                                                                return "0";
                                                                            } else if (income <= 52694) {
                                                                                return "0";
                                                                            }
                                                                        } else if (age <= 78) {
                                                                            if (income > 52418) {
                                                                                if (income > 59342) {
                                                                                    if (income > 64419) {
                                                                                        if (age > 68) {
                                                                                            if (age > 77) {
                                                                                                return "0";
                                                                                            } else if (age <= 77) {
                                                                                                return "0";
                                                                                            }
                                                                                        } else if (age <= 68) {
                                                                                            return "0";
                                                                                        }
                                                                                    } else if (income <= 64419) {
                                                                                        return "0";
                                                                                    }
                                                                                } else if (income <= 59342) {
                                                                                    if (age > 69) {
                                                                                        return "0";
                                                                                    } else if (age <= 69) {
                                                                                        return "0";
                                                                                    }
                                                                                }
                                                                            } else if (income <= 52418) {
                                                                                if (age > 69) {
                                                                                    if (age > 77) {
                                                                                        return "1";
                                                                                    } else if (age <= 77) {
                                                                                        return "0";
                                                                                    }
                                                                                } else if (age <= 69) {
                                                                                    return "0";
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else if (income <= 43452) {
                                                        return "0";
                                                    }
                                                }
                                            }
                                        } else if (income <= 41092) {
                                            if (gender == null) {
                                                return "0";
                                            } else if (gender.equals("0")) {
                                                return "0";
                                            } else if (!gender.equals("0")) {
                                                if (income > 35610) {
                                                    return "0";
                                                } else if (income <= 35610) {
                                                    return "1";
                                                }
                                            }
                                        }
                                    } else if (income <= 33658) {
                                        return "0";
                                    }
                                }
                            }
                        } else if (age <= 65) {
                            if (income > 84305) {
                                if (folder > 4) {
                                    return "0";
                                } else if (folder <= 4) {
                                    if (age > 63) {
                                        return "1";
                                    } else if (age <= 63) {
                                        if (age > 52) {
                                            return "0";
                                        } else if (age <= 52) {
                                            return "1";
                                        }
                                    }
                                }
                            } else if (income <= 84305) {
                                if (age > 47) {
                                    if (income > 70935) {
                                        if (age > 48) {
                                            if (age > 49) {
                                                if (age > 56) {
                                                    if (income > 72279) {
                                                        if (age > 58) {
                                                            return "0";
                                                        } else if (age <= 58) {
                                                            return "0";
                                                        }
                                                    } else if (income <= 72279) {
                                                        return "0";
                                                    }
                                                } else if (age <= 56) {
                                                    if (folder > 2) {
                                                        return "0";
                                                    } else if (folder <= 2) {
                                                        return "0";
                                                    }
                                                }
                                            } else if (age <= 49) {
                                                return "0";
                                            }
                                        } else if (age <= 48) {
                                            return "0";
                                        }
                                    } else if (income <= 70935) {
                                        if (income > 60837) {
                                            if (income > 64221) {
                                                if (age > 58) {
                                                    return "0";
                                                } else if (age <= 58) {
                                                    if (age > 56) {
                                                        return "0";
                                                    } else if (age <= 56) {
                                                        if (income > 67834) {
                                                            return "0";
                                                        } else if (income <= 67834) {
                                                            return "0";
                                                        }
                                                    }
                                                }
                                            } else if (income <= 64221) {
                                                if (age > 62) {
                                                    return "1";
                                                } else if (age <= 62) {
                                                    if (folder > 3) {
                                                        return "0";
                                                    } else if (folder <= 3) {
                                                        return "0";
                                                    }
                                                }
                                            }
                                        } else if (income <= 60837) {
                                            if (age > 61) {
                                                if (income > 59016) {
                                                    return "0";
                                                } else if (income <= 59016) {
                                                    if (income > 32517) {
                                                        if (folder > 3) {
                                                            if (age > 64) {
                                                                return "0";
                                                            } else if (age <= 64) {
                                                                if (income > 51517) {
                                                                    return "0";
                                                                } else if (income <= 51517) {
                                                                    return "0";
                                                                }
                                                            }
                                                        } else if (folder <= 3) {
                                                            if (income > 57399) {
                                                                return "1";
                                                            } else if (income <= 57399) {
                                                                if (age > 63) {
                                                                    return "0";
                                                                } else if (age <= 63) {
                                                                    return "0";
                                                                }
                                                            }
                                                        }
                                                    } else if (income <= 32517) {
                                                        return "1";
                                                    }
                                                }
                                            } else if (age <= 61) {
                                                if (income > 42585) {
                                                    if (age > 53) {
                                                        if (income > 59915) {
                                                            return "0";
                                                        } else if (income <= 59915) {
                                                            if (gender == null) {
                                                                return "0";
                                                            } else if (gender.equals("0")) {
                                                                if (age > 60) {
                                                                    return "1";
                                                                } else if (age <= 60) {
                                                                    if (age > 56) {
                                                                        if (income > 57077) {
                                                                            return "0";
                                                                        } else if (income <= 57077) {
                                                                            return "0";
                                                                        }
                                                                    } else if (age <= 56) {
                                                                        return "0";
                                                                    }
                                                                }
                                                            } else if (!gender.equals("0")) {
                                                                if (age > 55) {
                                                                    if (income > 51501) {
                                                                        return "0";
                                                                    } else if (income <= 51501) {
                                                                        return "0";
                                                                    }
                                                                } else if (age <= 55) {
                                                                    return "0";
                                                                }
                                                            }
                                                        }
                                                    } else if (age <= 53) {
                                                        if (folder > 3) {
                                                            if (income > 46911) {
                                                                if (age > 50) {
                                                                    return "0";
                                                                } else if (age <= 50) {
                                                                    return "0";
                                                                }
                                                            } else if (income <= 46911) {
                                                                return "1";
                                                            }
                                                        } else if (folder <= 3) {
                                                            if (income > 56517) {
                                                                return "0";
                                                            } else if (income <= 56517) {
                                                                if (income > 54651) {
                                                                    return "0";
                                                                } else if (income <= 54651) {
                                                                    if (income > 43741) {
                                                                        if (age > 49) {
                                                                            return "0";
                                                                        } else if (age <= 49) {
                                                                            return "0";
                                                                        }
                                                                    } else if (income <= 43741) {
                                                                        return "0";
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else if (income <= 42585) {
                                                    if (income > 35981) {
                                                        if (income > 37902) {
                                                            if (income > 40620) {
                                                                return "0";
                                                            } else if (income <= 40620) {
                                                                if (income > 40536) {
                                                                    return "1";
                                                                } else if (income <= 40536) {
                                                                    if (age > 60) {
                                                                        return "1";
                                                                    } else if (age <= 60) {
                                                                        return "0";
                                                                    }
                                                                }
                                                            }
                                                        } else if (income <= 37902) {
                                                            return "0";
                                                        }
                                                    } else if (income <= 35981) {
                                                        if (age > 49) {
                                                            if (income > 34241) {
                                                                return "0";
                                                            } else if (income <= 34241) {
                                                                return "0";
                                                            }
                                                        } else if (age <= 49) {
                                                            return "1";
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (age <= 47) {
                                    if (gender == null) {
                                        return "0";
                                    } else if (gender.equals("0")) {
                                        if (folder > 4) {
                                            return "0";
                                        } else if (folder <= 4) {
                                            if (income > 49820) {
                                                if (income > 58132) {
                                                    return "0";
                                                } else if (income <= 58132) {
                                                    return "1";
                                                }
                                            } else if (income <= 49820) {
                                                return "0";
                                            }
                                        }
                                    } else if (!gender.equals("0")) {
                                        if (folder > 4) {
                                            return "0";
                                        } else if (folder <= 4) {
                                            if (income > 54084) {
                                                return "0";
                                            } else if (income <= 54084) {
                                                if (income > 36829) {
                                                    return "0";
                                                } else if (income <= 36829) {
                                                    return "0";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (income <= 31949) {
                        if (income > 26044) {
                            if (income > 27768) {
                                if (income > 29369) {
                                    if (income > 29595) {
                                        if (folder > 2) {
                                            if (folder > 4) {
                                                return "0";
                                            } else if (folder <= 4) {
                                                return "0";
                                            }
                                        } else if (folder <= 2) {
                                            return "0";
                                        }
                                    } else if (income <= 29595) {
                                        return "0";
                                    }
                                } else if (income <= 29369) {
                                    if (age > 86) {
                                        return "1";
                                    } else if (age <= 86) {
                                        return "0";
                                    }
                                }
                            } else if (income <= 27768) {
                                return "0";
                            }
                        } else if (income <= 26044) {
                            if (age > 89) {
                                return "1";
                            } else if (age <= 89) {
                                if (age > 56) {
                                    if (age > 88) {
                                        return "0";
                                    } else if (age <= 88) {
                                        if (income > 25298) {
                                            return "0";
                                        } else if (income <= 25298) {
                                            if (gender == null) {
                                                return "0";
                                            } else if (gender.equals("0")) {
                                                if (folder > 3) {
                                                    return "0";
                                                } else if (folder <= 3) {
                                                    return "0";
                                                }
                                            } else if (!gender.equals("0")) {
                                                if (income > 18542) {
                                                    if (income > 18936) {
                                                        if (age > 84) {
                                                            return "0";
                                                        } else if (age <= 84) {
                                                            if (age > 81) {
                                                                return "1";
                                                            } else if (age <= 81) {
                                                                if (income > 21696) {
                                                                    return "0";
                                                                } else if (income <= 21696) {
                                                                    return "0";
                                                                }
                                                            }
                                                        }
                                                    } else if (income <= 18936) {
                                                        return "0";
                                                    }
                                                } else if (income <= 18542) {
                                                    return "1";
                                                }
                                            }
                                        }
                                    }
                                } else if (age <= 56) {
                                    if (age > 55) {
                                        return "1";
                                    } else if (age <= 55) {
                                        if (income > 23433) {
                                            return "0";
                                        } else if (income <= 23433) {
                                            if (age > 49) {
                                                return "0";
                                            } else if (age <= 49) {
                                                return "0";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (folder <= 1) {
                    if (gender == null) {
                        return "0";
                    } else if (gender.equals("0")) {
                        if (age > 83) {
                            return "0";
                        } else if (age <= 83) {
                            if (age > 79) {
                                return "0";
                            } else if (age <= 79) {
                                if (income > 26464) {
                                    if (income > 97941) {
                                        return "1";
                                    } else if (income <= 97941) {
                                        if (age > 55) {
                                            if (age > 67) {
                                                if (income > 72905) {
                                                    return "0";
                                                } else if (income <= 72905) {
                                                    return "0";
                                                }
                                            } else if (age <= 67) {
                                                if (age > 63) {
                                                    return "0";
                                                } else if (age <= 63) {
                                                    if (income > 67825) {
                                                        return "0";
                                                    } else if (income <= 67825) {
                                                        if (income > 66286) {
                                                            return "0";
                                                        } else if (income <= 66286) {
                                                            if (income > 63280) {
                                                                return "0";
                                                            } else if (income <= 63280) {
                                                                return "0";
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (age <= 55) {
                                            if (age > 46) {
                                                if (income > 74105) {
                                                    return "0";
                                                } else if (income <= 74105) {
                                                    if (income > 54797) {
                                                        return "0";
                                                    } else if (income <= 54797) {
                                                        if (income > 54088) {
                                                            return "1";
                                                        } else if (income <= 54088) {
                                                            return "0";
                                                        }
                                                    }
                                                }
                                            } else if (age <= 46) {
                                                return "0";
                                            }
                                        }
                                    }
                                } else if (income <= 26464) {
                                    return "0";
                                }
                            }
                        }
                    } else if (!gender.equals("0")) {
                        if (income > 57177) {
                            if (age > 46) {
                                if (income > 68741) {
                                    return "0";
                                } else if (income <= 68741) {
                                    if (income > 68450) {
                                        return "0";
                                    } else if (income <= 68450) {
                                        if (income > 66336) {
                                            return "1";
                                        } else if (income <= 66336) {
                                            return "0";
                                        }
                                    }
                                }
                            } else if (age <= 46) {
                                return "0";
                            }
                        } else if (income <= 57177) {
                            if (income > 20363) {
                                if (income > 56304) {
                                    return "0";
                                } else if (income <= 56304) {
                                    if (income > 27061) {
                                        if (income > 35939) {
                                            if (age > 86) {
                                                return "0";
                                            } else if (age <= 86) {
                                                if (age > 77) {
                                                    return "0";
                                                } else if (age <= 77) {
                                                    if (age > 55) {
                                                        if (income > 54447) {
                                                            return "0";
                                                        } else if (income <= 54447) {
                                                            if (age > 57) {
                                                                return "0";
                                                            } else if (age <= 57) {
                                                                return "0";
                                                            }
                                                        }
                                                    } else if (age <= 55) {
                                                        return "0";
                                                    }
                                                }
                                            }
                                        } else if (income <= 35939) {
                                            return "0";
                                        }
                                    } else if (income <= 27061) {
                                        return "0";
                                    }
                                }
                            } else if (income <= 20363) {
                                return "1";
                            }
                        }
                    }
                }
            }
        } else if (age <= 43) {
            if (income == null) {
                return "0";
            } else if (income > 47893) {
                if (age > 39) {
                    if (income > 56362) {
                        if (income > 65509) {
                            if (income > 128903) {
                                return "1";
                            } else if (income <= 128903) {
                                if (income > 125305) {
                                    return "0";
                                } else if (income <= 125305) {
                                    if (income > 123009) {
                                        return "1";
                                    } else if (income <= 123009) {
                                        if (income > 107658) {
                                            return "0";
                                        } else if (income <= 107658) {
                                            if (income > 96242) {
                                                return "0";
                                            } else if (income <= 96242) {
                                                if (income > 92925) {
                                                    return "0";
                                                } else if (income <= 92925) {
                                                    if (income > 91068) {
                                                        return "1";
                                                    } else if (income <= 91068) {
                                                        if (income > 90169) {
                                                            return "0";
                                                        } else if (income <= 90169) {
                                                            if (age > 42) {
                                                                return "0";
                                                            } else if (age <= 42) {
                                                                if (age > 41) {
                                                                    return "0";
                                                                } else if (age <= 41) {
                                                                    return "0";
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (income <= 65509) {
                            if (gender == null) {
                                return "0";
                            } else if (gender.equals("0")) {
                                return "0";
                            } else if (!gender.equals("0")) {
                                return "0";
                            }
                        }
                    } else if (income <= 56362) {
                        if (folder == null) {
                            return "0";
                        } else if (folder > 1) {
                            if (age > 40) {
                                return "0";
                            } else if (age <= 40) {
                                return "0";
                            }
                        } else if (folder <= 1) {
                            return "0";
                        }
                    }
                } else if (age <= 39) {
                    if (age > 34) {
                        if (folder == null) {
                            return "0";
                        } else if (folder > 3) {
                            if (folder > 4) {
                                return "0";
                            } else if (folder <= 4) {
                                if (income > 56599) {
                                    return "0";
                                } else if (income <= 56599) {
                                    return "1";
                                }
                            }
                        } else if (folder <= 3) {
                            if (income > 103903) {
                                return "1";
                            } else if (income <= 103903) {
                                if (income > 95407) {
                                    return "0";
                                } else if (income <= 95407) {
                                    if (income > 88519) {
                                        return "1";
                                    } else if (income <= 88519) {
                                        if (income > 48955) {
                                            if (age > 38) {
                                                return "0";
                                            } else if (age <= 38) {
                                                if (income > 69613) {
                                                    return "0";
                                                } else if (income <= 69613) {
                                                    if (folder > 2) {
                                                        return "0";
                                                    } else if (folder <= 2) {
                                                        if (income > 64423) {
                                                            return "0";
                                                        } else if (income <= 64423) {
                                                            return "0";
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (income <= 48955) {
                                            return "1";
                                        }
                                    }
                                }
                            }
                        }
                    } else if (age <= 34) {
                        if (folder == null) {
                            return "0";
                        } else if (folder > 1) {
                            if (income > 97854) {
                                return "0";
                            } else if (income <= 97854) {
                                if (folder > 4) {
                                    if (income > 51069) {
                                        if (income > 70917) {
                                            return "0";
                                        } else if (income <= 70917) {
                                            if (income > 60631) {
                                                return "0";
                                            } else if (income <= 60631) {
                                                return "0";
                                            }
                                        }
                                    } else if (income <= 51069) {
                                        return "0";
                                    }
                                } else if (folder <= 4) {
                                    if (income > 91098) {
                                        return "1";
                                    } else if (income <= 91098) {
                                        if (income > 68482) {
                                            if (income > 72606) {
                                                if (gender == null) {
                                                    return "0";
                                                } else if (gender.equals("0")) {
                                                    return "0";
                                                } else if (!gender.equals("0")) {
                                                    return "0";
                                                }
                                            } else if (income <= 72606) {
                                                return "0";
                                            }
                                        } else if (income <= 68482) {
                                            if (gender == null) {
                                                return "0";
                                            } else if (gender.equals("0")) {
                                                if (income > 67798) {
                                                    return "0";
                                                } else if (income <= 67798) {
                                                    if (income > 48469) {
                                                        if (income > 49530) {
                                                            if (age > 19) {
                                                                if (age > 20) {
                                                                    if (income > 50710) {
                                                                        if (folder > 3) {
                                                                            return "0";
                                                                        } else if (folder <= 3) {
                                                                            if (folder > 2) {
                                                                                return "0";
                                                                            } else if (folder <= 2) {
                                                                                return "0";
                                                                            }
                                                                        }
                                                                    } else if (income <= 50710) {
                                                                        return "0";
                                                                    }
                                                                } else if (age <= 20) {
                                                                    return "0";
                                                                }
                                                            } else if (age <= 19) {
                                                                return "1";
                                                            }
                                                        } else if (income <= 49530) {
                                                            return "0";
                                                        }
                                                    } else if (income <= 48469) {
                                                        return "1";
                                                    }
                                                }
                                            } else if (!gender.equals("0")) {
                                                if (income > 67878) {
                                                    return "1";
                                                } else if (income <= 67878) {
                                                    if (age > 26) {
                                                        if (age > 31) {
                                                            return "0";
                                                        } else if (age <= 31) {
                                                            return "0";
                                                        }
                                                    } else if (age <= 26) {
                                                        return "0";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (folder <= 1) {
                            if (age > 33) {
                                return "0";
                            } else if (age <= 33) {
                                if (income > 108728) {
                                    return "0";
                                } else if (income <= 108728) {
                                    if (income > 66993) {
                                        return "0";
                                    } else if (income <= 66993) {
                                        if (age > 18) {
                                            if (income > 51743) {
                                                return "0";
                                            } else if (income <= 51743) {
                                                return "0";
                                            }
                                        } else if (age <= 18) {
                                            return "0";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (income <= 47893) {
                if (age > 20) {
                    if (income > 46937) {
                        return "0";
                    } else if (income <= 46937) {
                        if (income > 18647) {
                            if (income > 44891) {
                                if (folder == null) {
                                    return "0";
                                } else if (folder > 1) {
                                    if (gender == null) {
                                        return "0";
                                    } else if (gender.equals("0")) {
                                        return "0";
                                    } else if (!gender.equals("0")) {
                                        return "0";
                                    }
                                } else if (folder <= 1) {
                                    return "0";
                                }
                            } else if (income <= 44891) {
                                if (income > 26503) {
                                    if (age > 39) {
                                        if (folder == null) {
                                            return "0";
                                        } else if (folder > 1) {
                                            if (income > 37947) {
                                                return "0";
                                            } else if (income <= 37947) {
                                                return "0";
                                            }
                                        } else if (folder <= 1) {
                                            return "0";
                                        }
                                    } else if (age <= 39) {
                                        if (age > 38) {
                                            return "0";
                                        } else if (age <= 38) {
                                            if (income > 30863) {
                                                if (age > 21) {
                                                    if (gender == null) {
                                                        return "0";
                                                    } else if (gender.equals("0")) {
                                                        if (income > 32362) {
                                                            if (age > 30) {
                                                                if (folder == null) {
                                                                    return "0";
                                                                } else if (folder > 2) {
                                                                    return "0";
                                                                } else if (folder <= 2) {
                                                                    return "0";
                                                                }
                                                            } else if (age <= 30) {
                                                                if (folder == null) {
                                                                    return "0";
                                                                } else if (folder > 2) {
                                                                    return "0";
                                                                } else if (folder <= 2) {
                                                                    return "0";
                                                                }
                                                            }
                                                        } else if (income <= 32362) {
                                                            return "0";
                                                        }
                                                    } else if (!gender.equals("0")) {
                                                        if (age > 26) {
                                                            if (income > 39800) {
                                                                return "0";
                                                            } else if (income <= 39800) {
                                                                if (age > 30) {
                                                                    return "0";
                                                                } else if (age <= 30) {
                                                                    return "0";
                                                                }
                                                            }
                                                        } else if (age <= 26) {
                                                            return "0";
                                                        }
                                                    }
                                                } else if (age <= 21) {
                                                    return "0";
                                                }
                                            } else if (income <= 30863) {
                                                if (income > 29842) {
                                                    return "1";
                                                } else if (income <= 29842) {
                                                    if (income > 26754) {
                                                        return "0";
                                                    } else if (income <= 26754) {
                                                        return "1";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (income <= 26503) {
                                    if (age > 37) {
                                        if (folder == null) {
                                            return "0";
                                        } else if (folder > 1) {
                                            if (income > 24056) {
                                                return "0";
                                            } else if (income <= 24056) {
                                                return "0";
                                            }
                                        } else if (folder <= 1) {
                                            return "0";
                                        }
                                    } else if (age <= 37) {
                                        if (income > 18934) {
                                            if (income > 19194) {
                                                if (folder == null) {
                                                    return "0";
                                                } else if (folder > 4) {
                                                    return "0";
                                                } else if (folder <= 4) {
                                                    if (folder > 1) {
                                                        if (income > 21906) {
                                                            if (age > 22) {
                                                                return "0";
                                                            } else if (age <= 22) {
                                                                return "1";
                                                            }
                                                        } else if (income <= 21906) {
                                                            return "0";
                                                        }
                                                    } else if (folder <= 1) {
                                                        return "0";
                                                    }
                                                }
                                            } else if (income <= 19194) {
                                                return "0";
                                            }
                                        } else if (income <= 18934) {
                                            return "1";
                                        }
                                    }
                                }
                            }
                        } else if (income <= 18647) {
                            return "0";
                        }
                    }
                } else if (age <= 20) {
                    return "0";
                }
            }
        }
        return null;
    }

}

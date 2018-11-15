## Project description 
+ Relevant field : date, code , amount
+ Build model : K-means clustering 
+ Build model using Apache Spark
+ K-Means cluster : 

## Project description 
+ Build a fraud detection system 
+ Fraud detection system for banking system using Hadoop , Spark and Machine learning. The fraud detection system will predict whether a payment transaction is a suspect transaction. 
    + If suspicious transaction is detected, then the payment processing system can step up security and ask for more information from the account holder before the transaction can be processed


## Tech stack 
+ Hadoop 
+ Spark 
+ Machine learning 




## Steps 
+ Dataset 
+ Design the model from dataset - clean the dataset 
+ Create fraud detection model 
+ Put model into use 

## Practices : 
+ Load dataset 
+ Clean dataset 
+ Field related: Date, Code, Amount 

+ Find relevant fields : 
+ Machine learning for fraud detection : predict outcome future transactions
+ Unsupervised machine learning method : detect anomalies in historical transaction data. 
+ Selected the unsupervised machine learning method - historical transaction dataset - pattern 

+ Clustering = unsupervised machine learning method : pattern in large datasets - several algorithms available to cluster data - selection = K - Means algorithm

+ Design high level architecture : 
    + Model - historical transaction data - Model is created 




+ Using Apache Spark : 
    + big data processing framework 
    + perform large-scale data processing on a cluster of computers - Map Reduce - Jobs- on Hadoop - batch process that process data in multiple stages - disk IO - writen data on the disk 
    + Spark = datasets - text datasets - graph datasets - batch and real time streaming data - classification - regression - recommendation - clustering 
+ Sparck architecture :Spark cluster 
    + Driver - Workder node-executor - Worker node executor - cluster manager - Worker node perform computations and run on the node of the Spark cluster 
    + Resilient distributed datasets
        + in mem data - - RDDs - operation can be done in parallel - all type of data -immutable collections of data objects
    + Action : functions on RDD - count the number of rows - RDD can be read from a file or persist on a file from the memory 
    + Transformation : map - filter - flatMap - groupByKey - reduceBykey - aggregateByKey -repartition - pipe - coalesce 


    


+ Calculating the yearly average stock prices using Apache Spark 
    + Dataset: daily stock price for IBM - calculate the yearly average using Spark - ibm-stock.csv 


+ Coding - scripting part 
    + Load dataset 
    + Spark shell : define csv file 



    



>>>csvFile = "ibm-stock.csv" 

First, we start the Spark interactive shell using the script pyspark and specify the name of the file containing the data:

>>>stockData = sc.textFile(csvFile) 

// spark context 
// Spark context : Spark function - object - connection reference to a Spark cluster - create initial RDD from the file - transformation 


SparkContext - create RDD - 



>>> pair = stockData.map(lambda x:(x.split("-")[0], x.split(",")[1])) 



RDD - transform - RDD map - 


Now we use our RDD stockdata and transform it into another RDD using the map function. In the transformation process, we will extract the year and the first value after the comma as the stock price, which we will use to create the averages. The new RDD is called pair:

>>>sumCount = pair.combineByKey(lambda value: (value, 1), lambda x, value: (float(x[0]) + float(value), x[1] + 1),lambda x, y: (x[0] + y[0], x[1] + y[1])) 

// CombineByKey function 
Using the preceding statement, we have created another RDD using the combineByKey function. This function uses the year as the key and then creates tuples that contain the sum of stock prices and their count for the particular key. This transformation creates a new RDD sumCount:

>>>averageByKey = sumCount.map(lambda (label, (value_sum, count)): (label, value_sum / count)) 

// Calculate the average stock prices - map - average by Key - 

Using the preceding statement, we calculate the average stock prices with the help of the map function, which creates another RDD known as averageByKey. Now that we have applied all the transformations to our data, we can print the final RDD to see the results using the following statement:

>>>averageByKey.sortByKey().collect(); 
// get the same result with Spark - Hive - Map reduce- 
// fs - - read file from HDFS - 







+ MLlib : 
    + scalable machine learning lib 
    + Algo : classfication, regression , clustering, collaborative filtering, dimensionality reductions - local vectors- matrices 

+ Testing algorithm using MLlib 
    + Clustering: group data points wiht similarity 
    + KMeans P clustering data point
        + input = NxM array and number of clusters - find the centroids of these clusters  




    




 The input of the K-means algorithm should be an NxM dimensional array and the number of clusters we want to create. The K-means algorithm finds the centroids of these clusters. The nearest cluster centroid for a data point indicates which cluster that data point belongs to. Once we have found the cluster centroids, then for each new data point we can determine in which cluster it falls.

The process of clustering is hard to visualize for an NxM dimensional array, however it can be easily understood with a two dimensional dataset. For a two-dimensional array containing x,y coordinates, clustering using K-means is shown in Figure 5:



MLlib comes with a parallel version of k-means++, known as k-means parallel. K-means is an iterative algorithm, therefore it is well suited for an in-memory framework such as Spark because the data from intermediate stages of iterations does not have to be written to the disk.

According to the Spark documentation available at http://spark.apache.org/docs/latest/mllib-clustering.html, the K-means implementation in MLlib accepts the following parameters:

k is the number of desired clusters.
maxIterations is the maximum number of iterations to run.
initializationMode specifies either random initialization or initialization via k-means parallel.
runs is the number of times to run the k-means algorithm (k-means is not guaranteed to find a globally optimal solution, and when run multiple times on a given dataset, the algorithm returns the best clustering result).
initializationSteps determines the number of steps in the k-means|| algorithm.
epsilon determines the distance threshold within which we consider k-means to have converged.
initialModel is an optional set of cluster centers used for initialization. If this parameter is supplied, only one run is performed.
We will do clustering using K-means on a simple dataset contained in the file stocks.csv to test drive Spark. The structure of this dataset is as follows:

Microsoft, 91259, 60420, 54.19 
IBM, 400000, 98787, 138.54 
Skype, 700, 716, 28 
SAP, 48000, 11567, 74.85 
Yahoo!, 14000 , 6426 , 33.11 
eBay, 15000, 8700, 29.06 

In this CSV file, the first field is the name of company followed by the number of employees, revenue in million USD, and stock price in USD at some point in time. We will process this file using Spark and create clusters using K-means.

We start the Spark shell using the pyspark command. After we use the command line prompt >>> then run the following statements one by one:

>>> from pyspark.mllib.clustering import KMeans, KMeansModel 
>>> from numpy import array 

The preceding statement will import the required libraries for K-means.

>>> stocks = sc.textFile("stocks.csv") 

We create a new RDD named stocks from the stocks.csv file using the preceding statement.

>>> data = stocks.map(lambda line: line.split(",", 1)) 

We transform stocks into a new RDD to split the company name because it cannot be used in the clustering.

>>>parsedData = data.map(lambda line: array([float(x) for x in line[1].split(',')])) 

We separate the number of employees, revenue, and stock price in an RDD array. Also, we convert the data from a Unicode string to float so that the K-Means algorithm can process it. Conversion from a string is essential because the K-Means algorithm can only process numbers.

>>> c = 2 

We specify that we want to create two clusters and we will use this as a parameter in the train function in the next step.

>>> clusters = KMeans.train(parsedData, c) 

We train the model using parsedData and specify the number of clusters we want to build.

>>>clusters.clusterCenters 
[array([ 33791.8 , 17565.8 ,   43.842]), array([ 4.00000000e+05,  9.87870000e+04,  1.38540000e+02])] 

The preceding statement shows that K-means has created two clusters and their centroids are shown in the two arrays returned.

You have now got an idea of how MLib works. Let us apply these principles in the next sections to build our fraud detection model.




+ Create fraud detection model 

+ Put fraud detection model into use 


    + Extended version of the fraud detection system - 

    + Generate data stream 
    + Process data steam using Spark stream 
    + Scale the performance of the model 
        + Real time processing - config Spark 
        + Deployment : node - spark jobs on YARN 











# Run application locally on 8 cores 
$ spark-submit --master local[8]  /Users/anurag/hdproject/eclipse/chapt3/transmodel.py 
 
# Run on a Spark standalone cluster in client deploy mode 
$ spark-submit --master spark://spar:7077  /Users/anurag/hdproject/eclipse/chapt3/transmodel.py 
 
# Run on a YARN cluster in YARN client mode 
export YARN_CONF_DIR=/Users/anurag/Java/hadoop-2.7.1/etc/hadoop 
$ spark-submit --master yarn-client  /Users/anurag/hdproject/eclipse/chapt3/transmodel.py 

 



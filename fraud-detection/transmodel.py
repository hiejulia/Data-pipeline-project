# Submit to spark using
# $root_path = "users/..."
# spark-submit $root_path/transmodel.py
# You need the full path of the python script

from pyspark.mllib.clustering import KMeans, KMeansModel
from pyspark import SparkContext
from pyspark import SparkConf

# Import libs : pyspark.mllib - clustering - import KMeans, KMeansModel - 
# Import pyspark - import SparkContext - import pyspark import SparkConf 


# transmodel.py 

# Function : payment code - 
def paymentCode(code):
    if code == "BA":
        return 1
    elif code == "IC":
        return 2
    elif code == "GM":
        return 3
    elif code == "DV":
        return 4
    elif code == "OV":
        return 5
    elif code == "GT":
        return 6

# Create a local StreamingContext with two working thread and batch interval of 1 second
conf = SparkConf().setAppName("model-kmeans-fraud-detection-system")
# conf = conf.setMaster("local[2]")


# Create a local StreamingContext with 2 working threads and batch interval of 1 seconds 

# 2 working threads - batch interval of 1s conf  = SparkConf.setapp

sc = SparkContext(conf=conf)
f1 = sc.textFile("hdfs://localhost:9000/fraud-dection-system/INGB01.csv")
f2 = f1.map(lambda line : [ x for x in line.split(",")])
f3 = f2.map(lambda line : [ line[0], paymentCode(line[1]),line[3]])


# sc = SparkContext conf = conf 
# f1= sc.textFile - f2 = f1.map lambda line : x for x in line.split , 
# f3 = f2 . map lambda line : line 0 - payma


# sc.textFile - hdfs://localhost

#Create two clusters
clusters = KMeans.train(f3, 2)
print clusters.clusterCenters
clusters.save(sc, "kmeansmodel01")

# Create 2 clusters 
# Trong rat la cool - no beo - nhung ma cai mat no dep trai - 







# No y chang nhu con trai - lol - lep thay me - deo co mong luon tran cao hon - the thoi - i dont really give a shit - no co tinh day - lol - 


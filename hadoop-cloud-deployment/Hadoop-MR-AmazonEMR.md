## How to run 

+ test-mr.jar 

+ S3 : create bucket : input-data : upload .txt data 
     + upload jar file 
     + store output data - 
     + S3 bucket - store logs 

+ Amazon ERM 
    + create cluster - hadoop distribution - Software config - AMI  
    + select EC2 instance 
    + Monitor MR cluster deployment 
    + view logs - debug computation 

<a href="https://imgur.com/BFMjwvy"><img src="https://i.imgur.com/BFMjwvy.png" title="source: imgur.com" /></a>
<a href="https://imgur.com/yuBTlVS"><img src="https://i.imgur.com/yuBTlVS.png" title="source: imgur.com" /></a>




+ Run Pig script 
    + Amazon EMR - use Apache Pig in interactive mode 
    + SSH to master node of the cluster `ssh -i <path-to-the-key-file> hadoop@<master-public-DNS>`
    + start pig interactive shell in the master node : `pig`


+ Execute Hive script 
    + 


# Ref :
+ Book 
    + Hadoop MapReduce v2 Cookbook 
+ Research paper 
    + Google 
    + Amazon 
    + Facebook 
+ Github project 

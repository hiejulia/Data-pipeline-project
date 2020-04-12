# Real time streaming pipeline 

+ Kafka - Storm 
+ Algorithms: 
    + Top k element in Data stream 
    + Count-Min-Sketch algorithm 
    


+ Tech stack 
    + Rocks DB 
        + 2 DB : transientStateDB, failedStateDB 
    + Java 


<a href="https://imgur.com/1nIb5rs"><img src="https://i.imgur.com/1nIb5rs.png" title="source: imgur.com" /></a>



# Theory part
+ Checkpoint protocol 
    + global snapshot 
        + Check "A survey of Rollback-Recovery Protocols in Messaging-Passing System"
    + potential for data loss 
+ Log 
    + receiver based message logging 
    + sender based message logging 
    + hybrid message logging 


+ Migrating data loss 

+ Algorithms for data analysis 
    + 

+ Store 
    + store for batch/ offline access 
    + chain multiple streaming process workflows: Apache Samza 

    + caching system 
    

+ Communication patterns 
    + Data sync 
    + RMI/ RPC 
    + Pub/ sub 
    + Simple messaging 
    
+ Filter stream 
    + Static 
    + Dynamic 



+ Protocol used to send streaming data to client 
    + webhook 
    + http long polling 
    + SSE 
    + web socket 




+ Consumer devices to access the data 
<a href="https://imgur.com/3wxosgB"><img src="https://i.imgur.com/3wxosgB.png" title="source: imgur.com" /></a>
    + web socket long polling 
    + Streaming API proxy provide SQL query : Apache Calcite 



# Ref 
+ https://github.com/HolmesNL/kafka-spout 
+ Book : Streaming data: Understanding the real time pipeline 
+ https://dev.to/usamaashraf/playing-with-apache-storm-on-docker---like-a-boss-4bgb 



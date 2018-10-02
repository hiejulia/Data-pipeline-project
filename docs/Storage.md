# Storage 

+ key-value pair 
+ json - avro format 
+ Hbase - cassandra 
+ Kafka 
    + scalable : distribute data load 
    + durable : 
    + reliable : data replication 
    + high performance : partitioning, message storage, scalability
    + architecture components 
        + producer : topic partition - round robin fashion 
        + topic 
        + partition : topic parallelism - kafka cluster 
        + consumer : 
        + broker : kafka cluster - period of time 
        + kafka connect 
        + kafka streams 
    + Kafka connect workers : standalone vs distributed mode 
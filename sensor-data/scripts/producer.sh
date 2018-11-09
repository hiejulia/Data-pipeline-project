#!/bin/bash


# Start producer 
# cd inside kafka/bin 
# Write to web logs topic 
kafka-console-producer.sh --broker-list localhost:9092 --topic sensor
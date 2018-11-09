#!/usr/bin/env bash


# Start zookeeper service at the background
brew services start zookeeper


# start kafka server
brew services start kafka


# Start schema registry 


# Start Confluent
./bin/confluent start schema-registry

echo "Finish start service "

brew services list 


bin/kafka-topics --list --zookeeper localhost:2181


bin/kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic my-first-topic1

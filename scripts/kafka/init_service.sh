#!/usr/bin/env bash


# Start zookeeper service at the background
brew services start zookeeper


# start kafka server
brew services start kafka

echo "Finish start service "

brew services list 
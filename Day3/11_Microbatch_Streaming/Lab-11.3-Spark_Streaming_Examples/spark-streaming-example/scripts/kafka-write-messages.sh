#!/usr/bin/env bash

if [ -z "$KAFKA_HOME" ]; then
    echo "Please set KAFKA_HOME environment variable."
    exit 1
fi  

# this command expects input to be entered from the command line. Use ctrl-d to exit.
$KAFKA_HOME/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic messages 

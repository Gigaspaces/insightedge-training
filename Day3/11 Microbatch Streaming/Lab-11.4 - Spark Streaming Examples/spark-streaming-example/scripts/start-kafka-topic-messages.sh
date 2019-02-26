#!/usr/bin/env bash

if [ -z "$KAFKA_HOME" ]; then
    echo "Please set KAFKA_HOME environment variable."
    exit 1
fi  

if [ ! -d "$KAFKA_HOME/logs" ]; then
  mkdir $KAFKA_HOME/logs
fi

## This section intentionally commented out.
## insightedge will start Zookeeper.
## export ZLOGS=$KAFKA_HOME/logs/zookeeper.log
## echo "Starting ZooKeeper, logs: $ZLOGS"
## nohup $KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties > $ZLOGS 2>&1 &

## echo "Zookeeper started with PID: $!";

## echo "Waiting 10 seconds..."
## sleep 10

export KLOGS=$KAFKA_HOME/logs/kafka.log
echo "Starting Kafka, logs: $KLOGS"
nohup $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties > $KLOGS 2>&1 &

echo "Kafka started with PID: $!";

echo "Waiting 5 seconds..."
sleep 5

echo "Creating (1) Kafka topics"
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper 127.0.0.1:2181 --replication-factor 1 --partitions 1 --topic messages 

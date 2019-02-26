#!/usr/bin/env bash

$KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic messages --from-beginning

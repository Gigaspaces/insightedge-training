#!/usr/bin/env bash

$KAFKA_HOME/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my_group_id 

#$KAFKA_HOME/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list

# Lab: Spark Streaming Example with Kafka

In this lab you will learn run an example that consumes data from Kafka.

# Prerequisites

1. Install Kafka.
1. Set the KAFKA_HOME environment variable.

# Instructions

1. Start InsightEdge in Demo mode:

    go to gigaspaces-insightedge-enterprise-14.0.0-ga-b20000/bin

    run ./insightedge demo

1. Start Kafka. A script has been provided for your convenience under scripts/start-kafka-topic-messages.sh

1. Run `mvn package` (look for pom.xml in this directory)

1. Run insightedge-submit. An example command is provided below:

```bash
insightedge/bin/insightedge-submit --class com.gigaspaces.ietraining.SparkStreamingExample \
  --master spark://127.0.0.1:7077 \
  /path to target dir/insightedge-examples-1.0-SNAPSHOT-jar-with-dependencies.jar
```
5. Write some messages to the topic. A script can be found at scripts/kafka-write-messages.sh. The console that is running the insightedge-submit should print the messages that were entered.

# Troubleshooting
1. If you don't see any output, you can check if the messages are being consumed with scripts/kafka-show-groups.sh
    

# Lab: Spark Streaming Example with Kafka

In this lab you will learn run an example that consumes data from Kafka.

# Prerequisites

**1.** Install Kafka.<br>
**2.** Set the KAFKA_HOME environment variable.

# Instructions

**1.** Start InsightEdge in Demo mode:

    go to gigaspaces-insightedge-enterprise-14.2.0-ga-b20400/bin

    run ./insightedge demo
    
**2.** Run Kafka. Go to $KAFKA_HOME and run:<br>
   `./bin/kafka-server-start.sh ../config/server.properties`

**3.** Create topic in Kafka. A script has been provided for your convenience under 
    `scripts/start-kafka-topic-messages.sh`

**4.** Run `mvn package` (look for pom.xml in this directory)

**5.** Run insightedge-submit. An example command is provided below:

```bash
insightedge/bin/insightedge-submit --class com.gigaspaces.ietraining.SparkStreamingExample \
  --master spark://127.0.0.1:7077 \
  /path to target dir/insightedge-examples-1.0-SNAPSHOT-jar-with-dependencies.jar
```
**Alternatively**, run the "Lab 11.4 Example - Kafka Streaming" notebook found in this directory.

**6.** Write some messages to the topic. A script can be found at `scripts/kafka-write-messages.sh.` The console that is running the insightedge-submit should print the messages that were entered.

# Troubleshooting
**1.** To check if there any messages in the topic, you can run scripts/kafka-output-topic.sh<br>
**2.** If you don't see any output, you can check if the messages are being consumed with scripts/kafka-show-groups.sh
    

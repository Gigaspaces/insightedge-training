package com.gigaspaces.ietraining

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.all._

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

/*
  See: https://spark.apache.org/docs/2.3.2/streaming-kafka-0-10-integration.html
 */
object SparkStreamingExample {

  def main(args: Array[String]): Unit = {
    val settings = if (args.length > 0)
      args
    else
      Array(new SparkConf().get("spark.master", InsightEdgeConfig.SPARK_MASTER_LOCAL_URL_DEFAULT),
        sys.env.getOrElse(InsightEdgeConfig.INSIGHTEDGE_SPACE_NAME, InsightEdgeConfig.INSIGHTEDGE_SPACE_NAME_DEFAULT))

    if (settings.length != 2) {
      System.err.println("Usage: SparkStreamingExample <spark master url> <space name>")
      System.exit(1)
    }

    val Array(master, space) = settings
    println(s"master is: $master")
    println(s"space is: $space")
    val config = InsightEdgeConfig(space)
    val spark = SparkSession.builder
      .appName("spark-streaming-example")
      .master(master)
      .getOrCreate()

    //initializing the insightedge context via the spark context
    spark.sparkContext.initializeInsightEdgeContext(config)

    val sc = spark.sparkContext

    val ssc = new StreamingContext(sc, Seconds(30))

    ssc.checkpoint("/tmp/checkpoint")

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "my_group_id",
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("messages")

    val messagesStream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    messagesStream.map(record=>(record.value().toString)).print


/*
    messagesStream.foreachRDD { rdd =>
      println("--- New RDD with " + rdd.partitions.size + " partitions and " + rdd.count + " records")

      rdd.foreach { record =>
        print(record.value().toString())
      }
    }
 */

    ssc.start()

    ssc.awaitTermination()
  }
}

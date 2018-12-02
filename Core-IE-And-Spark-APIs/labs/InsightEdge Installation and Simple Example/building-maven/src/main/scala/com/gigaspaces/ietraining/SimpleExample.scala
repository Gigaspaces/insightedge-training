package com.gigaspaces.ietraining

import org.apache.spark.sql.SparkSession
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.all._

case class Person(var id: String, var name: String, var age: Int)

object SimpleExample {

  def main(args: Array[String]) {

    val config = InsightEdgeConfig("insightedge-space")

    val spark = SparkSession.builder
      .appName("DataframeGridExample")
      .master("spark://127.0.0.1:7077")
      .insightEdgeConfig(config)
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/video-games-sales.csv")

    df.show(5)

    spark.stop()
  }
}


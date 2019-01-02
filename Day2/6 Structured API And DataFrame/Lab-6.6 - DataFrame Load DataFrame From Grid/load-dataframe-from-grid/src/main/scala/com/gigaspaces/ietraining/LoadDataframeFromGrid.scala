package com.gigaspaces.ietraining

import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.insightedge.scala.annotation._
import org.insightedge.spark.context.InsightEdgeConfig
import org.insightedge.spark.implicits.all._

import scala.beans.BeanProperty

case class Person(
                   @BeanProperty @SpaceId(autoGenerate = true) var id: String,
                   @BeanProperty var name: String,
                   @BeanProperty var age: Int ) {

  def this() = this(null, null, -1)
}


object LoadDataframeFromGrid {

  def main(args: Array[String]) {

    val initConfig = InsightEdgeConfig.fromSparkConf(new SparkConf())

    //args: <spark master url> <space name>
    val settings = if (args.length > 0) args
    else Array( new SparkConf().get("spark.master", InsightEdgeConfig.SPARK_MASTER_LOCAL_URL_DEFAULT),
      initConfig.spaceName)

    if (settings.length != 2) {
      System.err.println("Usage: LoadDataFrame <spark master url> <space name>")
      System.exit(1)
    }
    val ieConfig = InsightEdgeConfig("demo", Some("xap-14.0.0"), Some("127.0.0.1"))
    val Array(master, space) = settings
    val spark = SparkSession.builder
      .appName("example-load-dataframe")
      .master(master)
      .insightEdgeConfig(ieConfig)
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import org.insightedge.spark.implicits.all._
    import spark.implicits._

    val persons = Seq(
      Person("101", "john", 25),
      Person("102", "sam", 33)
    )

    val df = persons.toDF()

    // Persist DataFrame into a collection named "people"
    df.write.mode(SaveMode.Overwrite).grid("people")

    // Load the DataFrame from the collection by name
    val persisted = spark.read.grid("people")

    // Displays the content of the DataFrame to stdout
    persisted.show()

    spark.stop()
  }
}


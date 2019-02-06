package com.gigaspaces.ietraining

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


object SaveLoadDataframeFromGrid {

  def main(args: Array[String]) {

    val ieConfig = InsightEdgeConfig("demo", Some("xap-14.0.0"), Some("127.0.0.1"))
    val spark = SparkSession.builder
      .master("local")
      .appName("example-load-dataframe")
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


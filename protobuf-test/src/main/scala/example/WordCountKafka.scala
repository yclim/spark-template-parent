package example

import org.apache.spark.sql.SparkSession

object WordCountKafka {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.appName("WordCountKafka").getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    import spark.implicits._

    val lines = spark.readStream.format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "wordcount_input")
      .option("startingOffsets", "earliest")
      .load()

    lines.printSchema()

    val word = lines.select('value).as[String].flatMap(_.split(" "))

    word.printSchema()

    val wordCounts = word.groupBy("value").count()
    val query = wordCounts.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    query.awaitTermination()

  }

}

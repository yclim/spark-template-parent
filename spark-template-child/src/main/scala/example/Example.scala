package example

import org.apache.spark.sql.SparkSession

object Example {
  def main(args: Array[String]) {
    if (args.length < 1) {
      // scalastyle:off println
      System.err.println("Usage: example.Example <hdfsPath>")
      // scalastyle:on println
      System.exit(1)
    }
    val hdfsPath = args(0)
    val spark = SparkSession.builder.getOrCreate()
    val sc = spark.sparkContext;
    spark.sparkContext.setLogLevel("WARN")
    val pairs = sc.textFile(hdfsPath)
                  .flatMap(_.split(' '))
                  .map((_, 1))
                  .reduceByKey(_ + _)

    // scalastyle:off println
    for (pair <- pairs.collect()) println( pair )
    // scalastyle:on println
    spark.stop()
  }
}
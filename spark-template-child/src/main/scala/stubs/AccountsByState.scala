package stubs

import org.apache.spark.sql.SparkSession

object AccountsByState {
  def main(args: Array[String]) {
    if (args.length < 1) {
      // scalastyle:off println
      System.err.println("Usage: stubs.AccountByState <state-code>")
      // scalastyle:on println
      System.exit(1)
    }
    val stateCode = args(0)
  }
}


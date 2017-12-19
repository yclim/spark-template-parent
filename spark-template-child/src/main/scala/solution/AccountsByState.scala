package solution

import org.apache.spark.sql.SparkSession

object AccountsByState {
  def main(args: Array[String]) {
    if (args.length < 1) {
      // scalastyle:off println
      System.err.println("Usage: solution.AccountsByState <state-code>")
      // scalastyle:on println
      System.exit(1)
    }
    val stateCode = args(0)
    val spark = SparkSession.builder.getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    val accountsDF = spark.read.table("accounts")
    val stateAccountsDF = accountsDF.where(accountsDF("state") === stateCode)
    stateAccountsDF.write.mode("overwrite").save("/loudacre/accounts_by_state/" + stateCode)
    spark.stop()
  }
}


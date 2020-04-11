package ML

import org.apache.spark.sql.SparkSession

object SparkSessionCreator {

  def sparkSessionCreate(): SparkSession = {
    SparkSession
      .builder()
      .master("local")
      .appName("hedge-fund-real-time-analysis")
      .getOrCreate()
  }

}


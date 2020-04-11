package machineLearning

import org.apache.spark.sql.SparkSession

object SparkSessionCreator {

  def sparkSessionCreate(): SparkSession = {
    SparkSession
      .builder()
      .master("local")
      .appName("hedge-fund-real-time-analysis")
      .config("spark.driver.bindAddress", "127.0.0.1")
      .getOrCreate()
  }

}

package machineLearning

import org.apache.spark.sql.DataFrame
import com.mongodb.spark.config.ReadConfig
import com.mongodb.spark.sql._
import org.apache.spark.sql.SparkSession

object DataSourcer {
  /**
   * function to fetch train data from MongoDB and return DataFrame
   * @param sparkSession entry to interact with underlying Spark functionality and allows programming Spark with DataFrame and Dataset APIs
   */
  def rawTrainData(sparkSession: SparkSession, Symbol: String): DataFrame = {

    val readConfig: ReadConfig = ReadConfig(Map("uri" -> "mongodb://127.0.0.1/", "database" -> "scaladb", "collection" -> Symbol)) // 1)
    sparkSession.read.mongo(readConfig)


  }
  /**
   * function to fetch latest data from MongoDB and return DataFrame
   * @param sparkSession entry to interact with underlying Spark functionality and allows programming Spark with DataFrame and Dataset APIs
   */
  def rawTestData(sparkSession: SparkSession, Symbol: String): DataFrame = {
    val readConfig: ReadConfig = ReadConfig(Map("uri" -> "mongodb://127.0.0.1/", "database" -> "scaladb", "collection" -> Symbol)) // 1)
    val df = sparkSession.read.mongo(readConfig)
    df.createOrReplaceTempView("data")
    sparkSession.sql("Select timestamp,open,high,low,close,volume FROM data order by Timestamp desc limit 1")

  }


}

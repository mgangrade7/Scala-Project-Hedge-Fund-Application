package machineLearning

import com.mongodb.spark.config.ReadConfig
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.lit
import com.mongodb.spark._
import com.mongodb.spark.config.ReadConfig
import com.mongodb.spark.sql._
import org.apache.spark.sql.SparkSession
import com.mongodb.spark.MongoSpark

import org.apache.spark.sql.functions.{max, min}
import org.bson.Document

object DataSourcer {

  def rawTrainData(sparkSession: SparkSession, Symbol: String): DataFrame = {

    val readConfig: ReadConfig = ReadConfig(Map("uri" -> "mongodb://127.0.0.1/", "database" -> "scaladb77", "collection" -> Symbol)) // 1)
    sparkSession.read.mongo(readConfig)


  }

  def rawTestData(sparkSession: SparkSession, Symbol: String): DataFrame = {
    val readConfig: ReadConfig = ReadConfig(Map("uri" -> "mongodb://127.0.0.1/", "database" -> "scaladb", "collection" -> Symbol)) // 1)
    val df = sparkSession.read.mongo(readConfig)
    df.createOrReplaceTempView("data")
    sparkSession.sql("Select timestamp,open,high,low,close,volume FROM data order by Timestamp desc limit 1")

  }


}

import com.mongodb.spark.config.ReadConfig
import com.mongodb.spark.sql._
import org.apache.spark.sql.{DataFrame, SparkSession}

object DataSourcer {

  def rawTrainData(sparkSession: SparkSession, Symbol: String): DataFrame = {
    //Connect to MongoDB to load historic data
    val readConfig: ReadConfig = ReadConfig(Map("uri" -> "mongodb://127.0.0.1/", "database" -> "scaladb77", "collection" -> Symbol)) // 1)
    sparkSession.read.mongo(readConfig)


    //    //Testing: Load data from local for training
    //    sparkSession.read.option("header", "true").csv("./src/main/resources/data.csv")

  }


  def rawTestData(sparkSession: SparkSession, Symbol: String): DataFrame = {
    //Connect to MongoDB to load recent data
    val readConfig: ReadConfig = ReadConfig(Map("uri" -> "mongodb://127.0.0.1/", "database" -> "scaladb", "collection" -> Symbol)) // 1)
    val df = sparkSession.read.mongo(readConfig)
    df.createOrReplaceTempView("data")
    sparkSession.sql("Select timestamp,open,high,low,close,volume FROM data order by Timestamp desc limit 1")


    //    //Testing: Load data from local for Predicting
    //    sparkSession.read.option("header", "true").csv("/Users/amit/Desktop/test.csv") //mongoDB 1st document of document
    //      .withColumn("Open", lit("0"))



  }


}

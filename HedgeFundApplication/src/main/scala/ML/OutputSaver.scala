package ML

import com.mongodb.spark.config.ReadConfig
import org.apache.spark.ml.PipelineModel
import org.apache.spark.sql.{DataFrame, SaveMode}
import org.apache.spark.sql.SparkSession
import com.mongodb.spark.config.ReadConfig
import com.mongodb.spark.sql._
import org.apache.spark.sql.SparkSession
import org.apache.spark.api.java.JavaSparkContext;
import com.mongodb.spark.config.WriteConfig;
import com.mongodb.spark.MongoSpark;


// save your output from the various objects here

object OutputSaver {

  // function to save a fitted pipeline
  def pipelineSaver(pipelineModel: PipelineModel, Symbol: String): Unit = {

    //val pipelineSaverPath = s"/pipelines_$Symbol/fitted-pipeline-$Symbol"

    pipelineModel
      .write
      .overwrite()
      .save(s"./${Symbol}_pipelines/${Symbol}_fitted-pipeline")

  }

  // function to save predictions
  def predictionsSaver(sparkSession: SparkSession, dataFrame: DataFrame, Symbol: String): Unit = {

    //val predictionSaverPath = s"./$Symbol%s_predictions/$Symbol.predictions_csv/"

    dataFrame
      .select("Timestamp", "High", "Low", "Close", "Volume", "prediction")
      .write
      .option("header", "true")
      .mode(saveMode = SaveMode.Overwrite)
      .csv(path = s"./${Symbol}_predictions/${Symbol}_predictions_csv/")

    //save in mongo
    sparkSession.conf.set("spark.mongodb.input.uri", s"mongodb://127.0.0.1/scaladb.$Symbol.prediction")
    val df_predicted = sparkSession.read.format("csv").option("header", "true").load(s"./${Symbol}_predictions/${Symbol}_predictions_csv/")

    df_predicted.write
      .option("uri", "mongodb://127.0.0.1/")
      .option("spark.mongodb.output.database", "scaladb")
      .option("spark.mongodb.output.collection", s"$Symbol.prediction")

      .format(source = "mongo").mode("append").save()

  }

  def MetricSaver(dataFrame: DataFrame, Symbol: String): Unit = {

    //val MetricSaverPath =s"./Model_metrics_$Symbol/model_metrics_$Symbol%s_csv"

    dataFrame
      .select("Model", "RMSE")
      .write
      .option("header", "true")
      .mode(saveMode = SaveMode.Overwrite)
      .csv(path = s"./${Symbol}_Model_metrics/${Symbol}_model_metrics_csv")


  }


}


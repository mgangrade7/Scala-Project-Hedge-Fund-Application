package machineLearning

import org.apache.spark.ml.PipelineModel
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object OutputSaver {

  /**
   * function to save a fitted pipeline
   * @param pipelineModel machine learning pipeline with fitted model
   * @param Symbol stock symbol
   */
  def pipelineSaver(pipelineModel: PipelineModel, Symbol: String): Unit = {

    pipelineModel
      .write
      .overwrite()
      .save(s"./${Symbol}_pipelines/${Symbol}_fitted-pipeline")

  }

  /**
   * function to save a predicted values in MongoDB
   * @param sparkSession DateFrame to train the model
   * @param Symbol DataFrame to test the model
   * @param dataFrame  DataFrame of predicted values
   */
  def predictionsSaver(sparkSession: SparkSession, dataFrame: DataFrame, Symbol: String): Unit = {

    dataFrame
      .select("Timestamp", "High", "Low", "Close", "Volume", "prediction")
      .write
      .option("header", "true")
      .mode(saveMode = SaveMode.Overwrite)
      .csv(path = s"./${Symbol}_predictions/${Symbol}_predictions_csv/")

    sparkSession.conf.set("spark.mongodb.input.uri", s"mongodb://127.0.0.1/scaladb.$Symbol.prediction")
    val df_predicted = sparkSession.read.format("csv").option("header", "true").load(s"./${Symbol}_predictions/${Symbol}_predictions_csv/")

    df_predicted.write
      .option("uri", "mongodb://127.0.0.1/")
      .option("spark.mongodb.output.database", "scaladb")
      .option("spark.mongodb.output.collection", s"$Symbol.prediction")

      .format(source = "mongo").mode("append").save()

  }

  /**
   * function to save a model metrics (Model name, RMSE value)
   * @param dataFrame model metrics DateFrame
   * @param Symbol stock symbol
   */
  def MetricSaver(dataFrame: DataFrame, Symbol: String): Unit = {

    dataFrame
      .select("Model", "RMSE")
      .write
      .option("header", "true")
      .mode(saveMode = SaveMode.Overwrite)
      .csv(path = s"./${Symbol}_Model_metrics/${Symbol}_model_metrics_csv")


  }


}

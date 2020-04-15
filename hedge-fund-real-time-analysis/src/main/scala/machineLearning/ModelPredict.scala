package machineLearning

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.PipelineModel
/**
 * Driver to make prediction and save into csv file
 */

object ModelPredict {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)

    def runModelPredict(Symbol: String): Unit = {

      val stock_symbol = Symbol//stock symbol

      val spark = SparkSessionCreator.sparkSessionCreate() // create spark session

      val rawTestData = DataSourcer.rawTestData(sparkSession = spark, Symbol = stock_symbol)// train data

      val cleanTestData = DataCleaner.cleanData(dataFrame = rawTestData)// clean train data

      val fittedPipeline = PipelineModel.load(s"./${stock_symbol}_pipelines/${stock_symbol}_fitted-pipeline")// load fitted pipeline

      val predictions = fittedPipeline.transform(dataset = cleanTestData) // make predictions

      OutputSaver.predictionsSaver(sparkSession = spark, dataFrame = predictions, Symbol = stock_symbol)// save prediction
    }

    runModelPredict("ford")
    runModelPredict("gm")


  }

}

package ML

import org.apache.spark.ml.PipelineModel

import org.apache.log4j.Logger
import org.apache.log4j.Level


object ModelPredict {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)

    def runModelPredict(Symbol: String): Unit = {
      //stock symbol
      val stock_symbol = Symbol

      // create spark session
      val spark = SparkSessionCreator.sparkSessionCreate()

      // train data
      val rawTestData = DataSourcer.rawTestData(sparkSession = spark, Symbol = stock_symbol)

      // clean train data
      val cleanTestData = DataCleaner.cleanData(dataFrame = rawTestData)

      // load fitted pipeline
      val fittedPipeline = PipelineModel.load(s"./${stock_symbol}_pipelines/${stock_symbol}_fitted-pipeline")

      // make predictions
      val predictions = fittedPipeline.transform(dataset = cleanTestData)

      // save prediction
      OutputSaver.predictionsSaver(sparkSession = spark, dataFrame = predictions, Symbol = stock_symbol)
    }

    runModelPredict("ford")
    runModelPredict("gm")


  }

}

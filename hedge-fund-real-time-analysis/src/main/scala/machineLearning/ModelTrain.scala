package machineLearning

import machineLearning.OutputSaver.pipelineSaver
import org.apache.log4j.{Level, Logger}
/**
 * Driver to save machine learning pipeline
 */

object ModelTrain {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)

    def runModelTrain(Symbol: String): Unit = {

      val stock_symbol = Symbol//stock symbol

      val spark = SparkSessionCreator.sparkSessionCreate()// create spark session

      val rawTrainData = DataSourcer.rawTrainData(sparkSession = spark, stock_symbol)// train data

      val cleanTrainData = DataCleaner.cleanData(dataFrame = rawTrainData) // clean train data

      val fittedPipeline = MachineLearning.pipelineFit(dataFrame = cleanTrainData, stock_symbol) // fitted pipeline

      pipelineSaver(pipelineModel = fittedPipeline, stock_symbol)// save fitted pipeline
    }

    runModelTrain("ford")
//    runModelTrain("gm")

  }

}

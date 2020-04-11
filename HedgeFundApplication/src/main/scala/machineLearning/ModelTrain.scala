import machineLearning.OutputSaver._

import org.apache.log4j.Logger
import org.apache.log4j.Level


object ModelTrain {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)

    def runModelTrain(Symbol: String): Unit = {
      //stock symbol
      val stock_symbol = Symbol

      // create spark session
      val spark = machineLearning.SparkSessionCreator.sparkSessionCreate()

      // train data
      val rawTrainData = DataSourcer.rawTrainData(sparkSession = spark, stock_symbol)

      // clean train data
      val cleanTrainData = machineLearning.DataCleaner.cleanData(dataFrame = rawTrainData)

      // fitted pipeline
      val fittedPipeline = MachineLearning.pipelineFit(dataFrame = cleanTrainData, stock_symbol)

      // save fitted pipeline
      pipelineSaver(pipelineModel = fittedPipeline, stock_symbol)
    }

    runModelTrain("ford")
    runModelTrain("gm")

  }

}

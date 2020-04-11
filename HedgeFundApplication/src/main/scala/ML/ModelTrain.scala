import ML.{DataCleaner, SparkSessionCreator}
import org.apache.log4j.Logger
import org.apache.log4j.Level
import ML.OutputSaver._


object ModelTrain {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)

    def runModelTrain(Symbol: String): Unit = {
      //stock symbol
      val stock_symbol = Symbol

      // create spark session
      val spark = SparkSessionCreator.sparkSessionCreate()

      // train data
      val rawTrainData = DataSourcer.rawTrainData(sparkSession = spark, stock_symbol)

      // clean train data
      val cleanTrainData = DataCleaner.cleanData(dataFrame = rawTrainData)

      // fitted pipeline
      val fittedPipeline = MachineLearning.pipelineFit(dataFrame = cleanTrainData, stock_symbol)

      // save fitted pipeline
      pipelineSaver(pipelineModel = fittedPipeline, stock_symbol)
    }

    runModelTrain("ford")
//    runModelTrain("gm")

  }

}

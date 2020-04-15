package machineLearning

import org.apache.spark.sql.DataFrame

object DataCleaner {

  /**
   * function to produce a clean data frame from a raw data frame
   * @param dataFrame DateFrame to train the model
   */
  def cleanData(dataFrame: DataFrame): DataFrame = {

    // def function to format data correctly
    def formatData(dataFrame: DataFrame): DataFrame = {

      dataFrame
        .withColumn("Timestamp", dataFrame("Timestamp").cast("String"))
        .withColumn("Open", dataFrame("Open").cast("Double"))
        .withColumn("High", dataFrame("High").cast("Double"))
        .withColumn("Low", dataFrame("Low").cast("Double"))
        .withColumn("Close", dataFrame("Close").cast("Double"))
        .withColumn("Volume", dataFrame("Volume").cast("Double"))
        .withColumnRenamed("Open", "label")

    }

    // format raw data
    val outputData = formatData(dataFrame)


    // return cleaned data frame
    outputData

  }

}

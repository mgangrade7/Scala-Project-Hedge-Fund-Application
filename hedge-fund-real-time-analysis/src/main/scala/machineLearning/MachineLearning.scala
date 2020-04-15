package machineLearning

import machineLearning.MLModels.LinearRegressionModel.linearRegressionModel
import machineLearning.MLModels.DecisionTreeRegressionModel.decisionTreeRegressionModel
import machineLearning.MLModels.RandomForestRegressionModel.randomForestRegressionModel
import machineLearning.MLModels.GradientBoostingRegressionModel.gradientBoostingRegressionModel


import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.{Pipeline, PipelineModel, PipelineStage}
import org.apache.spark.sql.{DataFrame, SparkSession}

object MachineLearning extends App {

  /**
   * Develope ensemble of machine learning model and fit model in the pipeline based upon RMSE value
   * @param dataFrame train data
   * @param Symbol stock symbol
   */
  def pipelineFit(dataFrame: DataFrame, Symbol: String): PipelineModel = {
    println("****************** Inside Machine Learning -> pipelineFit *******************")
    dataFrame.show()
    // define feature vector assembler
    val featureAssembler = new VectorAssembler()
      .setInputCols(Array[String](
        "High",
        "Low",
        "Close",
        "Volume"
      ))
      .setOutputCol("features")


    // ************************* Data Pre-processing *************************
    val Array(trainingData, testData) = dataFrame.randomSplit(Array(0.7, 0.3)) //Split data train (70%) and test (30%)

    val df_train = featureAssembler.transform(trainingData)
    println("Training Data")
    df_train.show(5)
    df_train.select("features").show(5)
    println("Testing Data")
    val df_test = featureAssembler.transform(testData)
    df_test.show(5)
    df_test.select("features").show(5)
    println("*******************")

    // ************************* compute test error *************************
    val evaluator = new RegressionEvaluator()
      .setLabelCol("label")
      .setPredictionCol("prediction")
      .setMetricName("rmse")

    // ************************* Machine Learning Models *************************
    val(lrModel, rmse_lr) = linearRegressionModel(df_train, df_test, evaluator) //Linear Regression Model
    val(dtModel, rmse_dt) = decisionTreeRegressionModel(df_train, df_test, evaluator) //Decision Tree Regression Model
    val(rfrModel, rmse_rfr) = randomForestRegressionModel(df_train, df_test, evaluator) //Random Forest Regression
    val(gbtModel, rmse_gbr) = gradientBoostingRegressionModel(df_train, df_test, evaluator) //Gradient Boosting Regression

    //*************************  DataFrame for analysing model metrics *************************
    val ss = SparkSession.builder().appName("DataSet Test")
      .master("local[*]").getOrCreate()
    import ss.implicits._

    val ModelMetricDS = Seq(
      ("Linear Regressor", rmse_lr),
      ("Decision Tree Regressor", rmse_dt),
      ("Random Forest Regressor", rmse_rfr),
      ("Gradient Boosting Regressor", rmse_gbr)).toDF("Model", "RMSE")

    ModelMetricDS.show()
    OutputSaver.MetricSaver(ModelMetricDS, Symbol) //Saving Data frame in local

    //************************* ML Model Map *************************
    val models: Map[Double, Any] = Map(
      rmse_lr -> lrModel,
      rmse_dt -> dtModel,
      rmse_rfr -> rfrModel,
      rmse_gbr -> gbtModel
    )

    //************************* Model Selection *************************
    def lowestKeyMember[Any](m: Map[Double, Any]): Any = m(m.keys.min)
    val final_model = lowestKeyMember(models).asInstanceOf[PipelineStage] //Selecting the model with lowest RMSE value

    //*************************  Creating Pipeline *************************

    val pipeline = new Pipeline()
      .setStages(
        Array(
          featureAssembler, //Schema for input/output data
          final_model //fitting selected model in pipeline
        )
      )

    //************************* Initiating Pipeline *************************
    val pipelineModel = pipeline.fit(dataFrame)


    pipelineModel // return fitted pipeline

  }

}

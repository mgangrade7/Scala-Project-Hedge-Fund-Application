package machineLearning

import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression._
import org.apache.spark.ml.{Pipeline, PipelineModel, PipelineStage}
import org.apache.spark.sql.{DataFrame, SparkSession}

object MachineLearning extends App {


  def pipelineFit(dataFrame: DataFrame, Symbol: String): PipelineModel = {
    println("*************************************")
    dataFrame.show()
    // define feature vector assembler
    val featureAssembler = new VectorAssembler()
      .setInputCols(Array[String](
        "High",
        "Low",
        "Close",
        "Volume"
      )
      )
      .setOutputCol("features")

    //1st row multiply by 30


    val Array(trainingData, testData) = dataFrame.randomSplit(Array(0.7, 0.3))

    val df_train = featureAssembler.transform(trainingData)
    val df_test = featureAssembler.transform(testData)

    //val final_model

    // ************************* compute test error *************************
    val evaluator = new RegressionEvaluator()
      .setLabelCol("label")
      .setPredictionCol("prediction")
      .setMetricName("rmse")

    // ************************* define Linear regression *************************
    val lr = new LinearRegression()
      .setMaxIter(10)
      .setRegParam(0.3)
      .setElasticNetParam(0.8)

    val lrModel: LinearRegressionModel = lr.fit(df_train)
    val predictions_lr = lrModel.transform(df_test)

    // select (prediction, true label)
    predictions_lr.select("prediction", "label", "features").show(5)

    val rmse_lr = evaluator.evaluate(predictions_lr)
    println(s"Root Mean Squared Error (RMSE) on test data = $rmse_lr")

    // ************************* define decision tree regression *************************
    val dt = new DecisionTreeRegressor()
      .setLabelCol("label")
      .setFeaturesCol("features")

    val dtModel: DecisionTreeRegressionModel = dt.fit(df_train)
    val predictions_dt = dtModel.transform(df_test)

    predictions_dt.select("prediction", "label", "features").show(5)

    val rmse_dt = evaluator.evaluate(predictions_dt)
    println(s"Root Mean Squared Error (RMSE) on test data = $rmse_dt")


    // ************************* define random forest regression *************************
    val rf = new RandomForestRegressor()
      .setLabelCol("label")
      .setFeaturesCol("features")

    val rfrModel: RandomForestRegressionModel = rf.fit(df_train)
    val predictions_rfr = rfrModel.transform(df_test)

    // select (prediction, true label)
    predictions_rfr.select("prediction", "label", "features").show(5)

    // compute test error

    val rmse_rfr = evaluator.evaluate(predictions_rfr)
    println(s"Root Mean Squared Error (RMSE) on test data = $rmse_rfr")

    // ************************* define Gradient boosting regression *************************
    val gbt = new GBTRegressor()
      .setLabelCol("label")
      .setFeaturesCol("features")
      .setMaxIter(10)

    val gbtModel: GBTRegressionModel = gbt.fit(df_train)
    val predictions_gbt = gbtModel.transform(df_test)
    predictions_gbt.select("prediction", "label", "features").show(5)

    val rmse_gbr = evaluator.evaluate(predictions_gbt)
    println(s"Root Mean Squared Error (RMSE) on test data = $rmse_gbr")

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
    OutputSaver.MetricSaver(ModelMetricDS, Symbol)


    //*************************  Selecting model with lowest RSME value for deployment in pipeline *************************
    def lowestKeyMember[Any](m: Map[Double, Any]): Any = m(m.keys.min)

    val models: Map[Double, Any] = Map(
      rmse_lr -> lrModel,
      rmse_dt -> dtModel,
      rmse_rfr -> rfrModel,
      rmse_gbr -> gbtModel
    )

    val final_model = lowestKeyMember(models).asInstanceOf[PipelineStage]

    //*************************  Creating Pipeline *************************

    val pipeline = new Pipeline()
      .setStages(
        Array(
          featureAssembler,
          final_model
        )
      )


    // fit pipeline
    val pipelineModel = pipeline.fit(dataFrame)

    // return fitted pipeline
    pipelineModel

  }

}

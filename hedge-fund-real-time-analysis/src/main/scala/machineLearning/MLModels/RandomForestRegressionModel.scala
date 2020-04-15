package machineLearning.MLModels

import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.regression.{RandomForestRegressionModel, RandomForestRegressor}
import org.apache.spark.sql.DataFrame
/**
 * Random Forest Regression Model
 */
object RandomForestRegressionModel {
  /**
   * Build Random Forest Regression Model and compute model metric (RMSE)
   * @param df_train DateFrame to train the model
   * @param df_test DataFrame to test the model
   * @param evaluator object for computing model metrics
   */

  def randomForestRegressionModel(df_train: DataFrame, df_test: DataFrame, evaluator: RegressionEvaluator) = {
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

    (rfrModel, rmse_rfr)
  }
}

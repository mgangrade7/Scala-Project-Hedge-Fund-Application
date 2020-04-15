package machineLearning.MLModels

import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.regression.{GBTRegressionModel, GBTRegressor}
import org.apache.spark.sql.DataFrame
/**
 * Gradient Boosting Regression Model
 */
object GradientBoostingRegressionModel {

  /**
   * Build Gradient Boosting Regression Model and compute model metric (RMSE)
   * @param df_train DateFrame to train the model
   * @param df_test DataFrame to test the model
   * @param evaluator object for computing model metrics
   */
  def gradientBoostingRegressionModel(df_train: DataFrame, df_test: DataFrame, evaluator: RegressionEvaluator) = {
    val gbt = new GBTRegressor()
      .setLabelCol("label")
      .setFeaturesCol("features")
      .setMaxIter(10)

    val gbtModel: GBTRegressionModel = gbt.fit(df_train)
    val predictions_gbt = gbtModel.transform(df_test)
    predictions_gbt.select("prediction", "label", "features").show(5)

    val rmse_gbr = evaluator.evaluate(predictions_gbt)
    println(s"Root Mean Squared Error (RMSE) on test data = $rmse_gbr")

    (gbtModel, rmse_gbr)
  }
}

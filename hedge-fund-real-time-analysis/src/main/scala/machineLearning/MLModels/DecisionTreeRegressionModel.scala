package machineLearning.MLModels

import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.regression.{DecisionTreeRegressionModel, DecisionTreeRegressor}
import org.apache.spark.sql.DataFrame
/**
 * Decision Tree Regression Model
 */
object DecisionTreeRegressionModel {


  /**
   * Build Decision Tree Regression Model and compute model metric (RMSE)
   * @param df_train DateFrame to train the model
   * @param df_test DataFrame to test the model
   * @param evaluator object for computing model metrics
   */
  def decisionTreeRegressionModel(df_train: DataFrame, df_test: DataFrame, evaluator: RegressionEvaluator) = {
    val dt = new DecisionTreeRegressor()
      .setLabelCol("label")
      .setFeaturesCol("features")

    val dtModel: DecisionTreeRegressionModel = dt.fit(df_train)
    val predictions_dt = dtModel.transform(df_test)


    predictions_dt.select("prediction", "label", "features").show(5)

    val rmse_dt = evaluator.evaluate(predictions_dt)
    println(s"Root Mean Squared Error (RMSE) on test data = $rmse_dt")

    (dtModel, rmse_dt)
  }
}
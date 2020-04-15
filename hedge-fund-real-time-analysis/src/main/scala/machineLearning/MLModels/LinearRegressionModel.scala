package machineLearning.MLModels

import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.regression.{LinearRegression, LinearRegressionModel}
import org.apache.spark.sql.DataFrame
/**
 * Linear Regression Model
 */

object LinearRegressionModel {

  /**
   * Build Linear Regression Model and compute model metric (RMSE)
   * @param df_train DateFrame to train the model
   * @param df_test DataFrame to test the model
   * @param evaluator object for computing model metrics
   */
  def linearRegressionModel(df_train: DataFrame, df_test: DataFrame, evaluator: RegressionEvaluator) = {
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

    (lrModel, rmse_lr)
  }
}

package MachineLearningModels
import machineLearning.MLModels.DecisionTreeRegressionModel
import machineLearning.SparkSessionCreator
import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.DataFrame
import org.scalatest.FunSuite

class DecisionTreeRegressionModelTest extends FunSuite {
  test("MLModels.DecisionTreeRegressionModel.decisionTreeRegressionModel") {
    Logger.getLogger("org").setLevel(Level.OFF)

    val sparkSession = SparkSessionCreator.sparkSessionCreate()

    var fibonacci : DataFrame = sparkSession.read.option("header", "true").csv("./src/main/resources/3x.csv")

    fibonacci = fibonacci
      .withColumn("n", fibonacci("n").cast("Double"))
      .withColumn("value", fibonacci("value").cast("Double"))
      .withColumnRenamed("value", "label")


    val evaluator = new RegressionEvaluator()
      .setLabelCol("label")
      .setPredictionCol("prediction")
      .setMetricName("rmse")

    val featureAssembler = new VectorAssembler()
      .setInputCols(Array[String]("n"))
      .setOutputCol("features")

    val Array(trainingData, testData) = fibonacci.randomSplit(Array(0.7, 0.3))

    val df_train = featureAssembler.transform(trainingData)
    val df_test = featureAssembler.transform(testData)

    import sparkSession.implicits._
    val test_10 = featureAssembler.transform(Seq(10).toDF("n"))
    val test_20 = featureAssembler.transform(Seq(20).toDF("n"))

    assert(
      DecisionTreeRegressionModel.decisionTreeRegressionModel(df_train, df_test, evaluator)._1.transform(test_10).select("prediction").first().get(0) !== DecisionTreeRegressionModel.decisionTreeRegressionModel(df_train, df_test, evaluator)._1.transform(test_20).select("prediction").first().get(0)
    )
 }
}

# SCALA-PROJECT 
## Hedge Fund Application: Real Time Risk Analysis

![alt text](/hedge-fund-real-time-analysis/Images/logo.png "Logo Title")

## Objective
* Develop a scalable & reactive infrastructure for real-time risk analysis
* Maintain updated database for analysis
* Develop robust data pipeline for information digestion
* Develop ensemble of machine learning model 
* Deploy best rmse model in the pipeline for reactive experience
* Fetch data in real-time from mongoDB for interactive dashboards

## Architecture
![alt text](https://github.com/amitpingale92/Big-Data-Scala-Final-Project/blob/master/Images/ScalaProjectArchitecture.png "Architecture")

## Work Flow
### Data Engineering
* Fetch real-time data from Alpha Vantage API
* Create Kafka topic for multiple stock data
* Produce & consume data using scala application
![alt text](https://github.com/amitpingale92/Big-Data-Scala-Final-Project/blob/master/Images/Kafka.png "Kafka")

## Machine Learning
* Develop ensemble of models
* * Linear Regression
* * Decision Tree Regression
* * Random Forest Regression
* * Gradient Boosting Regression
![alt text](https://github.com/amitpingale92/Big-Data-Scala-Final-Project/blob/master/Images/MachineLearning.png "ML Model")

## Machine Learning Pipeline
* Select model with lowest RMSE value
* Deploy in spark pipeline
![alt text](https://github.com/amitpingale92/Big-Data-Scala-Final-Project/blob/master/Images/MachineLearningPipeline.png "Pipeline")

## Requirements
* Spark {ML, SQL}
* MongoDB
* Tableau
* Running Kafka cluster script
* [requirements.sh](https://github.com/amitpingale92/Big-Data-Scala-Final-Project/blob/master/requirements.sh)

## Authors
* Amit Pingale
* Shreya Nair
* Mayank Gangrade

## Licence
This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/amitpingale92/Big-Data-Scala-Final-Project/blob/master/LICENSE) file for details





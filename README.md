# SCALA-PROJECT 
## Hedge Fund Application: Real Time Risk Analysis

![alt text](/hedge-fund-real-time-analysis/Images/Hedge-Funds.jpg "Logo Title")

## Objective
* Develop a scalable & reactive infrastructure for real-time risk analysis
* Maintain updated database for analysis
* Develop robust data pipeline for information digestion
* Develop ensemble of machine learning model 
* Deploy best rmse model in the pipeline for reactive experience
* Fetch data in real-time from mongoDB for interactive dashboards

## Architecture
![alt text](/hedge-fund-real-time-analysis/Images/ScalaProjectArchitecture.png "Architecture")

## Work Flow
### Data Engineering
* Fetch real-time data from Alpha Vantage API
* Create Kafka topic for multiple stock data
* Produce & consume data using scala application
![alt text](/hedge-fund-real-time-analysis/Images/Kafka.png "Kafka")

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
![alt text](/hedge-fund-real-time-analysis/Images/MachineLearningPipeline.png "Pipeline")

## Requirements
* Spark {ML, SQL}
* MongoDB
* Tableau
* Running Kafka cluster script

## How to install and run Kafka server

#### Step 1: Install Java
```sh
sudo apt update
sudo apt install default-jdk
```

#### Step 2: Download Apache Kafka
```sh
wget http://www-us.apache.org/dist/kafka/2.4.0/kafka_2.13-2.4.0.tgz
tar xzf kafka_2.13-2.4.0.tgz
mv kafka_2.13-2.4.0 /usr/local/kafka
```

#### Step 3: Start Kafka Server
```sh
sudo systemctl start zookeeper
sudo systemctl start kafka
sudo systemctl status kafka
```

#### Step 4: Create a Topic in Kafka
```sh
cd /usr/local/kafka
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic ford
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic gm
```

#### Step 5: Send Messages to Kafka
```sh
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic ford
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic gm
```

#### Step 6: Using Kafka Consumer
```sh
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic ford --from-beginning
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic gm --from-beginning
```

## Authors
* Amit Pingale
* Mayank Gangrade

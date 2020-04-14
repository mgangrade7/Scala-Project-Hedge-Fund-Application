import java.util
import java.util.Properties

import mongo.Helpers._
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}
import org.apache.kafka.clients.producer._
import org.mongodb.scala.{MongoCollection, _}
import scalaj.http.{Http, HttpRequest, HttpResponse}
import spray.json.DefaultJsonProtocol._
import spray.json.{JsValue, _}

import scala.collection.JavaConverters._

/**
 * Package level object consist of all the required methods
 */
package object kafka {

//  API Key provided by AlphaVantage. One can get the key after registering on website.
  val APIKEY: String = "U4HV0SUO7S0J40TC"

  /**
   * Function to check the URL response
   * @param url link of the URL
   * @return HTTP Response having properties like body, code, header
   */
  def checkURLResponse(url: String): HttpResponse[String] = {
    val response: HttpResponse[String] = Http(url).asString
    response
  }

  /**
   * Publish data on kafka topic
   * @param topic name of the kafka topic
   * @param symbol symbol of the company share found on AlphaVantage website
   */
  def publishToKafka(topic: String, symbol: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    val url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="+symbol+"&interval=5min&apikey="+APIKEY

    // Make API call in every 5 minutes
    while(true) {
      checkURLResponse(url).code match {
        case 200 => {
          val request: HttpRequest = Http(url)
          val record = new ProducerRecord[String, String](topic, request.asString.body)
          producer.send(record)
          Thread.sleep(300000)
//          producer.close(
        }
        case _ => println("error in url response, response code : ")
      }
    }
  }

  /**
   * Consume data from Kafka topic, Parse the data and load the same into MongoDb
   * @param topic name of the Kafka topic from where data needs to be consumed
   * @param dbname database name in the mongodb
   * @param collectionName mongodb collection name
   */
  def consumeFromKafkaAndStoreInDB(topic: String, dbname: String, collectionName: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("auto.offset.reset", "latest")
    props.put("group.id", "consumer-group")
    
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Arrays.asList(topic))

    val mongoClient: MongoClient = MongoClient()

    val database: MongoDatabase = mongoClient.getDatabase(dbname)

    val collection: MongoCollection[Document] = database.getCollection(collectionName)

    while (true) {
      val record = consumer.poll(1000).asScala
      for (data <- record.iterator) {
        println(data.value())
        val test: Unit = parseDataAndInsert(data, collection)
        println(test.toString)
      }
    }
  }

  /**
   * Parse data and insert into Mongodb collection
   * @param data date to be parsed
   * @param collection name of the  mongodb collection
   */
  def parseDataAndInsert(data: ConsumerRecord[String, String], collection: MongoCollection[Document]): Unit = {
    val responseOne: String = data.value()
    val globalMap = responseOne.parseJson.convertTo[Map[String, JsValue]]

    val metaData = globalMap.get("Meta Data").get.convertTo[Map[String, JsValue]]
    val symbol: String = metaData.get("2. Symbol").get.toString().replaceAll("\"", "")
    println(symbol)

    val reviewMap = globalMap.get("Time Series (5min)").get.convertTo[Map[String, JsValue]]

    for ((k, v) <- reviewMap) {
      val timeStamp = k
      val value = v.convertTo[Map[String, JsValue]]

      val open = value.get("1. open").get.toString().replaceAll("\"", "").toDouble
      val high = value.get("2. high").get.toString().replaceAll("\"", "").toDouble
      val low = value.get("3. low").get.toString().replaceAll("\"", "").toDouble
      val close = value.get("4. close").get.toString().replaceAll("\"", "").toDouble
      val volume = value.get("5. volume").get.toString().replaceAll("\"", "").toDouble

      println(symbol + " " + timeStamp + " " + open + " " + high + " " + low + " " + close + " " + volume)

      val doc: Document = Document(
        "Symbol" -> symbol,
        "Timestamp" -> timeStamp,
        "Open" -> open,
        "High" -> high,
        "Low" -> low,
        "Close" -> close,
        "Volume" -> volume
      )
      //Push data into MongoDB
      collection.insertOne(doc).results()
    }
  }

}

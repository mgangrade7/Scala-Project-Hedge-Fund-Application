package kafka

/**
 * Consume data from Kafka topic and store the same into MongoDb
 */

object consumer2 extends App {

  val topic: String = "gm"
  val dbname: String = "scaladb"
  val collectionName: String = "gm"

  consumeFromKafkaAndStoreInDB(topic = topic, dbname = dbname, collectionName = collectionName)

}

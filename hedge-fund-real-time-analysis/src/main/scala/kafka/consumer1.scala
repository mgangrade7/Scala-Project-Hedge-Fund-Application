package kafka

/**
 * Consume data from Kafka topic and store the same into MongoDb
 */

object consumer1 extends App {

  val topic: String = "ford"
  val dbname: String = "scaladb"
  val collectionName: String = "ford"

  consumeFromKafkaAndStoreInDB(topic = topic, dbname = dbname, collectionName = collectionName)

}

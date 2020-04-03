package kafka

object consumer1 extends App {

  val topic: String = "ford"
  val dbname: String = "scaladb77"
  val collectionName: String = "zord"

  consumeFromKafkaAndStoreInDB(topic = topic, dbname = dbname, collectionName = collectionName)

}

package kafka

object consumer2 extends App {

  val topic: String = "ge"
  val dbname: String = "scaladb"
  val collectionName: String = "ge"

  consumeFromKafkaAndStoreInDB(topic = topic, dbname = dbname, collectionName = collectionName)

}

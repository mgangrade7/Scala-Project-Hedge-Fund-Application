package kafka

/**
 * Hit the URL, get the response and Publish the same on Kafka topic
 */

object producer1 extends App {

  val topic: String = "ford"
  val symbol: String = "F"

  writeToKafka(topic = topic, symbol = symbol)

}

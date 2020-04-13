package kafka

/**
 * Hit the URL, get the response and Publish the same on Kafka topic
 */

object producer2 extends App {

  val topic: String = "gm"
  val symbol: String = "GM"

  writeToKafka(topic = topic, symbol = symbol)

}

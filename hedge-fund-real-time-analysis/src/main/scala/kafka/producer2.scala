package kafka

object producer2 extends App {

  val topic: String = "gm"
  val symbol: String = "GM"

  writeToKafka(topic = topic, symbol = symbol)

}

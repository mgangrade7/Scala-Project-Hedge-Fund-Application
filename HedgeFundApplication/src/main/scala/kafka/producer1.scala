package kafka

object producer1 extends App {

  val topic: String = "ford"
  val symbol: String = "F"

  writeToKafka(topic = topic, symbol = symbol)

}

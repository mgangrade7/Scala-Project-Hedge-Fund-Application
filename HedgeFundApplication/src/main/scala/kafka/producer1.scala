package kafka

object producer1 extends App {

  val topic: String = "ford"
  val url: String = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=F&interval=5min&apikey=U4HV0SUO7S0J40TC"

  writeToKafka(topic = topic, url = url)

}

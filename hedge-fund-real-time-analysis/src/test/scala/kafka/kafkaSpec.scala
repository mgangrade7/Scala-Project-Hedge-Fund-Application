package kafka

import org.scalatest.{FlatSpec, Matchers}

class kafkaSpec extends FlatSpec with Matchers {

  val url: String = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=F&interval=5min"

  behavior of "checkURLResponse"
  it should "get the Ok response" in {
    val response = kafka.checkURLResponse(url)
    response.code shouldBe 200
  }

}

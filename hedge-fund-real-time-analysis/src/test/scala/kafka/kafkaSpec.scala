package kafka

import org.scalatest.{FlatSpec, Matchers}

class kafkaSpec extends FlatSpec with Matchers {

  val url: String = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=F&interval=5min"

  behavior of "checkURLResponse"
  it should "get the Ok response" in {
    val response = kafka.checkURLResponse(url)
    response.code shouldBe 200
  }

  behavior of "checkURLResponse"
  it should "get no error in response" in {
    val response = kafka.checkURLResponse(url)
    response.isError shouldBe false
    response.isClientError shouldBe false
    response.isServerError shouldBe false
    response.isNotError shouldBe true
    response.isSuccess shouldBe true
  }

  behavior of "checkURLResponse"
  it should "get response content in json format" in {
    val response = kafka.checkURLResponse(url)
    response.contentType.get shouldBe "application/json"
  }

  behavior of "checkURLResponse"
  it should "get correct response code" in {
    val response = kafka.checkURLResponse(url)
    response.is2xx shouldBe true
    response.is3xx shouldBe false
    response.is4xx shouldBe false
    response.is5xx shouldBe false
  }

}

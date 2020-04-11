package kafka
import scalaj.http.{Http, HttpRequest, HttpResponse}
object testobj extends App {

  val response: HttpResponse[String] = Http("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=F&interval=5min").asString
  println(response.body)
//  println(response.code)
//  println(response.headers)
//  println(response.cookies)
//  println("Hello, world!")

}

package kafka

import scalaj.http.{Http, HttpRequest, HttpResponse}
object testobj extends App {

  val url = "https://raw.githubusercontent.com/mgangrade7/Java-Coding-and-Concepts/master/test.txt"
  val response: HttpResponse[String] = Http(url).asString

  val body: String = response.body
    println(body)
  println("Hello, world!")



}

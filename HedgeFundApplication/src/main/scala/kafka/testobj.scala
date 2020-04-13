package kafka
import scalaj.http.{Http, HttpRequest, HttpResponse}
object testobj extends App {

  val response: HttpResponse[String] = Http("https://raw.githubusercontent.com/mgangrade7/Java-Coding-and-Concepts/master/coding/pom.xml").asString
  println(response.body)
  println(response.code)
  println(response.headers)
  println(response.cookies)
//  println("Hello, world!")

}

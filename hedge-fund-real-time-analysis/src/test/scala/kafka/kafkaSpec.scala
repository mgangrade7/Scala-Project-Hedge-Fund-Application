package kafka

import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.JavaConverters._

class kafkaSpec extends FlatSpec with Matchers{

  val url = "https://raw.githubusercontent.com/mgangrade7/Java-Coding-and-Concepts/master/test.txt"
  val topic = "test"

  behavior of "kafka"
  it should "proper records should be publish and consume from kafka topics" in {
    val response = kafka.publish(url, topic)

    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("auto.offset.reset", "latest")
    props.put("group.id", "consumer-group")

    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Arrays.asList(topic))

    while (true) {
      val record = consumer.poll(1000).asScala
      for (data <- record.iterator) {
        val value = data.value()
        println(value)
        value shouldBe "hello world"
      }
    }
  }
}

package com.rojosam

import java.time.{Duration, Instant}
import java.util.{Date, Properties}

import kafka.javaapi.producer.Producer
import kafka.producer.KeyedMessage
import kafka.producer.ProducerConfig


object GenerateMessages {

  def main(args: Array[String]): Unit = {
    val start = Instant.now
    println(s"Arguments: ${args.mkString(", ")}")
    val rnd = new scala.util.Random(255)
    val events = args(0).toLong

    val props = new Properties()

    props.put("metadata.broker.list", args(1))
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    props.put("partitioner.class", "com.rojosam.SimplePartitioner")
    props.put("request.required.acks", "1")

    val config = new ProducerConfig(props)

    val producer = new Producer[String, String](config)

    for (n <- 1L to events) {
      val runtime = new Date()
      val ip = "192.168.2." + rnd.nextInt(255)
      val msg = runtime + ",www.test_5.com," + ip + s",$n"
      val data = new KeyedMessage[String, String]("test_arq", ip, msg)
      producer.send(data)
    }
    producer.close
    val end = Instant.now()
    println(Duration.between(start, end).formatted("HH"))
  }

}

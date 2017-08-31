package com.rojosam.actors

import java.time.{Duration, Instant}
import java.util.{Date, Properties}

import akka.actor.{Actor, ActorLogging, Props}
import org.apache.kafka.clients.producer.ProducerRecord

import scala.language.implicitConversions



object KafkaProducer {
  def props(id:Int, servers:String):Props = Props(classOf[KafkaProducer], id, servers)
}

class KafkaProducer(id:Int, servers:String) extends Actor with ActorLogging{
  override def receive: Receive = {
    case numEvents:Long => {
      val start = Instant.now
      val rnd = new scala.util.Random(255)
      val props = new Properties()

      props.put("bootstrap.servers", servers)
      props.put("partitioner.class", "com.rojosam.SimplePartitioner")
      props.put("acks", "all")
      props.put("retries", 0: java.lang.Integer)
      props.put("batch.size", 16384: java.lang.Integer)
      props.put("linger.ms", 1: java.lang.Integer)
      props.put("buffer.memory", 33554432: java.lang.Integer)
      props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      props.put("compression.type", "snappy")

      //val config = new ProducerConfig(props)

      val producer = new org.apache.kafka.clients.producer.KafkaProducer[String, String](props)

      for (n <- 1L to numEvents) {
        val runtime = new Date()
        val ip = "192.168.2.255"
        val msg = s"$runtime, $ip ,www.test_5.com, $n - $id"
        val data = new ProducerRecord[String, String]("test_arq", n.toString, msg)
        producer.send(data)
      }
      producer.close
      val end = Instant.now()
      val sec = math.max(Duration.between(start, end).getSeconds, 0.1)
      //val nano = Duration.between(start, end).getNano
      log.error(s"Actor [$id], Duration in seconds: [$sec] - Messages by sec [${numEvents/sec}]")
    }
  }
}

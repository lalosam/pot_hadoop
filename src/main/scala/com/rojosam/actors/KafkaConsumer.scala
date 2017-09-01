package com.rojosam.actors

import java.time.{Duration, Instant}
import java.util.{Date, Properties}

import akka.actor.{Actor, ActorLogging, Props}

import java.util


import scala.language.implicitConversions
import scala.collection.JavaConversions._



object KafkaConsumer {
  def props(id:Int, servers:String):Props = Props(classOf[KafkaConsumer], id, servers)
}

class KafkaConsumer(id:Int, servers:String) extends Actor with ActorLogging{
  override def receive: Receive = {
    case numEvents:Long => {
      var start = Instant.now
      val rnd = new scala.util.Random(255)
      val props = new Properties()
      val threshold = 100000L

      log.warning(s"Starting consumer [$id]")
//      props.put("bootstrap.servers", servers)
      props.put("zookeeper.connect", servers)
      props.put("group.id", "test")
      props.put("enable.auto.commit", "true")
      props.put("auto.commit.interval.ms", "1000")
      props.put("session.timeout.ms", "30000")
      props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
      props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
      props.put("client.id", s"TEST_ARQ_$id")
      val consumer = new org.apache.kafka.clients.consumer.KafkaConsumer[String, String](props)
      consumer.subscribe(util.Arrays.asList("test_arq"))
      var count = 0L
      while (true) {
        val records = consumer.poll(100)
        for (record <- records) {
          count += 1
          if(count % threshold == 0){
            val end = Instant.now()
            val sec = math.max(Duration.between(start, end).getSeconds, 0.1)
            start = Instant.now
            log.error(s"Actor [$id], Duration in seconds: [$sec] - Messages received [$threshold] - Accumulated [$count]")
          }
        }
      }

    }
  }
}

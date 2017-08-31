package com.rojosam

import java.util

import org.apache.kafka.clients.producer.Partitioner
import kafka.utils.VerifiableProperties
import org.apache.kafka.common.Cluster
import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by eduardo on 7/26/17.
  */
class SimplePartitioner extends Partitioner {
  val LOG = LoggerFactory.getLogger(this.getClass)

  LOG.error("Initializing SimplePartitioner")

  def this(props: VerifiableProperties) {
    this
  }

  override def partition(topic: String, key: scala.Any, keyBytes: Array[Byte], value: scala.Any, valueBytes: Array[Byte], cluster: Cluster): Int = {
      val stringKey = key.asInstanceOf[String]
    (stringKey.toLong % cluster.partitionCountForTopic(topic)).toInt
  }

  override def close(): Unit = {
    LOG.error("Finalizing SimplePartitioner")
  }

  override def configure(configs: util.Map[String, _]) : Unit = {

  }

}

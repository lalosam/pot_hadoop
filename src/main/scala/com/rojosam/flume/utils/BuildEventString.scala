package com.rojosam.flume.utils

import java.nio.charset.StandardCharsets

import org.apache.flume.Event

import scala.collection.JavaConverters._

object BuildEventString {

  def buildString(event:Event):String = {
    val body = new String(event.getBody, StandardCharsets.UTF_8)
    val headers = event.getHeaders.asScala.map( m => s"${m._1} = ${m._2}").mkString(", ")
    s"BODY [$body] -- HEADERS [$headers]"
  }

}

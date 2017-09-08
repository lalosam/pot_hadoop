package com.rojosam.flume.utils

import java.nio.charset.StandardCharsets

import org.apache.flume.Event

import scala.collection.JavaConverters._

object BuildEventString {

  def buildString(event:Event, includeHeaders:Boolean, encloseBody:Boolean):String = {
    val body = new String(event.getBody, StandardCharsets.UTF_8)

    val sb:StringBuilder = new StringBuilder
    if (encloseBody){
      sb.append("BODY [")
      sb.append(body)
      sb.append("]")
    }else{
      sb.append(body)
    }

    if(includeHeaders){
      val headers = event.getHeaders.asScala.map( m => s"${m._1} = ${m._2}").mkString(", ")
      sb.append(" -- HEADERS [")
      sb.append(headers)
      sb.append("]")
    }
   sb.toString()
  }

}

package com.rojosam.flume.interceptors

import java.util

import scala.collection.JavaConverters._
import com.rojosam.flume.utils.BuildEventString
import org.apache.flume.interceptor.Interceptor
import org.apache.flume.{Context, Event}

class CustomInterceptor(includeHeaders:Boolean, encloseBody:Boolean, dropHeadersList:Array[String]) extends Interceptor {

  override def intercept(event: Event):Event = {
    event.setBody(BuildEventString.buildString(event, includeHeaders, encloseBody).getBytes)
    event
  }

  override def intercept(events: util.List[Event]):util.List[Event] = {
    events.asScala.map(e => intercept(e)).asJava
  }

  override def initialize():Unit = {
    println("Initilizing CustomInterceptor")
  }

  override def close():Unit = {
    println ("Closing CustomInterceptor")
  }
}


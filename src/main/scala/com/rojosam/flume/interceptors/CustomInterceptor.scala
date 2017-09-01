package com.rojosam.flume.interceptors

import java.util

import org.apache.flume.interceptor.Interceptor
import org.apache.flume.Event

class CustomInterceptor extends Interceptor {

  override def intercept(event: Event):Event = {
    event
  }

  override def intercept(events: util.List[Event]):util.List[Event] = {
    events
  }

  override def initialize():Unit = {

  }

  override def close():Unit = {

  }
}

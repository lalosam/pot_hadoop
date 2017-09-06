package com.rojosam.flume.interceptors

import org.apache.flume.Context
import org.apache.flume.interceptor.Interceptor.Builder

class CustomInterceptorBuilder  extends Builder{
  override def build():CustomInterceptor = {
    new CustomInterceptor
  }

  override def configure(ctx: Context):Unit = {
    ctx.toString
  }
}

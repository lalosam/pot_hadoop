package com.rojosam.flume.interceptors

import org.apache.flume.Context
import org.apache.flume.interceptor.Interceptor.Builder


class CustomInterceptorBuilder  extends Builder{

  var includeHeaders:Boolean
  var encloseBody:Boolean
  var dropHeadersList:String

  val includeHeadersDefault = true
  val encloseBodyDefault = true
  val dropHeadersListDefault = ""
  val includeHeadersString = "includeHeaders"
  val encloseBodyString = "encloseBody"
  val dropHeadersListString = "dropHeadersList"


  override def build():CustomInterceptor = {
    new CustomInterceptor(includeHeaders, encloseBody, dropHeadersList.split(","))
  }

  override def configure(ctx: Context):Unit = {
    includeHeaders  = ctx.getBoolean(includeHeadersString, includeHeadersDefault)
    encloseBody     = ctx.getBoolean(encloseBodyString, encloseBodyDefault)
    dropHeadersList = ctx.getString(dropHeadersListString, dropHeadersListDefault)
  }
}

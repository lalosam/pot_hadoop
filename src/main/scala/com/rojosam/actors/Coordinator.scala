package com.rojosam.actors

import akka.actor.{Actor, Props}
import akka.routing.{ActorRefRoutee, BroadcastRoutingLogic, Router}
import com.rojosam.KafkaMain.Arguments

/**
  * Created by eduardo on 8/3/17.
  */
object Coordinator {
  def props(arguments:Arguments):Props = Props(classOf[Coordinator], arguments)
}

class Coordinator(arguments:Arguments) extends Actor {

 var routerProduce:Router = {
   var i = 0
   val routees = Vector.fill(arguments.threads) {
     i += 1
     val r = context.actorOf(KafkaProducer.props(i, arguments))
     context watch r
     ActorRefRoutee(r)
   }
   Router(BroadcastRoutingLogic(), routees)
 }

  var routerConsume:Router = {
    var i = 0
    val routees = Vector.fill(arguments.threads) {
      i += 1
      val r = context.actorOf(KafkaConsumer.props(i, arguments))
      context watch r
      ActorRefRoutee(r)
    }
    Router(BroadcastRoutingLogic(), routees)
  }


  override def receive: Receive = {
    case "produce" => routerProduce.route("NOT_MATTER", sender())
    case "consume" => routerConsume.route("NOT_MATTER", sender())
  }
}

package com.rojosam.actors

import akka.actor.{Actor, Props}
import akka.routing.{ActorRefRoutee, BroadcastRoutingLogic, Router}

/**
  * Created by eduardo on 8/3/17.
  */
object Coordinator {
  def props(threads:Int, numEvents:Long, servers:String):Props = Props(classOf[Coordinator], threads, numEvents, servers)
}

class Coordinator(threads:Int, numEvents:Long, servers:String) extends Actor {

 var routerProduce:Router = {
   var i = 0
   val routees = Vector.fill(threads) {
     i += 1
     val r = context.actorOf(KafkaProducer.props(i, servers))
     context watch r
     ActorRefRoutee(r)
   }
   Router(BroadcastRoutingLogic(), routees)
 }

  var routerConsume:Router = {
    var i = 0
    val routees = Vector.fill(threads) {
      i += 1
      val r = context.actorOf(KafkaConsumer.props(i, servers))
      context watch r
      ActorRefRoutee(r)
    }
    Router(BroadcastRoutingLogic(), routees)
  }


  override def receive: Receive = {
    case "produce" => routerProduce.route(numEvents, sender())
    case "consume" => routerConsume.route(numEvents, sender())
  }
}

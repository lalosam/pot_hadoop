package com.rojosam

import java.time.{Duration, Instant}

import akka.actor.ActorSystem
import com.rojosam.actors.Coordinator
import com.typesafe.config.ConfigFactory

/**
  * Created by eduardo on 7/28/17.
  */
object LoadActors {

  def main(args: Array[String]): Unit = {
    println(s"Arguments: ${args.mkString(", ")}")
    val config = ConfigFactory.load()
    val system: ActorSystem = ActorSystem("POT_Hadoop", config)
    try{
      val coordinator = system.actorOf(Coordinator.props(args(0).toInt, args(1).toLong, args(3)))
      Thread.sleep(3000)
      coordinator ! args(2)
    }finally {
      Thread.sleep(3000)
      system.shutdown()
    }
  }

}




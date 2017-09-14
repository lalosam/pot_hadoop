package com.rojosam

import java.lang

import akka.actor.ActorSystem
import com.rojosam.actors.Coordinator
import com.rojosam.utils.PrettyPrint
import com.typesafe.config.ConfigFactory

/**
  * Created by eduardo on 7/28/17.
  */
object LoadActors {

  case class Arguments(threads:Int = 1, numEvents:Long = 10, action:String="", kafkaServers:String="localhost:9092")

  def main(args: Array[String]): Unit = {

    val parser = new scopt.OptionParser[Arguments]("POT Kafka-Flume") {
      head("Kafka-Flume", "1.0")

      opt[Int]('t', "threads")
        .optional()
        .valueName("<num>")
        .action((x,c) => c.copy(threads = x)).text("Number of threads to send/recive kafka messages")

      opt[Long]('e', "numEvents")
        .optional()
        .valueName("<num>")
        .action((x,c) => c.copy(numEvents = x)).text("Number of events to send to kafka topic")

      opt[String]('a', "action")
        .required()
        .valueName("<action>")
        .action((x,c) => c.copy(action = x)).text("Action: [produce, consume]")

      opt[String]('s', "servers")
        .optional()
        .valueName("<server:host,...>")
        .action((x,c) => c.copy(kafkaServers = x)).text("Kafka broker list")
    }


    parser.parse(args.toSeq, Arguments()) match {
      case Some(arguments) =>
        println(PrettyPrint.print(arguments))
        run(arguments)
      case None =>
    }
  }

  def run(arguments: Arguments):Unit = {
    val config = ConfigFactory.load()
    val system: ActorSystem = ActorSystem("POT_Hadoop", config)
    try{
      val coordinator = system.actorOf(Coordinator.props(arguments.threads, arguments.numEvents, arguments.kafkaServers))
      // Wait the actors system is up and ready
      Thread.sleep(3000)
      coordinator ! arguments.action
    }finally {
      Thread.sleep(3000)
      system.shutdown()
    }
  }


}




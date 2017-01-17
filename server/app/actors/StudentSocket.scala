package actors

import akka.actor.{Actor, ActorRef, Props}
import play.api.libs.json.JsObject

class StudentSocket private (out: ActorRef) extends Actor {
	def receive: Receive = {
		case msg: JsObject =>
			println(msg)
			out ! msg
	}

	override def postStop(): Unit = {
		println("Stopped")
	}
}

object StudentSocket {
	def props(out: ActorRef) = Props(new StudentSocket(out))
}

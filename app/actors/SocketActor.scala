package actors

import akka.actor.{Actor, ActorRef, Props}
import play.api.libs.json.JsObject

class SocketActor private (out: ActorRef) extends Actor {
	def receive = {
		case msg: JsObject =>
			println(msg)
			out ! msg
	}

	override def postStop(): Unit = {
		println("Stopped")
	}
}

object SocketActor {
	def props(out: ActorRef) = Props(new SocketActor(out))
}

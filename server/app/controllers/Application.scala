package controllers

import actors.{StudentSocket, TeacherSocket}
import akka.actor.{ActorSystem, Props}
import akka.stream.Materializer
import com.google.inject.Inject
import play.api.libs.streams.ActorFlow
import play.api.mvc.WebSocket.MessageFlowTransformer
import play.api.mvc._
import socket.Message

class Application @Inject() (teacherSocketFactory: TeacherSocket.Factory)
                            (implicit as: ActorSystem, mat: Materializer)
		extends Controller {

	def index = Action {
		Ok(views.html.test())
	}

	/** A flow transformer allowing to read and write SocketFrames */
	private implicit val transformer: MessageFlowTransformer[Message, Message] =
		MessageFlowTransformer.jsonMessageFlowTransformer[Message, Message]

	/** The teacher socket endpoint */
	def teacherSocket = WebSocket.accept[Message, Message] { request =>
		ActorFlow.actorRef(out => Props(teacherSocketFactory(out)))
	}

	/** The student socket endpoint */
	def studentSocket(room: String) = WebSocket.accept[Message, Message] { request =>
		ActorFlow.actorRef(out => StudentSocket.props(out))
	}
}
package controllers

import actors.SocketActor
import akka.actor.ActorSystem
import akka.stream.Materializer
import com.google.inject.Inject
import play.api.libs.json.JsValue
import play.api.libs.streams.ActorFlow
import play.api.mvc._

class Application @Inject() (implicit as: ActorSystem, mat: Materializer) extends Controller {

	def index = Action {
		Ok(views.html.test())
	}

	def socket = WebSocket.accept[JsValue, JsValue] { request =>
		ActorFlow.actorRef(out => SocketActor.props(out))
	}

	def notFound(notFound: String) = Default.notFound

	def other(others: String) = index
}

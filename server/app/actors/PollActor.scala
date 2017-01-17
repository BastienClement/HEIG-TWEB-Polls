package actors

import akka.actor.Actor
import akka.util.Timeout
import java.util.concurrent.TimeUnit
import play.api.libs.json.{JsValue, Writes}
import scala.language.implicitConversions

trait PollActor extends Actor {
	implicit val defaultTimeout = Timeout(5, TimeUnit.SECONDS)
	implicit def toJsValue[T](value: T)(implicit w: Writes[T]): JsValue = w.writes(value)
}

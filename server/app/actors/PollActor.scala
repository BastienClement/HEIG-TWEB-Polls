package actors

import akka.actor.Actor
import akka.util.Timeout
import java.util.concurrent.TimeUnit
import play.api.libs.json.{JsValue, Writes}
import scala.language.implicitConversions

/**
  * Common attributes for Actors
  */
trait PollActor extends Actor {
	/** Default timeout for Akka operations requiring a timeout */
	implicit val defaultTimeout = Timeout(5, TimeUnit.SECONDS)

	/** Automatically converts anything that can be serialized to JSON */
	implicit def toJsValue[T](value: T)(implicit w: Writes[T]): JsValue = w.writes(value)
}

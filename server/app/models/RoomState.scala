package models

import play.api.libs.json.Json

case class RoomState(poll: Option[Poll], results: Option[Array[Int]], done: Boolean)

object RoomState {
	implicit val format = Json.format[RoomState]
}

package socket

import play.api.libs.json._

case class Message(label: String, payload: JsValue = JsNull)

object Message {
	implicit val format = new Format[Message] {
		/** Reads a message frame */
		def reads(json: JsValue): JsResult[Message] =
			for {
				label <- (json \ "label").validate[String]
				payload = (json \ "payload").asOpt[JsValue] getOrElse JsNull
			} yield Message(label, payload)

		/** Writes a message frame */
		def writes(msg: Message): JsObject =
			Json.obj(
				"label" -> msg.label,
				"payload" -> msg.payload
			)
	}
}

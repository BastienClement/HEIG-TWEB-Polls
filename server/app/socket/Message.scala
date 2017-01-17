package socket

import play.api.libs.json._

case class Message(label: String, payload: JsValue = JsNull)

object Message {
	/** The Message JSON serializer implementation */
	implicit val format = new Format[Message] {
		def reads(json: JsValue): JsResult[Message] =
			for {
				label <- (json \ "label").validate[String]
				payload = (json \ "payload").asOpt[JsValue] getOrElse JsNull
			} yield Message(label, payload)

		def writes(msg: Message): JsObject =
			Json.obj(
				"label" -> msg.label,
				"payload" -> msg.payload
			)
	}
}

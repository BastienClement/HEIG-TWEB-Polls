package models

import play.api.libs.json.Json

case class Poll(question: String, answers: Seq[String], correct: Int)

object Poll {
	implicit val format = Json.format[Poll]
}

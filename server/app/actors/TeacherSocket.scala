package actors

import akka.actor.{Actor, ActorRef}
import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import com.google.inject.name.Named
import models.Poll
import socket.Message

class TeacherSocket @Inject() (@Assisted out: ActorRef)
                              (@Named("rooms-manager") rm: ActorRef)
		extends Actor with PollActor {
	/** The room actor */
	var room: ActorRef = null

	/** Handle initialization message */
	def receive: Receive = {
		case RoomsManager.RoomCreated(roomId, roomActor) =>
			room = roomActor
			out ! Message("ROOM_READY", roomId)
			context.become(ready)
	}

	/** TODO */
	def ready: Receive = {
		// Starting a poll
		case Message("begin", question) => room ! Room.BeginPoll(question.as[Poll])

		// Closing a poll and display results
		case Message("close", _) => room ! Room.ClosePoll

		// Ending a poll, hiding the last poll card
		case Message("end", _) => room ! Room.EndPoll

		// The room status was updated
		case Room.StateUpdated(question) => out ! Message("STATE_UPDATED", question)

		// The number of connected students was updated
		case Room.StudentsCountUpdated(count) => out ! Message("STUDENTS_COUNT_UPDATED", count)

		case other => println("Unsupported message: " + other.toString)
	}

	/** Registers the room with the rooms manager */
	override def preStart(): Unit = {
		out ! Message("ROOM_INITIALIZING")
		rm ! RoomsManager.CreateRoom
	}

	/** Closes the room when the owner disconnects */
	override def postStop(): Unit = {
		if (room != null) room ! Room.Destroy
	}
}

object TeacherSocket {
	trait Factory {
		def apply(out: ActorRef): Actor
	}
}

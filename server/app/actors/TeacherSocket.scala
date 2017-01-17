package actors

import akka.actor.{Actor, ActorRef}
import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import com.google.inject.name.Named
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
		case foo => out ! foo
	}

	/** Registers the room with the rooms manager */
	override def preStart(): Unit = {
		out ! Message("ROOM_INITIALIZING")
		rm ! RoomsManager.CreateRoom(self)
	}

	/** Closes the room when the owner disconnects */
	override def postStop(): Unit = {
		if (room != null) room ! Room.Close
	}
}

object TeacherSocket {
	trait Factory {
		def apply(out: ActorRef): Actor
	}
}

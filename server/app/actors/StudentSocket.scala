package actors

import akka.actor.{Actor, ActorRef, PoisonPill}
import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import com.google.inject.name.Named
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import socket.Message

/**
  * The student-side web socket handler
  *
  * @param out    the outgoing socket actor
  * @param roomId the poll room to join
  * @param rm     the RoomsManager actor
  */
class StudentSocket @Inject() (@Assisted out: ActorRef, @Assisted roomId: String)
                              (@Named("rooms-manager") rm: ActorRef)
		extends Actor with PollActor {
	/** The room actor */
	var room: ActorRef = null

	/** Initial message handler */
	def receive: Receive = {
		// Room found and ready to be joined
		case RoomsManager.RoomFound(roomActor) =>
			room = roomActor
			room ! Room.Join
			out ! Message("ROOM_JOINED")
			context.become(ready)

		// Room does not exists
		case RoomsManager.RoomNotFound =>
			out ! Message("ROOM_NOT_FOUND")
			// There seems to be a race condition when stopping the actor too fast, keeping the websocket open
			import context.dispatcher
			context.system.scheduler.scheduleOnce(Duration(500, TimeUnit.MILLISECONDS), self, PoisonPill)
	}

	/** Message handler */
	def ready: Receive = {
		// Voting
		case Message("vote", answer) => room ! Room.Vote(answer.as[Int])

		// The room status was updated
		case Room.StateUpdated(question) => out ! Message("STATE_UPDATED", question)

		// The room to which the student is connected is now closed
		case Room.Destroyed =>
			out ! Message("ROOM_DESTROYED")
			room = null
			context.stop(self)

		case other => println("Unsupported message: " + other.toString)
	}

	/** Performs room registration on connection */
	override def preStart(): Unit = {
		rm ! RoomsManager.SearchRoom(roomId)
	}

	/** Leaves the room on actor stop */
	override def postStop(): Unit = {
		if (room != null) room ! Room.Leave
	}
}

object StudentSocket {
	case class Init(room: String)

	trait Factory {
		def apply(out: ActorRef, id: String): Actor
	}
}

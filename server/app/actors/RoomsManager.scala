package actors

import akka.actor.{Actor, ActorRef, Props, Terminated}
import scala.annotation.tailrec
import scala.util.Random

class RoomsManager extends Actor {
	/** Random number generator for rooms id */
	private val random = new Random

	/** Available rooms */
	private var rooms = Map.empty[String, ActorRef]

	/** Generates a new room id */
	@tailrec
	private def nextId: String = {
		val high = random.nextInt(9) + 1
		val low = random.nextInt(100000)
		val id = (high * 100000 + low).toString
		if (rooms.get(id).isEmpty) id
		else nextId
	}

	/** Message handler */
	def receive: Receive = {
		// Room creation from the teacher
		case RoomsManager.CreateRoom(owner) =>
			val id = nextId
			val room = context.actorOf(Props(new Room(owner)))
			rooms += (id -> room)
			context.watch(room)
			sender() ! RoomsManager.RoomCreated(id, room)

		// Joining room as a student
		case RoomsManager.JoinRoom(id, client) =>
			rooms.get(id) match {
				case Some(room) => sender() ! RoomsManager.RoomJoined(room)
				case None => sender() ! RoomsManager.RoomNotFound
			}

		// Room closed
		case Terminated(room) =>
			rooms.find { case (_, r) => r == room }.foreach { case (id, _) => rooms -= id }
	}
}

object RoomsManager {
	case class CreateRoom(owner: ActorRef)
	case class RoomCreated(id: String, room: ActorRef)

	case class JoinRoom(id: String, client: ActorRef)
	case class RoomJoined(room: ActorRef)
	case object RoomNotFound
}

package actors

import akka.actor.{Actor, ActorRef, Props, Terminated}
import scala.annotation.tailrec
import scala.util.Random

class RoomsManager extends Actor {
	/** Random number generator for rooms id */
	private val random = new Random

	/** Available rooms */
	private var rooms: Map[String, ActorRef] = Map.empty

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
		case RoomsManager.CreateRoom =>
			val id = nextId
			val owner = sender()
			val room = context.actorOf(Props(new Room(owner)))
			rooms += (id -> room)
			context.watch(room)
			sender() ! RoomsManager.RoomCreated(id, room)

		// Joining room as a student
		case RoomsManager.SearchRoom(id) =>
			rooms.get(id) match {
				case Some(room) => sender() ! RoomsManager.RoomFound(room)
				case None => sender() ! RoomsManager.RoomNotFound
			}

		// Room closed
		case Terminated(room) =>
			rooms.find { case (_, r) => r == room }.foreach { case (id, _) => rooms -= id }
	}
}

object RoomsManager {
	/** Creates a new room with the sender as the owner */
	case object CreateRoom

	/** Response to the CreateRoom message */
	case class RoomCreated(id: String, room: ActorRef)

	/** Searches for a room with the given id */
	case class SearchRoom(id: String)

	/** Response to SearchRoom is the room was found */
	case class RoomFound(room: ActorRef)

	/** Response to SearchRoom is the room was not found */
	case object RoomNotFound
}

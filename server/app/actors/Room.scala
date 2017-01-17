package actors

import akka.actor.{Actor, ActorRef, PoisonPill, Terminated}

class Room (owner: ActorRef) extends Actor with PollActor {
	/** The set of connected students */
	var students = Set.empty[ActorRef]

	/** Helper for broadcasting */
	object broadcast {
		def ! (o: Any): Unit = for (s <- students) s ! o
	}

	/** Message handler */
	def receive: Receive = {
		case Room.Join(actor) =>
			students += actor
			updateStudentsCount()

		case Room.Close =>
			broadcast ! Room.Closed
			self ! PoisonPill

		case Terminated(actor) =>
			students -= actor
			updateStudentsCount()
	}

	/** Sends students count update notification to owner */
	def updateStudentsCount(): Unit = owner ! Room.StudentsUpdated(students.size)
}

object Room {
	// Students operations
	case class Join(actor: ActorRef)
	case class Leave(actor: ActorRef)

	// Outgoing events for owner
	case class StudentsUpdated(count: Int)

	// Room closing
	case object Close
	case object Closed
}

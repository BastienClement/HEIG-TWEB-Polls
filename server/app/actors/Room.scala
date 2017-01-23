package actors
import akka.actor.{Actor, ActorRef}
import models.{Poll, RoomState}

class Room (owner: ActorRef) extends Actor with PollActor {
	/** The set of connected students */
	var students: Set[ActorRef] = Set.empty

	/** The current poll in this room */
	var poll: Option[Poll] = None

	/** Responses to the current poll */
	var results: Array[Int] = Array.empty

	/** Whether the current poll is done */
	var done: Boolean = true

	/** The set of actors that have voted */
	var voters: Set[ActorRef] = Set.empty

	/** Helper for broadcasting */
	object broadcast {
		def ! (o: Any): Unit = for (s <- students) s ! o
	}

	/** Message handler */
	def receive: Receive = {
		case Room.Join =>
			students += sender()
			sender() ! Room.StateUpdated(currentState)
			updateStudentsCount()

		case Room.Leave =>
			students -= sender()
			updateStudentsCount()

		case Room.BeginPoll(pollQuestion) =>
			poll = Some(pollQuestion)
			results = Array.fill(pollQuestion.answers.length)(0)
			voters = Set.empty
			done = false
			broadcastRoomState()

		case Room.Vote(answer) =>
			if (poll.isDefined && !voters.contains(sender()) && answer >= 0 && answer < poll.get.answers.length) {
				voters += sender()
				results(answer) += 1
				broadcastRoomState()
			}

		case Room.ClosePoll =>
			done = true
			broadcastRoomState()

		case Room.EndPoll =>
			poll = None
			broadcastRoomState()

		case Room.Destroy =>
			broadcast ! Room.Destroyed
			context.stop(self)
	}

	/** Sends students count update notification to owner */
	def updateStudentsCount(): Unit = owner ! Room.StudentsCountUpdated(students.size)

	/** Computes the current state object for this room */
	def currentState: RoomState = RoomState(
		poll = poll,
		results = if (poll.isDefined) Some(results) else None,
		done = if (poll.isDefined) done else true
	)

	/** Sends room state update notification to teacher and students */
	def broadcastRoomState(): Unit = {
		val msg = Room.StateUpdated(currentState)
		owner ! msg
		broadcast ! msg
	}
}

object Room {
	//
	// Owner operations
	//
	/** Begins a new poll with the given question */
	case class BeginPoll(poll: Poll)

	/** Stops the current poll and display results */
	case object ClosePoll

	/** Closes the current poll */
	case object EndPoll

	/** Closes the room when the teacher disconnects */
	case object Destroy

	//
	// Outgoing events for owner
	//
	/** Updates of the number of online students */
	case class StudentsCountUpdated(count: Int)

	/** Updates of the number of responses received */
	case class ResponsesCountUpdated(count: Int)

	//
	// Students operations
	//
	/** Joining the room */
	case object Join

	/** Leaving the room */
	case object Leave

	/** Voting */
	case class Vote(answer: Int)

	//
	// Outgoing events for students
	//
	/** The active question of the room was changed */
	case class StateUpdated(state: RoomState)

	/** The room is now closed */
	case object Destroyed
}

import actors.{RoomsManager, StudentSocket, TeacherSocket}
import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

class Module extends AbstractModule with AkkaGuiceSupport {
	def configure(): Unit = {
		bindActor[RoomsManager]("rooms-manager")
		bindActorFactory[TeacherSocket, TeacherSocket.Factory]
		bindActorFactory[StudentSocket, StudentSocket.Factory]
	}
}

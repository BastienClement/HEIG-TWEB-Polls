import {Injectable} from "@angular/core";
import {ReplaySubject} from "rxjs/ReplaySubject";
import {Observable} from "rxjs/Observable";
import {Poll} from "../models/poll";
import {TwollSocket} from "../utils/twollsocket";
import {RoomState} from "../models/roomstate";

@Injectable()
export class TeacherService {
	private socket: TwollSocket;

	public roomId = new ReplaySubject<string>(1);
	public onlines = new ReplaySubject<number>(1);
	public quickJoinURL: string;
	public editing: string;
	public state: RoomState = new RoomState();
	public polls: Array<Poll> = [];

	constructor() {
		this.onlines.next(0);
		this.roomId.subscribe(id => this.quickJoinURL = `https://twoll.me/${id}`);

		this.polls.push(new Poll("How awesome is Twoll.me?", ["Super awesome!", "Quite awesome!", "Somewhat awesome!"]));

		this.socket = TwollSocket.open("/room");
		this.socket.stream.subscribe(msg => {
			switch (msg.label) {
				case "ROOM_READY":
					this.roomId.next(msg.payload);
					break;
				case "STUDENTS_COUNT_UPDATED":
					this.onlines.next(msg.payload);
					break;
				case "STATE_UPDATED":
					this.state = msg.payload;
					break;
				default:
					console.log(msg.label, msg.payload);
					break;
			}
		});
	}

	public createPoll(): void {
		const poll = new Poll("What is the question of this new poll?");
		this.polls.push(poll);
		this.editing = poll.id;
	}

	public startEditing(poll: Poll): void {
		this.editing = poll.id;
	}

	public stopEditing(poll: Poll): void {
		if (poll.id == this.editing) {
			this.editing = null;
		}
	}

	public deletePoll(poll: Poll): void {
		this.polls = this.polls.filter(p => p !== poll);
	}

	public startPoll(poll: Poll): void {
		this.socket.send("begin", poll);
	}

	public stopPoll(): void {
		this.socket.send("close");
	}

	public closePoll(): void {
		this.socket.send("end");
	}
}

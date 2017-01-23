import {Injectable} from "@angular/core";
import {ReplaySubject} from "rxjs/ReplaySubject";
import {Observable} from "rxjs/Observable";
import {Poll} from "../models/poll";
import {TwollSocket, TwollSocketMessage} from "../utils/twollsocket";

@Injectable()
export class TeacherService {
	private socket: TwollSocket;

	public roomId = new ReplaySubject<string>(1);
	public onlines = new ReplaySubject<number>(1);
	public quickJoinURL: string;
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
			}
		});
	}

	public createPoll(): void {
		this.polls.push(new Poll("What should this new poll's title be?"));
	}
}

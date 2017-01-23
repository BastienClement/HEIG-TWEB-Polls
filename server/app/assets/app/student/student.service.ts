import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {TwollSocket} from "../utils/twollsocket";
import {RoomState} from "../models/roomstate";

declare const ROOM_ID: string;

@Injectable()
export class StudentService {
	private socket: TwollSocket;
	public state: RoomState = new RoomState();
	public alive: boolean = true;
	public notFound: boolean = false;
	public closed: boolean = false;

	constructor() {
		this.socket = TwollSocket.open("/room/" + ROOM_ID);
		this.socket.stream.subscribe(msg => {
			switch (msg.label) {
				case "ROOM_NOT_FOUND":
					this.notFound = true;
					this.alive = false;
					this.state = new RoomState();
					break;
				case "ROOM_DESTROYED":
					this.closed = true;
					this.alive = false;
					this.state = new RoomState();
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

	public vote(choice: number): void {
		this.socket.send("vote", choice);
	}
}

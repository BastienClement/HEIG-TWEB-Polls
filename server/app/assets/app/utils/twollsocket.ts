import {Observable} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";

export class TwollSocket {
	static open(path: string): TwollSocket {
		const scheme = document.location.protocol == "http:" ? "ws://" : "wss://";
		const host = document.location.host;
		return new TwollSocket(scheme + host + path);
	}

	private subject = new Subject();
	private socket: WebSocket;

	private constructor(url: string) {
		this.socket = new WebSocket(url);
		this.socket.onmessage = (msg: MessageEvent) => {
			const data = JSON.parse(msg.data);
			this.subject.next(data);
		};
		this.socket.onclose = () => this.subject.complete();
	}

	public get stream(): Observable<TwollSocketMessage> {
		return this.subject.asObservable();
	}
}

export interface TwollSocketMessage {
	label: string;
	payload: any;
}

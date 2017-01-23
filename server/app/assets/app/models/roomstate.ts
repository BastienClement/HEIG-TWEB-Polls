import {Poll} from "./poll";

export class RoomState {
	constructor(public poll: Poll = null,
	            public results: Array<number> = [],
	            public done: boolean = true) {
	}
}

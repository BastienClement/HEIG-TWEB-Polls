export class Poll {
	constructor(public question: String = "",
	            public answers: Array<String> = ["", ""],
	            public correct: number = 0) {
	}
}

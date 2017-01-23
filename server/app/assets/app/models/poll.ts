export class Poll {
	public id: string = Poll.generateId();
	constructor(public question: string = "",
	            public answers: Array<string> = ["", ""],
	            public correct: number = 0) {
	}

	private static generateId() {
		function s4() {
			return Math.floor((1 + Math.random()) * 0x10000)
				.toString(16)
				.substring(1);
		}
		return s4() + s4() + "-" + s4() + "-" + s4() + "-" +
			s4() + "-" + s4() + s4() + s4();
	}
}

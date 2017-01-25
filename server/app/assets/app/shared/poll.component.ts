import {Component, Input, Output, EventEmitter} from "@angular/core";
import {Poll} from "../models/poll";
import {RoomState} from "../models/roomstate";

@Component({
	selector: "twoll-poll",
	templateUrl: "assets/app/shared/poll.component.html",
	styleUrls: ['assets/app/shared/poll.component.css'],
})
export class PollComponent {
	@Input() poll: Poll;
	@Input() editable: boolean;
	@Input() editing: boolean;
	@Input() state: RoomState;
	@Input() error: string;

	@Output() onEdit = new EventEmitter<void>();
	@Output() onSave = new EventEmitter<void>();
	@Output() onDelete = new EventEmitter<void>();
	@Output() onStart = new EventEmitter<void>();
	@Output() onStop = new EventEmitter<void>();
	@Output() onClose = new EventEmitter<void>();
	@Output() onSubmit = new EventEmitter<number>();

	public answer: number = -1;
	public submitted: string = "";

	constructor() {
	}

	public edit(): void {
		this.onEdit.emit();
	}

	public save(): void {
		if(this.poll.question.length == 0){
			this.error = "Question can not be empty";
		}
		else if(this.poll.answers.length == 0){
			this.error = "A question must at least have one answer";
		}
		else if(this.poll.answers.indexOf("") != -1){
			this.error = "No answer can be empty";
		}
		else{
			this.error = null;
			this.onSave.emit();
		}
	}

	public deletePoll(): void {
		if(confirm("Are you sure?")) {
			this.onDelete.emit();
		}
	}

	public start(): void {
		this.onStart.emit();
	}

	public stop(): void {
		this.error = null;
		this.onStop.emit();
	}

	public close(): void {
		this.error = null;
		this.onClose.emit();
	}

	public submit(): void {
		if(this.answer != -1) {
			this.error = null;
			this.onSubmit.emit(this.answer);
			this.submitted = this.poll.id;
		}
		else{
			this.error = "You must choose an answer";
		}
	}

	public identify(index: number): number {
		return index;
	}

	public deleteAnswer(index: number): void {
		if(this.poll.answers.length > 1) {
			this.poll.answers.splice(index, 1);
		}
	}

	public createAnswer(): void {
		this.poll.answers.push("");
	}

	public active(): boolean {
		return this.state.poll && this.state.poll.id == this.poll.id;
	}

	public receivedAnswers(): number {
		return this.state.results.reduce((a, b) => a + b);
	}
}

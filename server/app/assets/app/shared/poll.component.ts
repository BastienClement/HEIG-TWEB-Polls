import {Component, Input} from "@angular/core";
import {Poll} from "../models/poll";

@Component({
	selector: "twoll-poll",
	templateUrl: "assets/app/shared/poll.component.html",
	styleUrls: ['assets/app/shared/poll.component.css'],
})
export class PollComponent {
	@Input() poll: Poll;
	@Input() index: number;

	constructor() {
	}
}

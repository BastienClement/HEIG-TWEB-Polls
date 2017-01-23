import {Component} from "@angular/core";
import {TeacherService} from "./teacher.service";

@Component({
	selector: "twoll-poll-list",
	template: `
		<twoll-poll *ngFor="let poll of service.polls;"
			[poll]="poll" [editable]="true" [editing]="service.editing == poll.id" [state]="service.state"
			(onEdit)="service.startEditing(poll)" (onSave)="service.stopEditing(poll)" (onDelete)="service.deletePoll(poll)"
			(onStart)="service.startPoll(poll)" (onStop)="service.stopPoll()" (onClose)="service.closePoll()"></twoll-poll>
		<md-card id="new-card"></md-card>
		<div id="new-button">
			<button md-fab (click)="service.createPoll()"><i class="material-icons">add</i></button>
		</div>
	`,
	styles: [`
		:host {
			display: block;
		}
		
		twoll-poll {
			margin: 10px 0;
		}
		
		#new-card {
			opacity: 0.5;
		}
		
		#new-button {
			text-align: center;
			margin: -25px 0 50px 0;
		}
	`],
})
export class PollListComponent {
	constructor(public service: TeacherService) {
	}
}

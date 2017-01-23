import {Component} from "@angular/core";
import {StudentService} from "./student.service";

@Component({
	selector: "student-app",
	template: `
		<div id="header">
			<h1>Twoll.me</h1>
		</div>
		<twoll-poll *ngIf="service.state.poll"
			[poll]="service.state.poll" [editable]="false" [state]="service.state"
			(onSubmit)="vote($event)"></twoll-poll>
		<md-card *ngIf="!service.state.poll && service.alive">
			There is no poll open right now!
		</md-card>
		<md-card *ngIf="service.notFound">
			Room not found
		</md-card>
		<md-card *ngIf="service.closed">
			Room closed
		</md-card>
	`,
	styles: [`
		#header {
			background: #e97770 url("/assets/images/overlay.png");
			padding: 30px 0 50px 0;
			color: #fff;
		}
		
		h1 {
			font-size: 24p;
			text-align: center;
		}
		
		twoll-poll, md-card {
			width: 500px;
			margin: -30px auto 50px auto;
		}
		
		md-card {
			padding: 100px 0;
			text-align: center;
			color: #999;
			font-weight: 300;
		}
	`],
})
export class StudentComponent {
	constructor(public service: StudentService) {
	}

	public vote(choice: number): void {
		this.service.vote(choice);
	}
}

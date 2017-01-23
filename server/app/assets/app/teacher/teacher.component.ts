import {Component} from "@angular/core";
import {TeacherService} from "./teacher.service";

@Component({
	selector: "teacher-app",
	template: `
		<twoll-header></twoll-header>
		<twoll-poll-list></twoll-poll-list>
	`,
	styles: [`
		twoll-poll-list {
			max-width: 700px;
			min-width: 400px;
			padding: 10px;
			margin: auto;
			margin-top: -40px;
		}
	`],
})
export class TeacherComponent {
	constructor(public service: TeacherService) {

	}

	public title: String = "Teacher App"
}

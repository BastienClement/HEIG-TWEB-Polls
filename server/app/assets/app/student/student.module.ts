import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {MaterialModule} from "@angular/material";
import {PollComponent} from "../shared/poll.component";
import {StudentService} from "./student.service";
import {StudentComponent} from "./student.component";

@NgModule({
	imports: [
		BrowserModule,
		FormsModule,
		MaterialModule.forRoot()
	],
	declarations: [
		PollComponent,
		StudentComponent
	],
	providers: [StudentService],
	bootstrap: [StudentComponent]
})
export class StudentModule {
}

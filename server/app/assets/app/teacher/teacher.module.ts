import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {MaterialModule} from "@angular/material";
import {TeacherComponent} from "./teacher.component";
import {TeacherService} from "./teacher.service";
import {PollComponent} from "../shared/poll.component";
import {PollListComponent} from "./poll-list.component";
import {HeaderComponent, HeaderQRCodeComponent} from "./header.component";

@NgModule({
	imports: [
		BrowserModule,
		MaterialModule.forRoot()
	],
	declarations: [
		PollComponent,
		TeacherComponent,
		HeaderComponent,
		HeaderQRCodeComponent,
		PollListComponent
	],
	entryComponents: [
		HeaderQRCodeComponent
	],
	providers: [TeacherService],
	bootstrap: [TeacherComponent]
})
export class TeacherModule {
}

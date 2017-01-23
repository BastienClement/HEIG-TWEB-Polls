import {Component, Input} from "@angular/core";
import {MdDialog, MdDialogRef} from '@angular/material';
import {Observable} from "rxjs/Observable";
import {TeacherService} from "./teacher.service";

@Component({
	selector: "twoll-header",
	templateUrl: "assets/app/teacher/header.component.html",
	styleUrls: ["assets/app/teacher/header.component.css"],
})
export class HeaderComponent {
	constructor(public service: TeacherService, public dialog: MdDialog) {
	}

	public openQR(id: number): void {
		const ref = this.dialog.open(HeaderQRCodeComponent, {
			height: "552px",
			width: "552px"
		});
		ref.componentInstance.url = "https://chart.googleapis.com/chart?cht=qr&chs=500x500&chl=" + this.service.quickJoinURL;
		ref.componentInstance.visible = true;
	}
}

@Component({
	selector: "twoll-header-qr",
	template: `<img *ngIf="visible" src="{{url}}">`,
})
export class HeaderQRCodeComponent {
	@Input() url: string;
	@Input() visible: boolean = false;
}

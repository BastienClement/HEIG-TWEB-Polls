import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {TeacherModule} from "./teacher/teacher.module";

const platform = platformBrowserDynamic();

export function bootstrapTeacher() {
	platform.bootstrapModule(TeacherModule);
}

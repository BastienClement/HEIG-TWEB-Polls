import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";
import {TeacherModule} from "./teacher/teacher.module";
import {StudentModule} from "./student/student.module";

const platform = platformBrowserDynamic();

export function bootstrapTeacher() {
	platform.bootstrapModule(TeacherModule);
}

export function bootstrapStudent() {
	platform.bootstrapModule(StudentModule);
}

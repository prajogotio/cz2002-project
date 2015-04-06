package rep.scrame.view;

import rep.scrame.controller.InformationManager;
import rep.scrame.model.Course;
import rep.scrame.model.Faculty;

public class FacultyInformationView implements ScrameView {

	private Faculty faculty;
	
	public FacultyInformationView(Faculty faculty) {
		this.faculty = faculty;
	}
	
	@Override
	public void display() {
		System.out.println();
        System.out.println("Faculty Name    : " + faculty.getName());
        System.out.println("List of courses : ");
        System.out.println();
        System.out.println("      id  Course Code  Title");
        System.out.println("    ---------------------------------------------------------------------------");
        for (Course course : faculty.getCourses()) {
            System.out.format("    %4d  %-10s  %-50s\n", InformationManager.getInstance().getId(course), course.getCourseCode(), course.getName());
        }
        System.out.println();
	}
}

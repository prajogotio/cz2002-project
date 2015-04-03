package rep.scrame.view;

import java.util.ArrayList;

import rep.scrame.controller.InformationManager;
import rep.scrame.model.Course;

public class CourseListView implements ScrameView{

	public CourseListView() {}
	
	@Override
	public void display() {
		System.out.println();
        int id = 0;
        ArrayList<Course> courses = InformationManager.getInstance().getCourses();

        System.out.println("  id  Code        Title                                             AU  capacity     Faculty");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Course course : courses) {
            String facultyName = "";
            if(course.getFaculty() != null) facultyName = course.getFaculty().getName();
            System.out.format("%4d  %-10s %-50s  %-4d  %-4d     %-51s\n", id++, course.getCourseCode(), course.getName(), course.getAcademicUnit(), course.getCapacity(), facultyName);
        }
	}

}

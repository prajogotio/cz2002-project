package rep.scrame.view;

import java.util.ArrayList;

import rep.scrame.controller.InformationManager;
import rep.scrame.model.Course;

/**
 *	The course list view class. Manages the table view of all courses.
 */
public class CourseListView extends ListView{
	/**
	 * CourseListView constructor.
	 */
	public CourseListView() {super(InformationManager.getInstance().getCourses());}
	
	/*
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
	*/
	
	@Override
	protected String formatHeader(){
		String result = String.format("  id  Code        Title                                             AU  capacity     Faculty\n"
				+ "----------------------------------------------------------------------------------------------------------------------------------------------------");
		return result;
	}
	
	@Override
	protected String formatLine(){
		ArrayList<Course> courses = InformationManager.getInstance().getCourses();
		String facultyName = "";
		Course course = courses.get(currentIndex);
        if(course.getFaculty() != null) facultyName = course.getFaculty().getName();
        String result = String.format("%4d  %-10s %-50s  %-4d  %-4d     %-51s", currentIndex, course.getCourseCode(), course.getName(), course.getAcademicUnit(), course.getCapacity(), facultyName);
        return result;
	}


}

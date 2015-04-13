package rep.scrame.view;

import java.util.ArrayList;

import rep.scrame.controller.InformationManager;
import rep.scrame.model.CourseSession;
import rep.scrame.model.Student;

/**
 * Displays the session student list in a certain format.
 */
public class SessionStudentList implements ScrameView{
	/**
	 * The course session which students are going to be listed down.
	 */
	private CourseSession courseSession;
	
	/**
	 * The constructor of SessionStudentList.
	 * @param courseSession	The course session which students are going to be listed down.
	 */
	public SessionStudentList(CourseSession courseSession) {
		this.courseSession = courseSession;
	}
	
	@Override
	public void display() {
		System.out.println();
        System.out.println("Session name            : " + courseSession.getName());
        System.out.println("Session venue           : " + courseSession.getLocation());
        System.out.println("Capacity                : " + courseSession.getCapacity());
        System.out.println("Total students enrolled : " + courseSession.getEnrolledStudents().size());
        System.out.println("Available slots         : " + (courseSession.getCapacity() - courseSession.getEnrolledStudents().size()));
        ArrayList<Student> students = courseSession.getEnrolledStudents();
        if (students.isEmpty()) {
            System.out.println("There are currently no students enrolled in this session.");
        } else {
            System.out.println("      Students enrolled in this session");
            System.out.println("  id  Name                                        Matric. no.           Faculty name");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
            for (Student student : students) {
                System.out.format("%4d  %-42s  %-20s  %-51s\n", InformationManager.getInstance().getId(student), student.getFirstName() + ", " +student.getLastName(), student.getMatriculationNumber(), (student.getFaculty() == null ? " " : student.getFaculty().getName()));
            }
        }
        System.out.println();
		
	}

}

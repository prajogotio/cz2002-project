package rep.scrame.view;

import java.util.ArrayList;

import rep.scrame.model.Course;
import rep.scrame.model.Student;

public class StudentInformationView implements ScrameView{
	private Student student;
	
	public StudentInformationView(Student student) {
		this.student = student;
	}
	
	@Override
	public void display() {
		System.out.println();
        System.out.println("First name          : " + student.getFirstName());
        System.out.println("Last name           : " + student.getLastName());
        System.out.println("Matriculation No.   : " + student.getMatriculationNumber());
        System.out.println("Faculty             : " + (student.getFaculty() != null ? student.getFaculty().getName() : ""));
        System.out.println("NRIC                : " + student.getNRIC());
        System.out.println("Date of Birth       : " + student.getDateOfBirthAsString());
        System.out.println("Date of Enrollment  : " + student.getDateOfEnrollmentAsString());
        System.out.println("Phone No.           : " + student.getPhoneNumber());
        System.out.println("Address             : " + student.getAddress());
        System.out.println("\nCourses Registered this Semester:");
        ArrayList<Course> courseRegistered = student.getCourseRegistered();
        if(!courseRegistered.isEmpty()) {
            System.out.println();
            for (Course course : courseRegistered) {
                System.out.println("   " + course.getCourseCode() + " - " + course.getName());
            }
            System.out.println();
        } else {
            System.out.println("    No course is registered for this semester.");
        }
	}
}

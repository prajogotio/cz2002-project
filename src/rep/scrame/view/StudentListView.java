package rep.scrame.view;

import java.util.ArrayList;

import rep.scrame.controller.InformationManager;
import rep.scrame.model.Student;

public class StudentListView extends ListView{

	public StudentListView() { super(InformationManager.getInstance().getStudents());}
	
	/*
	public void display() {
		System.out.println();
        ArrayList<Student> students = InformationManager.getInstance().getStudents();

        int id = 0;
        System.out.println("  id  matriculation no.     first name            last name            NRIC            faculty                                            date of enrollment");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Student student : students) {
            String facultyName = "";
            if(student.getFaculty() != null) facultyName = student.getFaculty().getName();
            System.out.format("%4d  %-20s  %-20s  %-20s %-15s %-51s  %-15s\n",id++, student.getMatriculationNumber(), student.getFirstName(), student.getLastName(), student.getNRIC(), facultyName, student.getDateOfEnrollmentAsString());
        }
	}
	*/

	@Override
	protected String formatHeader(){
		String result;
		result =  "  id  matriculation no.     first name            last name            NRIC            faculty                                            date of enrollment\n-------------------------------------------------------------------------------------------------------------------------------------------------------------";
	return result;
	}
	
	@Override
	protected String formatLine(){
		ArrayList<Student> students = InformationManager.getInstance().getStudents();
		 String facultyName = "";
         if(students.get(currentIndex).getFaculty() != null) facultyName = (students.get(currentIndex)).getFaculty().getName();
         String result;
         result = String.format("%4d  %-20s  %-20s  %-20s %-15s %-51s  %-15s",currentIndex, (students.get(currentIndex)).getMatriculationNumber(), (students.get(currentIndex)).getFirstName(), students.get(currentIndex).getLastName(), students.get(currentIndex).getNRIC(), facultyName, students.get(currentIndex).getDateOfEnrollmentAsString());
         return result;
	}

	
	
}

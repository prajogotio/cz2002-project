package rep.scrame.controller.command;

import java.util.StringTokenizer;

import rep.scrame.controller.InformationManager;
import rep.scrame.model.Course;
import rep.scrame.model.Faculty;
import rep.scrame.model.FacultyMember;
import rep.scrame.model.Student;
import rep.scrame.view.CourseInformationView;
import rep.scrame.view.FacultyInformationView;
import rep.scrame.view.FacultyMemberInformationView;
import rep.scrame.view.ScrameView;
import rep.scrame.view.StudentInformationView;

/**
 * Manages a detailed view of a particular entity once invoked.
 */
public class ViewCommand implements Command {


    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        if (!tokens.hasMoreTokens()) {
        	displayErrorMessage();
            return;
        }
        String domain = tokens.nextToken();
        if (!tokens.hasMoreTokens()) {
        	displayErrorMessage();
            return;
        }
        String id = tokens.nextToken();
        if (domain.equals("-s")) {
            viewStudentInformation(id);
        } else if (domain.equals("-c")) {
            viewCourseInformation(id);
        } else if (domain.equals("-fm")) {
            viewFacultymember(id);
        } else if (domain.equals("-f")) {
            viewFaculty(id);
        } else {
        	displayErrorMessage();
        }
    }

    /**
     * Displays an error message.
     */
    private void displayErrorMessage() {
        System.out.println("Command does not match any recognised use case: vw [-s | -c | -fm | -f] id");

    }
    
    /**
     * Views a student information.
     * @param idString	Id of the student.
     */
    private void viewStudentInformation(String idString) {
        int id = InformationManager.checkedParseInt(idString);
        Student student = InformationManager.getInstance().getStudentById(id);
        if(student != null) {
            ScrameView studentInfo = new StudentInformationView(student);
            studentInfo.display();
        } else {
            System.out.println("student id is not valid.");
        }
    }

    /**
     * Views a course information.
     * @param idString	Course id.
     */
    private void viewCourseInformation(String idString) {
        int id = InformationManager.checkedParseInt(idString);
        Course course = InformationManager.getInstance().getCourseById(id);
        if (course != null) {
            ScrameView courseInfo = new CourseInformationView(course);
            courseInfo.display();
        } else {
            System.out.println("course id is not valid.");
        }
    }

    /**
     * Views information regarding faculty member.
     * @param idString	Faculty member id.
     */
    private void viewFacultymember(String idString) {
        int id = InformationManager.checkedParseInt(idString);
        FacultyMember facultyMember = InformationManager.getInstance().getFacultyMemberById(id);
        if (facultyMember != null) {
            ScrameView facultyMemberView = new FacultyMemberInformationView(facultyMember);
            facultyMemberView.display();
        } else {
            System.out.println("faculty-member id is not valid.");
        }
    }

    /**
     * Views information regarding a faculty.
     * @param idString	Faculty id.
     */
    private void viewFaculty(String idString) {
        int id = InformationManager.checkedParseInt(idString);
        Faculty faculty = InformationManager.getInstance().getFacultyById(id);
        if(faculty != null) {
            ScrameView facultyView = new FacultyInformationView(faculty);
            facultyView.display();
        } else {
            System.out.println("faculty id is not valid.");
        }
    }

}
package rep.scrame.controller.command;

import java.util.Scanner;
import java.util.StringTokenizer;

import rep.scrame.controller.InformationManager;
import rep.scrame.controller.SystemScannerAdapter;
import rep.scrame.model.Course;
import rep.scrame.model.FacultyMember;



/**
 * An interface to interpret an add command.
 */
public class AddCommand implements Command {
	/**
	 * The CommandInterpreter context of this add command.
	 */
    private CommandInterpreter context;

    /**
     * Displays error message.
     */
    private void displayErrorMessage() {
        System.out.println("Command does not match any recognised use case: add [-s | -c | -fm]");
    }
    
  
    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        String domain;
        if(tokens.hasMoreTokens()) {
            domain = tokens.nextToken();
        } else {
        	displayErrorMessage();
            return;
        }
        this.context = context;

        if(domain.equals("-s")) {
            displayAddStudentDialog();
        } else if (domain.equals("-c")) {
            displayAddCourseDialog();
        } else if (domain.equals("-fm")) {
            displayAddFacultyMemberDialog();
        } else {
        	displayErrorMessage();
        }
    }

    /**
     * Displays add course dialog activity.
     */
    private void displayAddCourseDialog() {
        System.out.println();
        Scanner scanner = SystemScannerAdapter.getInstance();
        System.out.println("Adding new Course into SCRAME System. Please fill in all relevant information:");
        System.out.print("Course Title: ");
        String title = scanner.nextLine();
        System.out.print("Course Code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter the faculty for which the course belongs to\n");
        context.parseStringToCommand("ls -f");
        System.out.print("faculty id: ");
        String facultyId = scanner.nextLine();
        System.out.print("Academic Unit: ");
        String academicUnit = scanner.nextLine();
        System.out.print("Capacity: ");
        String capacity = scanner.nextLine();
        Course course = InformationManager.getInstance().createNewCourse(title, courseCode, facultyId, academicUnit, capacity, "", "", "", "", "", "", "");
        System.out.print("Course is successfully registered.\nDo you want to add the particulars regarding the course now? (y/n) ");
        String response = scanner.nextLine();
        if(response.toLowerCase().equals("y") || response.toLowerCase().equals("yes")) {
            context.parseStringToCommand("upd -c " + InformationManager.getInstance().getId(course));
        }
    }

    /**
     * Displays add student dialog activity.
     */
    private void displayAddStudentDialog() {
        System.out.println();
        Scanner scanner = SystemScannerAdapter.getInstance();
        System.out.println("Adding new Student into SCRAME System. Please fill in all relevant information:");
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("NRIC: ");
        String NRIC = scanner.nextLine();
        System.out.print("Date of birth (dd/mm/yyyy): ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Date of enrollment (mm/yyyy): ");
        String dateOfEnrollment = scanner.nextLine();
        System.out.print("Matriculation number: ");
        String matriculationNumber = scanner.nextLine();
        System.out.println("Enter the Faculty by id:");
        context.parseStringToCommand("ls -f");
        System.out.print("faculty id: ");
        String facultyId = scanner.nextLine();

        InformationManager.getInstance().createNewStudent(firstName, lastName, NRIC, dateOfBirth, address, phoneNumber, dateOfEnrollment, matriculationNumber, facultyId);
        System.out.println("Student added successfully.");
    }

    /**
     * Displays add faculty member dialog activity.
     */
    private void displayAddFacultyMemberDialog() {
        System.out.println();
        Scanner scanner = SystemScannerAdapter.getInstance();
        System.out.println("Adding new Faculty Member into SCRAME System. Please fill in all relevant information:");
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("NRIC: ");
        String NRIC = scanner.nextLine();
        System.out.print("Date of birth (dd/mm/yyyy): ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter the Faculty by id:");
        context.parseStringToCommand("ls -f");
        System.out.print("faculty id: ");
        String facultyId = scanner.nextLine();
        System.out.println("Enter member's position:");
        FacultyMember.STATUS[] statuses = FacultyMember.STATUS.values();
        int choice = 0;
        System.out.println("  id  Position");
        System.out.println("-----------------------------------");
        for (FacultyMember.STATUS status : statuses) {
            System.out.format("%4d  %s\n", choice++, status.toString());
        }
        System.out.print("position id: ");
        String status = scanner.nextLine();

        InformationManager.getInstance().createNewFacultyMember(firstName, lastName, NRIC, dateOfBirth, address, phoneNumber, facultyId, status);
        System.out.println("Faculty member added successfully.");
    }


}
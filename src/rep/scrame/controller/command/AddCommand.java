package rep.scrame.controller.command;

import java.util.Scanner;
import java.util.StringTokenizer;

import rep.scrame.controller.InformationManager;
import rep.scrame.controller.SystemScannerAdapter;
import rep.scrame.model.Course;
import rep.scrame.model.FacultyMember;




public class AddCommand implements Command {
    private CommandInterpreter context;

    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        String domain;
        if(tokens.hasMoreTokens()) {
            domain = tokens.nextToken();
        } else {
            System.out.println("Command does not match any recognised use case: add [student | course | faculty-member]");
            return;
        }
        this.context = context;

        if(domain.equals("student")) {
            displayAddStudentDialog();
        } else if (domain.equals("course")) {
            displayAddCourseDialog();
        } else if (domain.equals("faculty-member")) {
            displayAddFacultyMemberDialog();
        } else {
            System.out.println("Command does not match any recognised use case: add [student | course | faculty-member]");
        }
    }

    private void displayAddCourseDialog() {
        System.out.println();
        Scanner scanner = SystemScannerAdapter.getInstance();
        System.out.println("Adding new Course into SCRAME System. Please fill in all relevant information:");
        System.out.print("Course Title: ");
        String title = scanner.nextLine();
        System.out.print("Course Code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter the faculty for which the course belongs to\n");
        context.parseStringToCommand("list faculties");
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
            context.parseStringToCommand("update course " + InformationManager.getInstance().getId(course));
        }
    }

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
        context.parseStringToCommand("list faculties");
        System.out.print("faculty id: ");
        String facultyId = scanner.nextLine();

        InformationManager.getInstance().createNewStudent(firstName, lastName, NRIC, dateOfBirth, address, phoneNumber, dateOfEnrollment, matriculationNumber, facultyId);
    }

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
        context.parseStringToCommand("list faculties");
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
    }


}
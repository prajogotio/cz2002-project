package rep.scrame.controller.command;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import rep.scrame.controller.InformationManager;
import rep.scrame.controller.SystemScannerAdapter;
import rep.scrame.model.Course;
import rep.scrame.model.CourseSession;
import rep.scrame.model.Student;
import rep.scrame.view.CourseStatistic;
import rep.scrame.view.ScrameView;
import rep.scrame.view.SessionStudentList;
import rep.scrame.view.StudentTranscript;


public class GetCommand implements Command {

    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        String command;
        if (tokens.hasMoreTokens()) {
            command = tokens.nextToken();
        } else {
            printErrorMessage();
            return;
        }
        if (command.equals("-t")) {
            getStudentTranscipt(context, tokens);
        } else if (command.equals("-s")) {
            getCourseInformation(context, tokens);
        } else {
            printErrorMessage();
        }
    }

    private void printErrorMessage() {
        System.out.println("Command does not match any recognised use case: get [ -t | -s] id");
    }


    private void getStudentTranscipt(CommandInterpreter context, StringTokenizer tokens) {
        String idString;
        if(tokens.hasMoreTokens()) {
            idString = tokens.nextToken();
        } else {
            printErrorMessage();
            return;
        }
        int id = InformationManager.checkedParseInt(idString);
        Student student = InformationManager.getInstance().getStudentById(id);
        if (student == null) {
            System.out.println("student id is not valid.");
            return;
        }
        ScrameView transcript = new StudentTranscript(student);
        transcript.display();
    }

    public void getCourseInformation(CommandInterpreter context, StringTokenizer tokens) {
        String idString;
        if(tokens.hasMoreTokens()) {
            idString = tokens.nextToken();
        } else {
            printErrorMessage();
            return;
        }
        int id = InformationManager.checkedParseInt(idString);
        Course course = InformationManager.getInstance().getCourseById(id);
        if (course == null) {
            System.out.println("course id is not valid.");
            return;
        }

        System.out.println("Choose one of the following options:");
        System.out.println("1. Student lists (enrollment and waitlist)" );
        System.out.println("2. List of students enrolled in the lecture session");
        System.out.println("3. List of students enrolled in a tutorial session");
        System.out.println("4. List of students enrolled in a lab session");
        System.out.println("5. Get course statistic");
        System.out.println("6. Cancel");
        System.out.print("choice number: ");
        Scanner scanner = SystemScannerAdapter.getInstance();
        String choice = scanner.nextLine();
        if(choice.equals("1")) {
        	displayStudentList(course);
    	} else if(choice.equals("2")) {
            CourseSession lecture = course.getLecture();
            displayStudentList(lecture);
        } else if (choice.equals("3")) {
            chooseCourseSession(course, "tutorial");
        } else if (choice.equals("4")) {
            chooseCourseSession(course, "lab");
        } else if (choice.equals("5")) {
        	displayCourseStatistic(course);
        }
    }
    
    private void displayStudentList(Course course) {
    	ArrayList<Student> enrolled = course.getEnrolledStudents();
    	ArrayList<Student> waitlist = course.getWaitList();
    	System.out.format("\n%s - %s\n", course.getCourseCode(), course.getName());
    	System.out.format("\nEnrolled students:            (size: %3d)\n", enrolled.size());
    	System.out.println("________________________________________");
    	System.out.println(" id    name");
    	System.out.println("----------------------------------------");
    	for (Student student : enrolled) {
    		System.out.format("%3d %-40s\n", InformationManager.getInstance().getId(student), student.getFirstName() + " " + student.getLastName());
    	}
    	
    	
    	System.out.format("\n\nStudents on waitlist:            (size: %3d)\n", waitlist.size());
    	System.out.println("________________________________________");
    	System.out.println(" id    name");
    	System.out.println("----------------------------------------");
    	for (Student student : waitlist) {
    		System.out.format("%3d %-40s\n", InformationManager.getInstance().getId(student), student.getFirstName() + " " + student.getLastName());
    	}
    	
    }

    private void chooseCourseSession(Course course, String type) {
        ArrayList<CourseSession> courseSessions = new ArrayList<CourseSession>();
        if (type.equals("tutorial")) {
            courseSessions = course.getTutorialGroups();
        } else if(type.equals("lab")) {
            courseSessions = course.getLabGroups();
        }
        System.out.println("Choose which session you want to display:");
        int numOfChoice = 1;
        for (CourseSession courseSession : courseSessions) {
            System.out.format("%d. %s\n", numOfChoice++, courseSession.getName());
        }
        System.out.println(""+ numOfChoice + ". Cancel");
        System.out.print("choice number: ");
        Scanner scanner = SystemScannerAdapter.getInstance();
        int choice = InformationManager.checkedParseInt(scanner.nextLine());
        if (choice == numOfChoice) {
            return;
        }
        if (1 <= choice && choice < numOfChoice) {
            displayStudentList(courseSessions.get(choice-1));
        } else {
            System.out.println("choice is not valid.");
        }
    }

    private void displayStudentList(CourseSession courseSession) {
        if(courseSession == null) {
            System.out.println("The course does not have the requested session.");
            return;
        }
        ScrameView studentList = new SessionStudentList(courseSession);
        studentList.display();
    }
    
    private void displayCourseStatistic(Course course) {
    	ScrameView courseStatistic = new CourseStatistic(course);
    	courseStatistic.display();
    }
}
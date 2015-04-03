package rep.scrame.controller.command;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import rep.scrame.controller.InformationManager;
import rep.scrame.controller.SystemScannerAdapter;
import rep.scrame.model.Assessment;
import rep.scrame.model.Course;
import rep.scrame.model.CourseGrading;
import rep.scrame.model.CourseSession;
import rep.scrame.model.MarkRecord;
import rep.scrame.model.Student;


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
        if (command.equals("student-transcript")) {
            getStudentTranscipt(context, tokens);
        } else if (command.equals("course-enrollment")) {
            getCourseInformation(context, tokens);
        } else {
            printErrorMessage();
        }
    }

    private void printErrorMessage() {
        System.out.println("Command does not match any recognised use case: get [ student-transcipt | course-enrollment] id");
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
        System.out.println("========================================================");
        System.out.println("                 Student Transcript");
        System.out.println("========================================================");
        System.out.println("Student name : " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Faculty      : " + (student.getFaculty() == null? "" : student.getFaculty().getName()));
        System.out.println();
        ArrayList<Course> courses = student.getCourseRegistered();
        for (Course course : courses) {
            CourseGrading courseGrading = course.getCourseGrading();
            if(courseGrading == null) continue;
            MarkRecord markRecord = courseGrading.getStudentMarkRecord(student);
            Assessment exam = courseGrading.getExam();
            ArrayList<Assessment> courseWork = courseGrading.getCourseWork();
            double examMark = markRecord.getAssessmentMark(exam);
            double overallMark = examMark * exam.getWeightage() / 100.0;
            double courseWorkOverallMark = 0;
            ArrayList<Double> courseWorkMarks = new ArrayList<Double>();
            for (Assessment assessment : courseWork) {
                courseWorkMarks.add(markRecord.getAssessmentMark(assessment));
                overallMark += courseWorkMarks.get(courseWorkMarks.size()-1) * assessment.getWeightage() * (100.0 - exam.getWeightage()) / 10000.0;
                courseWorkOverallMark += courseWorkMarks.get(courseWorkMarks.size()-1) * assessment.getWeightage() / 100.0;
            }
            System.out.println("Course title     : " + course.getName());
            System.out.println("Course code      : " + course.getCourseCode());
            System.out.format("Overall mark     : %.2f\n", overallMark);
            System.out.format("Exam mark        : %.2f\n", examMark);
            System.out.format("Coursework overall mark : %.2f\n", courseWorkOverallMark);
            if (courseWork.size() > 1) {
                System.out.println("Coursework mark break down:\n");
                for (int i = 0; i < courseWork.size(); ++i) {
                    System.out.format("    %-20s : %.2f\n", courseWork.get(i).getName(), courseWorkMarks.get(i));
                }
                System.out.println();
            }
            System.out.println();
        }
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
        System.out.println("1. List of students enrolled in the lecture session");
        System.out.println("2. List of students enrolled in a tutorial session");
        System.out.println("3. List of students enrolled in a lab session");
        System.out.println("4. Cancel");
        System.out.print("choice number: ");
        Scanner scanner = SystemScannerAdapter.getInstance();
        String choice = scanner.nextLine();
        if(choice.equals("1")) {
            CourseSession lecture = course.getLecture();
            displayStudentList(lecture);
        } else if (choice.equals("2")) {
            chooseCourseSession(course, "tutorial");
        } else if (choice.equals("3")) {
            chooseCourseSession(course, "lab");
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
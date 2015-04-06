package rep.scrame.controller.command;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

import rep.scrame.controller.DateAdapter;
import rep.scrame.controller.InformationManager;
import rep.scrame.controller.SystemScannerAdapter;
import rep.scrame.model.Assessment;
import rep.scrame.model.Course;
import rep.scrame.model.CourseGrading;
import rep.scrame.model.CourseSession;
import rep.scrame.model.FacultyMember;
import rep.scrame.model.Student;




public class UpdateCommand implements Command {

    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        if (!tokens.hasMoreTokens()) {
        	displayErrorMessage();
            return;
        }
        String domain = tokens.nextToken();
        if (domain.equals("-m")) {
            updateMarkRecord();
            return;
        }

        if (!tokens.hasMoreTokens()) {
        	displayErrorMessage();
            return;
        }
        String id = tokens.nextToken();
        if (domain.equals("-s")) {
            updateStudentInformationDialog(id);
        } else if (domain.equals("-c")) {
            updateCourseInformationDialog(id);
        } else {
        	displayErrorMessage();
        }
    }

    private void displayErrorMessage() {
        System.out.println("Command does not match any recognised use case: upd [-s | -c | -m] id");
    }
    
    private void updateStudentInformationDialog(String idString) {
        int id = InformationManager.checkedParseInt(idString);
        Student student = InformationManager.getInstance().getStudentById(id);
        if (student != null) {
        	boolean completed = false;
        	while (!completed) {
        		System.out.format("\nUpdate student information.\n");
        		System.out.format("Name       : %s %s\n", student.getFirstName(), student.getLastName());
        		System.out.format("Matric. No.: %s\n", student.getMatriculationNumber());
        		System.out.format("Choose the following options:\n");
        		System.out.println("1. Update address");
        		System.out.println("2. Update date of birth");
        		System.out.println("3. Update date of enrollment");
        		System.out.println("4. Update faculty");
        		System.out.println("5. Back");
        		Scanner scanner = SystemScannerAdapter.getInstance();
                String choice = scanner.nextLine();
                if (choice.equals("1")) {
                    updateStudentAddress(student);
                } else if (choice.equals("2")) {
                    updateStudentDateOfBirth(student);
                } else if (choice.equals("3")) {
                    updateStudentDateOfEnrollment(student);
                } else if (choice.equals("4")) {
                    updateStudentFaculty(student);
                } else {
                	break;
                }
        	}
        }
    }
    
    private void updateStudentAddress(Student student) {
    	System.out.format("Current addresss : %s\n", student.getAddress());
    	System.out.format("New address      : ");
    	Scanner scanner = SystemScannerAdapter.getInstance();
    	String input = scanner.nextLine();
    	student.setAddress(input);
    	System.out.println("Student address has been successfully updated.");
    }
    
    private void updateStudentDateOfBirth(Student student) {
    	System.out.format("Current date of birth            : %s\n", student.getDateOfBirthAsString());
    	System.out.format("Set date of birth to (dd/mm/yyyy): ");
    	Scanner scanner = SystemScannerAdapter.getInstance();
    	String input = scanner.nextLine();
    	Calendar newDate = DateAdapter.getCalendar(input);
    	student.setDateOfBirth(newDate.get(Calendar.DAY_OF_MONTH), newDate.get(Calendar.MONTH), newDate.get(Calendar.YEAR));
    	System.out.println("Student date of birth has been successfully updated.");
    }
    
    private void updateStudentDateOfEnrollment(Student student) {
    	System.out.format("Current date of enrollment            : %s\n", student.getDateOfEnrollmentAsString());
    	System.out.format("Set date of enrollment to (mm/yyyy)   : ");
    	Scanner scanner = SystemScannerAdapter.getInstance();
    	String input = scanner.nextLine();
    	Calendar newDate = DateAdapter.getCalendar("01/"+input);
    	student.setDateOfEnrollment(newDate);
    	System.out.println("Student date of enrollment has been successfully updated.");
    }

    private void updateStudentFaculty(Student student) {
    	System.out.format("Current faculty    : %s\n", student.getFaculty().getName());
    	System.out.format("Enter the faculty of student's new faculty:\n");
    	CommandInterpreter.getInstance().parseStringToCommand("list faculties");
    	System.out.format("Choice of faculty by id: ");
    	Scanner scanner = SystemScannerAdapter.getInstance();
    	String input = scanner.nextLine();
    	int id = InformationManager.checkedParseInt(input);
    	student.setFaculty(InformationManager.getInstance().getFacultyById(id));
    	System.out.println("Student faculty has been successfully updated.");
    }
    


    private void updateCourseInformationDialog(String idString) {
        int id = InformationManager.checkedParseInt(idString);
        Course course = InformationManager.getInstance().getCourseById(id);
        if (course != null) {
            boolean completed = false;
            while (!completed) {
                System.out.println();
                System.out.println(course.getCourseCode() + " - Update Course");
                System.out.println("Choose the following options:");
                System.out.println("1. Enroll student by student id");
                System.out.println("2. Add coordinator by coordinator id");
                System.out.println("3. Add/Update grading policy");
                System.out.println("4. Add/Update lecture");
                System.out.println("5. Add tutorial group");
                System.out.println("6. Add lab group");
                System.out.println("7. Back");
                System.out.print("Enter option number: ");
                Scanner scanner = SystemScannerAdapter.getInstance();
                String choice = scanner.nextLine();
                if (choice.equals("1")) {
                    enrollStudentIntoCourse(course);
                } else if (choice.equals("2")) {
                    addCoordinatorIntoCourse(course);
                } else if (choice.equals("3")) {
                    updateGradingPolicy(course);
                } else if (choice.equals("4")) {
                    addCourseSession("lecture", course);
                } else if (choice.equals("5")) {
                    addCourseSession("tutorial", course);
                } else if (choice.equals("6")) {
                    addCourseSession("lab", course);
                } else {
                    break;
                }
                completed = true;
                System.out.print("Continue updating the course? (y/n) ");
                choice = scanner.nextLine();
                if(choice.toLowerCase().equals("y") || choice.toLowerCase().equals("yes")) {
                    completed = false;
                }
            }
        } else {
            System.out.println("course id is not valid.");
        }
    }


    private void enrollStudentIntoCourse(Course course) {
        System.out.print("Enter student id: ");
        Scanner scanner = SystemScannerAdapter.getInstance();
        String idString = scanner.nextLine();
        int id = InformationManager.checkedParseInt(idString);
        Student student = InformationManager.getInstance().getStudentById(id);
        if (student != null) {
            if (course.getCapacity() > course.getEnrolledStudents().size()) {
                course.enrollStudent(student);
                System.out.format("%s %s has been successfully enrolled into %s.\n", student.getFirstName(), student.getLastName(), course.getName());
                enrollStudentIntoLectureGroup(student, course);
                enrollStudentIntoTutorialGroup(student, course);
                enrollStudentIntoLabGroup(student, course);
            } else {
                course.addToWaitList(student);
                System.out.format("%s is currently full. %s %s has been added to the waitlist.", course.getName(), student.getFirstName(), student.getLastName());
            }
        } else {
            System.out.println("student id is not valid.");
        }
    }

    private void enrollStudentIntoLectureGroup(Student student, Course course) {
        if(course.getLecture() != null) {
            course.getLecture().enrollStudent(student);
            System.out.format("%s %s is enrolled into the lecture group.\n", student.getFirstName(), student.getLastName());
        }
    }

    private void enrollStudentIntoTutorialGroup(Student student, Course course) {
        if(!course.getTutorialGroups().isEmpty()) {
            for (CourseSession courseSession : course.getTutorialGroups()) {
                if(courseSession.getCapacity() > courseSession.getEnrolledStudents().size()) {
                    courseSession.enrollStudent(student);
                    System.out.format("%s %s is enrolled into tutorial group %s\n", student.getFirstName(), student.getLastName(), courseSession.getName());
                    return;
                }
            }
        }
    }

    private void enrollStudentIntoLabGroup(Student student, Course course) {
        if(!course.getLabGroups().isEmpty()) {
            for (CourseSession courseSession : course.getLabGroups()) {
                if(courseSession.getCapacity() > courseSession.getEnrolledStudents().size()) {
                    courseSession.enrollStudent(student);
                    System.out.format("%s %s is enrolled into lab group %s\n", student.getFirstName(), student.getLastName(), courseSession.getName());
                    return;
                }
            }
        }
    }

    private void addCoordinatorIntoCourse(Course course) {
        Scanner scanner = SystemScannerAdapter.getInstance();
        System.out.print("Enter coordinator id: ");
        String idString = scanner.nextLine();
        int id = InformationManager.checkedParseInt(idString);
        FacultyMember facultyMember = InformationManager.getInstance().getFacultyMemberById(id);
        if (facultyMember != null) {
            course.addCoordinator(facultyMember);
            System.out.format("%s %s has been successfully added as a coordinator for %s\n", facultyMember.getFirstName(), facultyMember.getLastName(), course.getName());
        } else {
            System.out.println("faculty-member id is not valid.");
        }
    }


    private void updateGradingPolicy(Course course) {
        Scanner scanner = SystemScannerAdapter.getInstance();
        System.out.print("Exam weightage (%): ");
        String examWeightageString = scanner.nextLine();
        Assessment exam = InformationManager.getInstance().createNewAssessment("Exam", examWeightageString);
        int remainingWeightage = (int) (100 - exam.getWeightage());
        System.out.println("Coursework will have a weightage of " + remainingWeightage + "%");
        System.out.print("Do you want to add sub components to the coursework? (y/n) ");
        ArrayList<Assessment> courseWork = new ArrayList<Assessment>();
        String response = scanner.nextLine();
        if (response.toLowerCase().equals("y") || response.toLowerCase().equals("yes")) {
            boolean completed = false;
            remainingWeightage = 100;
            while (!completed) {
                System.out.print("Enter the name of the component: ");
                String name = scanner.nextLine();
                System.out.print("Enter the weightage of this component (out of "+ remainingWeightage +"%) : ");
                String weightage = scanner.nextLine();
                Assessment assessment = InformationManager.getInstance().createNewAssessment(name, weightage);
                courseWork.add(assessment);
                remainingWeightage -= (int) assessment.getWeightage();
                if (remainingWeightage <= 0) completed = true;
            }
        } else {
            courseWork.add(InformationManager.getInstance().createNewAssessment("Coursework", "100"));
        }
        CourseGrading courseGrading = InformationManager.getInstance().addNewCourseGrading(exam, courseWork);
        course.setCourseGrading(courseGrading);
        System.out.println("Grading Policy is successfully updated.");
    }

    private void addCourseSession(String type, Course course) {
        Scanner scanner = SystemScannerAdapter.getInstance();
        System.out.print("Enter " + type + " group name: ");
        String name = scanner.nextLine();
        System.out.print("Venue : ");
        String location = scanner.nextLine();
        System.out.print("Capacity: ");
        String capacity = scanner.nextLine();
        CourseSession courseSession = InformationManager.getInstance().createNewCourseSession(name, location, capacity);
        if (type.equals("lecture")) {
            course.setLecture(courseSession);
        } else if (type.equals("tutorial")) {
            course.getTutorialGroups().add(courseSession);
        } else if (type.equals("lab")) {
            course.getLabGroups().add(courseSession);
        }
        System.out.println(courseSession.getName() + " has been added successfully.");
    }

    private void updateMarkRecord() {
        Scanner scanner = SystemScannerAdapter.getInstance();
        System.out.println();
        System.out.println("Mark Entry Activity");
        System.out.println("Enter course id to which the mark will be entered.");
        System.out.print("course id: ");
        String courseId = scanner.nextLine();
        Course course = InformationManager.getInstance().getCourseById(InformationManager.checkedParseInt(courseId));
        if(course == null){
            System.out.println("course id is not valid.");
            return;
        }
        boolean completed = false;
        while(!completed) {
            System.out.println("Enter student id to which the mark will be awarded.");
            String studentId = scanner.nextLine();
            Student student = InformationManager.getInstance().getStudentById(InformationManager.checkedParseInt(studentId));
            if (student == null) {
                System.out.println("student id is not valid.");
                continue;
            } else if (!course.isStudentEnrolled(student)) {
                System.out.println("student is not enrolled in this course.");
                continue;
            }
            markEntryOfParticularStudent(course, student);
            completed = true;
            System.out.print("Do you want to update the mark of another student in this course? (y/n) ");
            String response = scanner.nextLine();
            if(response.toLowerCase().equals("yes") || response.toLowerCase().equals("y")) {
                completed = false;
            }
        }
    }

    private void markEntryOfParticularStudent(Course course, Student student) {
        Scanner scanner = SystemScannerAdapter.getInstance();
        System.out.println("Student name   : " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Course name    : " + course.getName());
        boolean completed = false;
        while(!completed) {
            System.out.println("Please choose the following option:");
            int numOfChoices = 1;
            if(course.getCourseGrading() != null) {
                if (course.getCourseGrading().getExam() != null) {
                    System.out.println("1. Key in Exam mark");
                    numOfChoices++;
                }
                if (!course.getCourseGrading().getCourseWork().isEmpty()) {
                    for (Assessment assessment : course.getCourseGrading().getCourseWork()) {
                        System.out.format("%d. Key in %s mark\n", numOfChoices++, assessment.getName());
                    }
                }
            }
            System.out.format("%d. Cancel\n", numOfChoices);
            System.out.print("choice number: ");
            String choice = scanner.nextLine();
            int choiceId = InformationManager.checkedParseInt(choice);
            if(choiceId > 0 && choiceId <= numOfChoices) {
                if (choiceId == numOfChoices) {
                    return;
                } else {
                    System.out.print("Enter mark (out of 100) : ");
                    String markString = scanner.nextLine();
                    double mark = InformationManager.checkedParseDouble(markString);
                    Assessment assessment;
                    if(choiceId == 1) {
                        assessment = course.getCourseGrading().getExam();
                    } else {
                        assessment = course.getCourseGrading().getCourseWork().get(choiceId-2);
                    }
                    course.getCourseGrading().getStudentMarkRecord(student).setAssessmentMark(assessment, mark);
                    System.out.println("Mark for " + assessment.getName() +" has been successfully recorded!");
                }
            } else {
                System.out.println("invalid choice.");
                continue;
            }

            completed = true;
            System.out.print("Continue mark entry for " + student.getFirstName() + " " + student.getLastName() + " in this course? (y/n)");
            String response = scanner.nextLine();
            if(response.toLowerCase().equals("y") || response.toLowerCase().equals("yes")) {
                completed = false;
            }
        }
    }
}
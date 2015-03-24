package rep.scrame.controller;

import rep.scrame.model.*;
import rep.scrame.view.Template;

import java.text.SimpleDateFormat;
import java.util.*;

public class CommandInterpreter {
    private static final CommandInterpreter INSTANCE = new CommandInterpreter();

    private Map<String, Command> commandLookUp;
    private AppController context;
    private Template template;

    public static CommandInterpreter getInstance() { return INSTANCE; }
    private CommandInterpreter() {
        context = null;
        template = new Template();

        initializeLookUpTable();
    }

    public void parseStringToCommand(String commandString) {
        StringTokenizer st = new StringTokenizer(commandString, " ");
        if(!st.hasMoreTokens()) {
            return;
        }
        String commandHead = st.nextToken();
        if(commandLookUp.containsKey(commandHead)) {
            commandLookUp.get(commandHead).invoke(this, st);
        } else {
            System.out.println(commandHead + " is not a recognised command. Enter \"help\" to check the list of recognised commands.");
        }
    }

    public void setApplicationContext(AppController context) {
        this.context = context;
    }

    public Template getTemplate() {
        return template;
    }

    public AppController getContext() {
        return context;
    }

    public void initializeLookUpTable() {
        commandLookUp = new HashMap<String, Command>();
        commandLookUp.put("display-home-screen", new DisplayHomeScreenCommand());
        commandLookUp.put("help", new HelpCommand());
        commandLookUp.put("quit", new QuitCommand());
        commandLookUp.put("list", new ListCommand());
        commandLookUp.put("add", new AddCommand());
        commandLookUp.put("view", new ViewCommand());
        commandLookUp.put("update", new UpdateCommand());
        commandLookUp.put("get", new GetCommand());
    }
}


interface Command {
    public void invoke(CommandInterpreter context, StringTokenizer tokens);
}


class DisplayHomeScreenCommand implements Command {
    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        context.getTemplate().printWelcomeScreen();
    }
}

class HelpCommand implements Command{
    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        context.getTemplate().printHelpScreen();
    }
}

class QuitCommand implements Command {
    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        System.out.println("Saving data...");
        context.getContext().stop();
        context.getTemplate().printLogOffScreen();
    }
}

class ListCommand implements Command {
    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        String domain;
        if(tokens.hasMoreTokens()) {
            domain = tokens.nextToken();
        } else {
            System.out.println("Command does not match any recognised use case: list [students | courses | faculties | faculty-members]");
            return;
        }

        if (domain.equals("students")) {
            displayStudents();
        } else if (domain.equals("courses")) {
            displayCourses();
        } else if (domain.equals("faculties")) {
            displayFaculties();
        } else if (domain.equals("faculty-members")) {
            displayFacultyMembers();
        } else {
            System.out.println("Command does not match any recognised use case: list [students | courses | faculties | faculty-members]");
        }
    }

    private void displayStudents() {
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

    private void displayFaculties() {
        System.out.println();
        ArrayList<Faculty> faculties = InformationManager.getInstance().getFaculties();
        int id = 0;
        System.out.println("  id                       Faculty Name");
        System.out.println("-----------------------------------------------------------------");
        for (Faculty faculty : faculties) {
            System.out.format("%4d    %s\n", id++, faculty.getName());
        }
    }

    private void displayFacultyMembers() {
        System.out.println();
        ArrayList<FacultyMember> facultyMembers = InformationManager.getInstance().getFacultyMembers();

        int id = 0;
        System.out.println("  id  first name            last name            NRIC            faculty                                              status");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
        for (FacultyMember facultyMember : facultyMembers) {
            String facultyName = "";
            if(facultyMember.getFaculty() != null) facultyName = facultyMember.getFaculty().getName();
            System.out.format("%4d  %-20s  %-20s %-15s %-51s  %-15s\n",id++, facultyMember.getFirstName(), facultyMember.getLastName(), facultyMember.getNRIC(), facultyName, facultyMember.getStatus());
        }
    }

    private void displayCourses() {
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
}

class AddCommand implements Command {
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

class ViewCommand implements Command {

    private StringTokenizer stringTokenizer;

    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        stringTokenizer = tokens;
        if (!tokens.hasMoreTokens()) {
            System.out.println("Command does not match any recognised use case: view [student | course | faculty-member | faculty] id");
            return;
        }
        String domain = tokens.nextToken();
        if (!tokens.hasMoreTokens()) {
            System.out.println("Command does not match any recognised use case: view [student | course | faculty-member | faculty] id");
            return;
        }
        String id = tokens.nextToken();
        if (domain.equals("student")) {
            viewStudentInformation(id);
        } else if (domain.equals("course")) {
            viewCourseInformation(id);
        } else if (domain.equals("faculty-member")) {
            viewFacultymember(id);
        } else if (domain.equals("faculty")) {
            viewFaculty(id);
        } else {
            System.out.println("Command does not match any recognised use case: view [student | course | faculty-member | faculty] id");
        }
    }

    private void viewStudentInformation(String idString) {
        int id = InformationManager.checkedParseInt(idString);
        Student student = InformationManager.getInstance().getStudentById(id);
        if(student != null) {
            System.out.println();
            System.out.println("First name : " + student.getFirstName());
            System.out.println("Last name  : " + student.getLastName());
            System.out.println("Matriculation No. : " + student.getMatriculationNumber());
            System.out.println("Faculty    : " + (student.getFaculty() != null ? student.getFaculty().getName() : ""));
            System.out.println("NRIC       : " + student.getNRIC());
            System.out.println("Date of Birth     : " + student.getDateOfEnrollmentAsString());
            System.out.println("Date of Enrollment: " + student.getDateOfEnrollmentAsString());
            System.out.println("Phone No.  : " + student.getPhoneNumber());
            System.out.println("Address    : " + student.getAddress());
            System.out.println("Courses Registered this Semester:");
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
        } else {
            System.out.println("student id is not valid.");
        }
    }

    private void viewCourseInformation(String idString) {
        int id = InformationManager.checkedParseInt(idString);
        Course course = InformationManager.getInstance().getCourseById(id);
        if (course != null) {
            System.out.println();
            System.out.println("Course Title : " + course.getName());
            System.out.println("Course Code  : " + course.getCourseCode());
            System.out.println("Capacity     : " + course.getCapacity());
            System.out.println("Total students enrolled     : " + course.getEnrolledStudents().size());
            System.out.println("Total available empty slots : " + (course.getCapacity() - course.getEnrolledStudents().size()));
            System.out.println("Total students on waitlist  : " + course.getWaitList().size());
            System.out.println("Course Grading Policy       :");
            if(course.getCourseGrading() != null) {
                System.out.println();
                System.out.println(" --- Exam        : " + (int) course.getCourseGrading().getExam().getWeightage() + "%");
                System.out.println(" --- Course work : " + (int) (100 - course.getCourseGrading().getExam().getWeightage()) + "%");
                if(course.getCourseGrading().getCourseWork().size() > 1) {
                    for (Assessment assessment : course.getCourseGrading().getCourseWork()) {
                        System.out.format("     --- %-20s : %2d%%\n", assessment.getName(), (int) assessment.getWeightage());
                    }
                }
                System.out.println();
            } else {
                System.out.println("    Course Grading Policy is not set yet");
            }
            System.out.println("Coordinators        :");
            if(!course.getCoordinators().isEmpty()) {
                System.out.println();
                System.out.println("      id  Name");
                System.out.println("    ------------------------------------------");
                for (FacultyMember coordinator : course.getCoordinators()) {
                    System.out.format("    %4d  %s, %s\n", InformationManager.getInstance().getId(coordinator), coordinator.getFirstName(), coordinator.getLastName());
                }
                System.out.println();
            } else {
                System.out.println("    No coordinator is added to this course yet.");
            }
            System.out.println("Lecture Information :");
            if(course.getLecture() != null) {
                System.out.println();
                System.out.println("    Venue : " + course.getLecture().getLocation());
                System.out.println();
            } else {
                System.out.println("    No lecture is set for the course yet.");
            }
            System.out.println("Tutorial Groups Information:");
            if(!course.getTutorialGroups().isEmpty()) {
                System.out.println();
                for(CourseSession tutorial : course.getTutorialGroups()) {
                    System.out.println("    Tutorial Group : " + tutorial.getName());
                    System.out.println("    Venue : " + tutorial.getLocation());
                    System.out.println("    Capacity: " + tutorial.getCapacity());
                    System.out.println("    Available Slot: " + (tutorial.getCapacity() - tutorial.getEnrolledStudents().size()));
                    System.out.println();
                }
            } else {
                System.out.println("    No tutorial group is added to this course yet.");
            }
            System.out.println("Lab Groups Information:");
            if(!course.getLabGroups().isEmpty()) {
                System.out.println();
                for(CourseSession lab : course.getLabGroups()) {
                    System.out.println("    Lab Group : " + lab.getName());
                    System.out.println("    Venue : " + lab.getLocation());
                    System.out.println("    Capacity: " + lab.getCapacity());
                    System.out.println("    Available Slot: " + (lab.getCapacity() - lab.getEnrolledStudents().size()));
                    System.out.println();
                }
            } else {
                System.out.println("    No lab group is added to this course yet.");
            }
        } else {
            System.out.println("course id is not valid.");
        }
    }

    private void viewFacultymember(String idString) {
        int id = InformationManager.checkedParseInt(idString);
        FacultyMember facultyMember = InformationManager.getInstance().getFacultyMemberById(id);
        if (facultyMember != null) {
            System.out.println();
            System.out.println("First name : " + facultyMember.getFirstName());
            System.out.println("Last name  : " + facultyMember.getLastName());
            System.out.println("Date of Birth : " + facultyMember.getDateOfBirthAsString());
            System.out.println("Rank       : " + facultyMember.getStatus());
            System.out.println("Faculty    : " + (facultyMember.getFaculty() != null ? facultyMember.getFaculty().getName() : ""));
            System.out.println("NRIC       : " + facultyMember.getNRIC());
            System.out.println("Phone No.  : " + facultyMember.getPhoneNumber());
            System.out.println("Address    : " + facultyMember.getAddress());
        } else {
            System.out.println("faculty-member id is not valid.");
        }
    }

    private void viewFaculty(String idString) {
        int id = InformationManager.checkedParseInt(idString);
        Faculty faculty = InformationManager.getInstance().getFacultyById(id);
        if(faculty != null) {
            System.out.println();
            System.out.println("Faculty Name    : " + faculty.getName());
            System.out.println("List of courses : ");
            System.out.println();
            System.out.println("      id  Course Code  Title");
            System.out.println("    ---------------------------------------------------------------------------");
            for (Course course : faculty.getCourses()) {
                System.out.format("    %4d  %-10s  %-50s\n", InformationManager.getInstance().getId(course), course.getCourseCode(), course.getName());
            }
            System.out.println();
        } else {
            System.out.println("faculty id is not valid.");
        }
    }

}

class UpdateCommand implements Command {

    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        if (!tokens.hasMoreTokens()) {
            System.out.println("Command does not match any recognised use case: update [student | course | faculty-member | mark] id");
            return;
        }
        String domain = tokens.nextToken();
        if (domain.equals("mark")) {
            updateMarkRecord();
            return;
        }

        if (!tokens.hasMoreTokens()) {
            System.out.println("Command does not match any recognised use case: update [student | course | faculty-member | mark] id");
            return;
        }
        String id = tokens.nextToken();
        if (domain.equals("student")) {
            updateStudentInformationDialog(id);
        } else if (domain.equals("course")) {
            updateCourseInformationDialog(id);
        } else if (domain.equals("faculty-member")) {
            updateFacultyMemberDialog(id);
        } else {
            System.out.println("Command does not match any recognised use case: update [student | course | faculty-member] id");
        }
    }

    private void updateStudentInformationDialog(String idString) {
        // not implemented yet
    }

    private void updateFacultyMemberDialog(String idString) {
        // not implemented yet
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
                System.out.println("7. Cancel");
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


class GetCommand implements Command {

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
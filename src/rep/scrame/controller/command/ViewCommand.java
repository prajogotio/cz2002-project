package rep.scrame.controller.command;

import java.util.ArrayList;
import java.util.StringTokenizer;

import rep.scrame.controller.InformationManager;
import rep.scrame.model.Assessment;
import rep.scrame.model.Course;
import rep.scrame.model.CourseSession;
import rep.scrame.model.Faculty;
import rep.scrame.model.FacultyMember;
import rep.scrame.model.Student;

public class ViewCommand implements Command {


    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
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
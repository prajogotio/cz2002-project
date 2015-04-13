package rep.scrame.view;

import rep.scrame.controller.InformationManager;
import rep.scrame.model.Assessment;
import rep.scrame.model.Course;
import rep.scrame.model.CourseSession;
import rep.scrame.model.FacultyMember;

/**
 * Course information view. Displays the detailed information of a course.
 */
public class CourseInformationView implements ScrameView{
	/**
	 * The course instance to be viewed.
	 */
	private Course course;
	
	/**
	 * CourseInformationView constructor.
	 * @param course	The course to be viewed.
	 */
	public CourseInformationView(Course course) {
		this.course = course;
	}
	
	@Override
	public void display() {
		System.out.println();
        System.out.println("Course Title                : " + course.getName());
        System.out.println("Course Code                 : " + course.getCourseCode());
        System.out.println("Capacity                    : " + course.getCapacity());
        System.out.println("Total students enrolled     : " + course.getEnrolledStudents().size());
        System.out.println("Total available empty slots : " + (course.getCapacity() - course.getEnrolledStudents().size()));
        System.out.println("Total students on waitlist  : " + course.getWaitList().size());
        System.out.println("\nCourse Grading Policy       :");
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
        System.out.println("\nCoordinators        :");
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
        System.out.println("\nLecture Information :");
        if(course.getLecture() != null) {
            System.out.println();
            System.out.println("    Venue : " + course.getLecture().getLocation());
            System.out.println();
        } else {
            System.out.println("    No lecture is set for the course yet.");
        }
        System.out.println("\nTutorial Groups Information:");
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
        System.out.println("\nLab Groups Information:");
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
	}
}

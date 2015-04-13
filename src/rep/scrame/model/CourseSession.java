package rep.scrame.model;

import java.util.ArrayList;

/**
 *	A course session, can be a lecture, a tutorial group or a lab group.
 */
public class CourseSession {

	/**
	 * The name of the session.
	 */
    private String name;
    
    /**
     * The official venue in which the session is going to be conducted.
     */
    private String location;
    
    /**
     * The course in which this session belongs to.
     */
    private Course course;
    
    /**
     * The sets of student enrolled into this particular session.
     */
    private ArrayList<Student> enrolledStudents;
    
    /**
     * The maximum capacity of this session.
     */
    private int capacity;

    
    /**
     * The constructor of CourseSession.
     * @param name		The name of the session.
     * @param location	The official venue.
     * @param capacity	The maximum capacity this session can accomodate.
     */
    public CourseSession(String name, String location, int capacity) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.course = null;
        enrolledStudents = new ArrayList<Student>();
    }

    /**
     * Sets the course for which this session belongs to.
     * @param course	The course to be set.
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Sets the maximum capacity of this course session.
     * @param capacity	The maximum capacity of this course session.
     */
    public void setCapacity(int capacity) {
        if(capacity < 0) capacity = 0;
        this.capacity = capacity;
    }

    /**
     * Retrieves the maximum capacity of this session.
     * @return	The maximum capacity of this session.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Check if a particular student is enrolled in this session.
     * @param student	The student to be checked
     * @return			True if the student is enrolled, false otherwise.
     */
    public boolean isStudentEnrolled(Student student) {
        return enrolledStudents.contains(student);
    }

    /**
     * Adds a student into this course session.
     * @param student	The student to be added.
     */
    public void enrollStudent(Student student) {
        if (capacity == enrolledStudents.size()) return;
        if (!isStudentEnrolled(student)) enrolledStudents.add(student);
    }

    /**
     * Removes the student from this course session.
     * @param student	The student to be removed.
     */
    public void unEnrollStudent(Student student) {
        enrolledStudents.remove(student);
    }

    /**
     * Retrieves the name of the course session.
     * @return The name of the course session.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this course session.
     * @param name	The desired name of the course session.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the venue where the session will be conducted
     * @return	The venue where the session will be conducted.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the venue where the session will be conducted.
     * @param location	The venue where the session will be conducted.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Retrieves the course in which this session belongs to.
     * @return	The course in which this session belongs to.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Retrieves the whole list of student enrolled in this session.
     * @return	The list of students enrolled.
     */
    public ArrayList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    /**
     * Sets the enrollment in this course session. Used for initialization of the course session.
     * @param enrolledStudents the enrollment list of this course session.
     */
    public void setEnrolledStudents(ArrayList<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }


}



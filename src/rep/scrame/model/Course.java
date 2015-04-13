package rep.scrame.model;


import java.util.ArrayList;

/**
 * A class that defines a course. This class stores and manages information regarding enrolled
 * students, students in waitlist, course grading policy, lecturers, and course sessions.
 *
 */
public class Course {
	/**
	 * The title of the course.
	 */
    private String name;
    
    /**
     * The course code, i.e. CZ2002.
     */
    private String courseCode;
    
    /**
     * The faculty in which this course belongs to.
     */
    private Faculty faculty;
    
    /**
     * The AU of this course.
     */
    private int academicUnit;
    
    /**
     * The maximum number of students allowed to enroll in this course.
     */
    private int capacity;
    
    /**
     * The list of students enrolled in this course
     */
    private ArrayList<Student> enrolledStudents;
    
    /**
     * The list of students in the waitlist, in case the course is full.
     */
    private ArrayList<Student> waitList;
    
    /**
     * The list of course coordinators.
     */
    private ArrayList<FacultyMember> coordinators;
    
    /**
     * The lecture session of this course.
     */
    private CourseSession lecture;
    
    /**
     * The tutorial sessions of this course.
     */
    private ArrayList<CourseSession> tutorialGroups;
    
    /**
     * The lab sessions of this course.
     */
    private ArrayList<CourseSession> labGroups;
    
    /**
     * The course grading policy.
     */
    private CourseGrading courseGrading;

    
    /**
     * Course constructor.
     * @param name			The name of the course.
     * @param courseCode	The course code.
     * @param capacity		The maximum capacity of the course. 
     * @param academicUnit	The AU assigned to this course.
     */
    public Course(String name, String courseCode, int capacity, int academicUnit) {
        this.name = name;
        this.capacity = capacity;
        this.courseCode = courseCode;
        this.academicUnit = academicUnit;
        faculty = null;
        enrolledStudents = new ArrayList<Student>();
        waitList = new ArrayList<Student>();
        coordinators = new ArrayList<FacultyMember>();
        courseGrading = new CourseGrading();
        lecture = null;
        tutorialGroups = new ArrayList<CourseSession>();
        labGroups = new ArrayList<CourseSession>();
    }

    
    /**
     * Sets the faculty in which this course belongs to.
     * @param faculty 	The faculty this course belongs to.
     */
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    /**
     * Retrieves the capacity of the course.
     * @return This course's capacity.
     */
    public int getCapacity() { return capacity; }

    /**
     * Sets the capacity of this course.
     * @param capacity The intended capacity.
     */
    public void setCapacity(int capacity) {
        if(capacity < 0) capacity = 0;
        this.capacity = capacity;
    }

    
    /**
     * Check if a particular student is enrolled in the course.
     * @param student	The student in question.
     * @return			True if student is enrolled, false otherwise.
     */
    public boolean isStudentEnrolled(Student student) {
        return enrolledStudents.contains(student);
    }

    /**
     * Check if a particular student is on waitlist.
     * @param student	The student in question.
     * @return			True if student is on waitlist, false otherwise.
     */
    public boolean isStudentOnWaitList(Student student) {
        return waitList.contains(student);
    }

    /**
     * Enrolls a student into the course.
     * @param student 	The student to enroll.
     */
    public void enrollStudent(Student student) {
        if(enrolledStudents.size() < capacity) {
            if (!isStudentEnrolled(student)) {
                enrolledStudents.add(student);
            }
        }
    }


    /**
     * Adds student into the course's waitlist.
     * @param student	The student to add.
     */
    public void addToWaitList(Student student) {
        if(!isStudentOnWaitList(student)) waitList.add(student);
    }

    /**
     * Removes a student from this course's enrollment list.
     * @param student	The student to remove.
     */
    public void unEnrollStudent(Student student) {
        enrolledStudents.remove(student);
    }

    /**
     * Removes a student from this course's waitlist.
     * @param student	The student to remove.
     */
    public void removeFromWaitList(Student student) {
        waitList.remove(student);
    }

    /**
     * Checks if a certain faculty member is conducting the course.
     * @param facultyMember	The faculty member to be checked.
     * @return		True if the faculty member is coordinating, false otherwise.
     */
    public boolean isCoordinating(FacultyMember facultyMember) {
        return coordinators.contains(facultyMember);
    }

    /**
     * Adds a faculty member as a coordinator
     * @param facultyMember		The faculty member to add.
     */
    public void addCoordinator(FacultyMember facultyMember) {
        if (!isCoordinating(facultyMember)) coordinators.add(facultyMember);
    }

    /**
     * Removes a coordinator from the coordinator list.
     * @param facultyMember		The coordinator to be removed.
     */
    public void removeCoordinator(FacultyMember facultyMember) {
        coordinators.remove(facultyMember);
    }

    /**
     * Retrieves the course grading policy of this course.
     * @return	The course grading policy of this course.
     */
    public CourseGrading getCourseGrading() {
        return courseGrading;
    }

    /**
     * Retrieves the academic unit of this course.
     * @return	The amount of AU awarded by this course.
     */
    public int getAcademicUnit() {
        return academicUnit;
    }

    /**
     * Sets the academic unit awarded by this course.
     * @param academicUnit	The intended academic unit.
     */
    public void setAcademicUnit(int academicUnit) {
        this.academicUnit = academicUnit;
    }

    /**
     * Retrieves the name of the course.
     * @return	The name of this course.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this course.
     * @param name	The desired name of this course.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the course code of this course.
     * @return	The course code of this course.
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Sets the course code of this course.
     * @param courseCode	The intended course code of this course.
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Retrieves the faculty in which this course belongs to.
     * @return	The faculty of this course.
     */
    public Faculty getFaculty() {
        return faculty;
    }

    /**
     * Retrieves the list of enrolled students.
     * @return	The list of the enrolled students.
     */
    public ArrayList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    /**
     * Retrieves the waitlist of the course.
     * @return	The waitlist of the course.
     */
    public ArrayList<Student> getWaitList() {
        return waitList;
    }

    /**
     * Retrieves the coordinator list.
     * @return	The coordinator list of this course.
     */
    public ArrayList<FacultyMember> getCoordinators() {
        return coordinators;
    }

    /**
     * Retrieves the lecture session of this course.
     * @return	The lecture session of this course.
     */
    public CourseSession getLecture() {
        return lecture;
    }

    /**
     * Sets the lecture session of this course.
     * @param lecture	The updated lecture session of this course.
     */
    public void setLecture(CourseSession lecture) {
        this.lecture = lecture;
    }

    /**
     * Retrieves the tutorial groups of this course.
     * @return	The tutorial groups of this course.
     */
    public ArrayList<CourseSession> getTutorialGroups() {
        return tutorialGroups;
    }

    /**
     * Retrieves the lab groups of this course.
     * @return	The lab groups of this course.
     */
    public ArrayList<CourseSession> getLabGroups() {
        return labGroups;
    }

    /**
     * Sets the course grading of this course.
     * @param courseGrading 	The course grading policy to be set.
     */
    public void setCourseGrading(CourseGrading courseGrading) {
        this.courseGrading = courseGrading;
    }

    /**
     * Sets the enrolled student list. Used for initialization this course.
     * @param enrolledStudents	The enrollment list of this course.
     */
    public void setEnrolledStudents(ArrayList<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    /**
     * Sets the waitlist of this course. Used for initialization of this course.
     * @param waitList	The waitlist of this course.
     */
    public void setWaitList(ArrayList<Student> waitList) {
        this.waitList = waitList;
    }

    /**
     * Sets the coordinator list of this course. Used for initialization of this course.
     * @param coordinators	The coordinators in this course.
     */
    public void setCoordinators(ArrayList<FacultyMember> coordinators) {
        this.coordinators = coordinators;
    }

    /**
     * Sets the tutorial groups for this course.
     * @param tutorialGroups	The tutorial groups.
     */
    public void setTutorialGroups(ArrayList<CourseSession> tutorialGroups) {
        this.tutorialGroups = tutorialGroups;
    }

    /**
     * Sets the lab groups for this course.
     * @param labGroups	The lab groups.
     */
    public void setLabGroups(ArrayList<CourseSession> labGroups) {
        this.labGroups = labGroups;
    }
}

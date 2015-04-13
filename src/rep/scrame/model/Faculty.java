package rep.scrame.model;


import java.util.ArrayList;

/**
 * The representation of a faculty. 
 */
public class Faculty {
	/**
	 * Name of the faculty.
	 */
    private String name;
    
    /**
     * List of students enrolled in the faculty.
     */
    private final ArrayList<Student> students;
    
    /**
     * List of faculty members.
     */
    private final ArrayList<FacultyMember> facultyMembers;
    
    /**
     * List of courses registered under the faculty.
     */
    private final ArrayList<Course> courses;

    /**
     * The constructor of Faculty class.
     * @param name	The name of the faculty.
     */
    public Faculty(String name) {
        this.name = name;
        students = new ArrayList<Student>();
        facultyMembers = new ArrayList<FacultyMember>();
        courses = new ArrayList<Course>();
    }

    /**
     * Adds a student into the faculty.
     * @param student	The student to be added.
     */
    public void addStudent(Student student) {
        if(!students.contains(student)) students.add(student);
    }

    /**
     * Add a faculty member into the faculty.
     * @param facultyMember	The faculty member to be added.
     */
    public void addFacultyMember(FacultyMember facultyMember) {
        if(!facultyMembers.contains(facultyMember))facultyMembers.add(facultyMember);
    }

    /**
     * Add a course into the faculty.
     * @param course	The course to be added.
     */
    public void addCourse(Course course) {
        if(!courses.contains(course)) courses.add(course);
    }

    /**
     * Retrieves the student lists of the faculty.
     * @return	List of students registered under the faculty.
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Retrieves the list of faculty members.
     * @return	List of faculty members registered under the faculty.
     */
    public ArrayList<FacultyMember> getFacultyMembers() {
        return facultyMembers;
    }

    /**
     * Retrieves the list of courses registered under the faculty.
     * @return 	The list of courses registered under the faculty.
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Retrieves the name of the faculty.
     * @return	The name of the faculty.
     */
    public String getName() {
        return name;
    }
}

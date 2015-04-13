package rep.scrame.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * The student class.
 */
public class Student extends Person{
	/**
	 * The matriculation number of the student.
	 */
    private String matriculationNumber;
    
    /**
     * The faculty in which the student belongs to.
     */
    private Faculty faculty;
    
    /**
     * The date of enrollment of the student.
     */
    private Calendar dateOfEnrollment;
    
    /**
     * The course registered to this student in this semester.
     */
    private ArrayList<Course> courseRegistered;

    /**
     * Student constructor.
     * @param particulars				The student particulars.
     * @param faculty					The faculty the student belongs to.	
     * @param matriculationNumber		The student's matriculation number.
     * @param dateOfEnrollment			The student's date of enrollment.
     */
    public Student(Particulars particulars, Faculty faculty, String matriculationNumber, Calendar dateOfEnrollment) {
        super(particulars);
        this.matriculationNumber = matriculationNumber;
        this.faculty = faculty;
        this.dateOfEnrollment = dateOfEnrollment;
        courseRegistered = new ArrayList<Course>();
    }

    /**
     * Sets student's matriculation number.
     * @param matriculationNumber	The matriculation number.
     */
    public void setMatriculationNumber(String matriculationNumber) {
        this.matriculationNumber = matriculationNumber;
    }

    /**
     * Gets the student's matriculation number.
     * @return	Student's matriculation number.
     */
    public String getMatriculationNumber() {
        return matriculationNumber;
    }

    /**
     * Gets this student's the date of enrollment.
     * @return	Student's date of enrollment.
     */
    public Calendar getDateOfEnrollment() {
        return dateOfEnrollment;
    }

    /**
     * Sets student's date of enrollment.
     * @param dateOfEnrollment	Student's date of enrollment.
     */
    public void setDateOfEnrollment(Calendar dateOfEnrollment) {
        this.dateOfEnrollment = dateOfEnrollment;
    }

    /**
     * Gets the student's faculty.
     * @return	Student's faculty.
     */
    public Faculty getFaculty() {
        return faculty;
    }

    /**
     * Sets the students' faculty.
     * @param faculty	Student's faculty.
     */
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    
    /**
     * Add course registered in this semester.
     * @param course	The course registered.
     */
    public void addCourseRegistered(Course course) { courseRegistered.add(course); }

    /**
     * Gets the list of course registered this semester.
     * @return	The list of course registered this semester.
     */
    public ArrayList<Course> getCourseRegistered() {
        return courseRegistered;
    }

    /**
     * Gets student's date of enrollment in MM/yyyy format.
     * @return	Student's date of enrollment in MM/yyyy format.
     */
    public String getDateOfEnrollmentAsString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/yyyy");
        String dateOfEnrollmentString = dateFormatter.format(dateOfEnrollment.getTime());
        return dateOfEnrollmentString;
    }
    
    /**
     * Sets student's date of enrollment.
     * @param date	Date from 1 to 31.
     * @param month	Month from 0 to 11.
     * @param year	Year in YYYY format.
     */
    public void setDateOfEnrollment(int date, int month, int year) {
    	dateOfEnrollment.set(year, month, date);
    }

}

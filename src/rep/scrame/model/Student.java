package rep.scrame.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Student extends Person{
    private String matriculationNumber;
    private Faculty faculty;
    private Calendar dateOfEnrollment;
    private ArrayList<Course> courseRegistered;

    public Student(Particulars particulars, Faculty faculty, String matriculationNumber, Calendar dateOfEnrollment) {
        super(particulars);
        this.matriculationNumber = matriculationNumber;
        this.faculty = faculty;
        this.dateOfEnrollment = dateOfEnrollment;
        courseRegistered = new ArrayList<Course>();
    }

    public void setMatriculationNumber(String matriculationNumber) {
        this.matriculationNumber = matriculationNumber;
    }

    public String getMatriculationNumber() {
        return matriculationNumber;
    }

    public Calendar getDateOfEnrollment() {
        return dateOfEnrollment;
    }

    public void setDateOfEnrollment(Calendar dateOfEnrollment) {
        this.dateOfEnrollment = dateOfEnrollment;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void addCourseRegistered(Course course) { courseRegistered.add(course); }

    public ArrayList<Course> getCourseRegistered() {
        return courseRegistered;
    }

    public String getDateOfEnrollmentAsString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/yyyy");
        String dateOfEnrollmentString = dateFormatter.format(dateOfEnrollment.getTime());
        return dateOfEnrollmentString;
    }

    public void setDateOfEnrollment(int date, int month, int year) {
    	dateOfEnrollment.set(year, month, date);
    }

}

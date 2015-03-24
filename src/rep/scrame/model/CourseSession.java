package rep.scrame.model;

import java.util.ArrayList;
import java.util.Calendar;

public class CourseSession {

    private String name;
    private String location;
    private Course course;
    private ArrayList<Student> enrolledStudents;
    private int capacity;

    public CourseSession(String name, String location, int capacity) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.course = null;
        enrolledStudents = new ArrayList<Student>();
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public void setCapacity(int capacity) {
        if(capacity < 0) capacity = 0;
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isStudentEnrolled(Student student) {
        return enrolledStudents.contains(student);
    }

    public void enrollStudent(Student student) {
        if (capacity == enrolledStudents.size()) return;
        if (!isStudentEnrolled(student)) enrolledStudents.add(student);
    }

    public void unEnrollStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Course getCourse() {
        return course;
    }


    public ArrayList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(ArrayList<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }


}



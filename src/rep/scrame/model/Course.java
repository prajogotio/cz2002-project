package rep.scrame.model;


import java.util.ArrayList;

public class Course {
    private String name;
    private String courseCode;
    private Faculty faculty;
    private int academicUnit;
    private int capacity;
    private ArrayList<Student> enrolledStudents;
    private ArrayList<Student> waitList;
    private ArrayList<FacultyMember> coordinators;
    private CourseSession lecture;
    private ArrayList<CourseSession> tutorialGroups;
    private ArrayList<CourseSession> labGroups;
    private CourseGrading courseGrading;

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

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public int getCapacity() { return capacity; }

    public void setCapacity(int capacity) {
        if(capacity < 0) capacity = 0;
        this.capacity = capacity;
    }

    public boolean isStudentEnrolled(Student student) {
        return enrolledStudents.contains(student);
    }

    public boolean isStudentOnWaitList(Student student) {
        return waitList.contains(student);
    }

    public void enrollStudent(Student student) {
        if(enrolledStudents.size() < capacity) {
            if (!isStudentEnrolled(student)) {
                enrolledStudents.add(student);
            }
        }
    }


    public void addToWaitList(Student student) {
        if(!isStudentOnWaitList(student)) waitList.add(student);
    }

    public void unEnrollStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public void removeFromWaitList(Student student) {
        waitList.remove(student);
    }

    public boolean isCoordinating(FacultyMember facultyMember) {
        return coordinators.contains(facultyMember);
    }

    public void addCoordinator(FacultyMember facultyMember) {
        if (!isCoordinating(facultyMember)) coordinators.add(facultyMember);
    }

    public void removeCoordinator(FacultyMember facultyMember) {
        coordinators.remove(facultyMember);
    }

    public CourseGrading getCourseGrading() {
        return courseGrading;
    }

    public int getAcademicUnit() {
        return academicUnit;
    }

    public void setAcademicUnit(int academicUnit) {
        this.academicUnit = academicUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public ArrayList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public ArrayList<Student> getWaitList() {
        return waitList;
    }

    public ArrayList<FacultyMember> getCoordinators() {
        return coordinators;
    }

    public CourseSession getLecture() {
        return lecture;
    }

    public void setLecture(CourseSession lecture) {
        this.lecture = lecture;
    }

    public ArrayList<CourseSession> getTutorialGroups() {
        return tutorialGroups;
    }

    public ArrayList<CourseSession> getLabGroups() {
        return labGroups;
    }

    public void setCourseGrading(CourseGrading courseGrading) {
        this.courseGrading = courseGrading;
    }

    public void setEnrolledStudents(ArrayList<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public void setWaitList(ArrayList<Student> waitList) {
        this.waitList = waitList;
    }

    public void setCoordinators(ArrayList<FacultyMember> coordinators) {
        this.coordinators = coordinators;
    }

    public void setTutorialGroups(ArrayList<CourseSession> tutorialGroups) {
        this.tutorialGroups = tutorialGroups;
    }

    public void setLabGroups(ArrayList<CourseSession> labGroups) {
        this.labGroups = labGroups;
    }
}

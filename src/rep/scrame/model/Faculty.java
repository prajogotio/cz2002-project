package rep.scrame.model;


import java.util.ArrayList;

public class Faculty {
    private String name;
    private final ArrayList<Student> students;
    private final ArrayList<FacultyMember> facultyMembers;
    private final ArrayList<Course> courses;

    public Faculty(String name) {
        this.name = name;
        students = new ArrayList<Student>();
        facultyMembers = new ArrayList<FacultyMember>();
        courses = new ArrayList<Course>();
    }

    public void addStudent(Student student) {
        if(!students.contains(student)) students.add(student);
    }

    public void addFacultyMember(FacultyMember facultyMember) {
        if(!facultyMembers.contains(facultyMember))facultyMembers.add(facultyMember);
    }

    public void addCourse(Course course) {
        if(!courses.contains(course)) courses.add(course);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<FacultyMember> getFacultyMembers() {
        return facultyMembers;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public String getName() {
        return name;
    }
}

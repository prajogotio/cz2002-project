package rep.scrame.controller;

import rep.scrame.model.*;

import java.io.*;
import java.util.*;

public class InformationManager {
    private static final InformationManager INSTANCE = new InformationManager();
    private final ArrayList<Student> students;
    private final ArrayList<Course> courses;
    private final ArrayList<Faculty> faculties;
    private final ArrayList<FacultyMember> facultyMembers;
    private final ArrayList<Assessment> assessments;
    private final ArrayList<CourseSession> courseSessions;
    private final ArrayList<CourseGrading> courseGradings;
    private final Map<Object, Integer> identifier;

    public static InformationManager getInstance() {
        return INSTANCE;
    }

    private InformationManager() {
        students = new ArrayList<Student>();
        courses = new ArrayList<Course>();
        faculties = new ArrayList<Faculty>();
        facultyMembers = new ArrayList<FacultyMember>();
        identifier = new HashMap<Object, Integer>();
        assessments = new ArrayList<Assessment>();
        courseSessions = new ArrayList<CourseSession>();
        courseGradings = new ArrayList<CourseGrading>();
        retrieveInformation();
    }

    public void saveInformation() {
        try {
            saveStudents();
            saveFacultyMembers();
            saveAssessments();
            saveCourseSessions();
            saveStudentPlacementInCourseSession();
            saveCourseGrading();
            saveCourses();
            saveMarkRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void saveStudents() throws Exception {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("data/student.txt")));
        for (Student student : students) {
            printWriter.println(student.getFirstName());
            printWriter.println(student.getLastName());
            printWriter.println(student.getNRIC());
            printWriter.println(student.getDateOfBirthAsString());
            printWriter.println(student.getAddress());
            printWriter.println(student.getPhoneNumber());
            printWriter.println(student.getDateOfEnrollmentAsString());
            printWriter.println(student.getMatriculationNumber());
            printWriter.println(getId(student.getFaculty()));
        }
        printWriter.close();
    }

    private void saveFacultyMembers() throws Exception {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("data/faculty_member.txt")));
        for (FacultyMember facultyMember : facultyMembers) {
            printWriter.println(facultyMember.getFirstName());
            printWriter.println(facultyMember.getLastName());
            printWriter.println(facultyMember.getNRIC());
            printWriter.println(facultyMember.getDateOfBirthAsString());
            printWriter.println(facultyMember.getAddress());
            printWriter.println(facultyMember.getPhoneNumber());
            printWriter.println(facultyMember.getStatusOrdinal());
            printWriter.println(getId(facultyMember.getFaculty()));
        }
        printWriter.close();
    }

    private void saveAssessments() throws Exception {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("data/assessment.txt")));
        for (Assessment assessment : assessments) {
            printWriter.println(assessment.getName());
            printWriter.println(assessment.getWeightage());
        }
        printWriter.close();
    }

    private void saveCourseSessions() throws Exception{
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("data/session.txt")));
        for (CourseSession courseSession : courseSessions) {
            printWriter.println(courseSession.getName());
            printWriter.println(courseSession.getLocation());
            printWriter.println(courseSession.getCapacity());
        }
        printWriter.close();
    }

    private void saveStudentPlacementInCourseSession() throws Exception{
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("data/student_session.txt")));
        for (CourseSession courseSession : courseSessions) {
            String students = "";
            for (Student student : courseSession.getEnrolledStudents()) {
                students += getId(student) + " ";
            }
            printWriter.println(students);
        }
        printWriter.close();
    }

    private void saveCourseGrading() throws Exception{
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("data/grading.txt")));
        for (CourseGrading courseGrading : courseGradings) {
            int examId = getId(courseGrading.getExam());
            printWriter.println(examId);
            String courseWork = "";
            for (Assessment assessment : courseGrading.getCourseWork()) {
                courseWork += getId(assessment) + " ";
            }
            printWriter.println(courseWork);
        }
        printWriter.close();
    }

    private void saveCourses() throws Exception {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("data/course.txt")));
        for (Course course : courses) {
            printWriter.println(course.getName());
            printWriter.println(course.getCourseCode());
            printWriter.println(getId(course.getFaculty()));
            printWriter.println(course.getAcademicUnit());
            printWriter.println(course.getCapacity());
            String enrolledStudents = "";
            for (Student student : course.getEnrolledStudents()) {
                enrolledStudents += getId(student) + " ";
            }
            printWriter.println(enrolledStudents);
            String waitlist = "";
            for (Student student : course.getWaitList()) {
                waitlist += getId(student) + " ";
            }
            printWriter.println(waitlist);
            String coordinators = "";
            for (FacultyMember facultyMember : course.getCoordinators()) {
                coordinators += getId(facultyMember) + " ";
            }
            printWriter.println(coordinators);
            if(course.getLecture() != null) printWriter.println(getId(course.getLecture()));
            else printWriter.println(" ");
            String tutorials = "";
            for (CourseSession tutorial : course.getTutorialGroups()) {
                tutorials += getId(tutorial) + " ";
            }
            printWriter.println(tutorials);
            String labs = "";
            for (CourseSession lab : course.getLabGroups()) {
                labs += getId(lab) + " ";
            }
            printWriter.println(labs);
            if (course.getCourseGrading() != null) printWriter.println(getId(course.getCourseGrading()));
            else printWriter.println(" ");
        }
        printWriter.close();
    }


    private void saveMarkRecords() throws Exception{
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("data/mark_record.txt")));
        for (Course course : courses) {
            CourseGrading courseGrading = course.getCourseGrading();
            if(courseGrading == null) continue;
            for (Student student : course.getEnrolledStudents()) {
                MarkRecord markRecord = courseGrading.getStudentMarkRecord(student);
                Assessment exam = courseGrading.getExam();
                double mark = markRecord.getAssessmentMark(exam);
                printWriter.format("%d %d %d %.3f\n", getId(courseGrading), getId(exam), getId(student), mark);
                for (Assessment assessment : courseGrading.getCourseWork()) {
                    mark = markRecord.getAssessmentMark(assessment);
                    printWriter.format("%d %d %d %.3f\n", getId(courseGrading), getId(assessment), getId(student), mark);
                }
            }
        }
        printWriter.close();
    }



    private void retrieveInformation() {
        try {
            retrieveFaculties();
            retrieveStudents();
            retrieveFacultyMembers();
            retrieveAssessments();
            retrieveCourseSessions();
            retrieveStudentPlacementInCourseSession();
            retrieveCourseGrading();
            retrieveCourses();
            retrieveMarkRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retrieveStudents() throws Exception {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/student.txt")));
        while(scanner.hasNext()) {
            createNewStudent(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
        }
        scanner.close();
    }

    private void retrieveFaculties() throws Exception {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/faculty.txt")));
        while(scanner.hasNext()) {
            String name = scanner.nextLine();
            faculties.add(new Faculty(name));
            identifier.put(faculties.get(faculties.size()-1), faculties.size()-1);
        }
        scanner.close();
    }

    private void retrieveFacultyMembers() throws Exception{
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/faculty_member.txt")));
        while(scanner.hasNext()) {
            createNewFacultyMember(scanner.nextLine(), scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine());
        }
        scanner.close();
    }

    private void retrieveAssessments() throws Exception {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/assessment.txt")));
        while(scanner.hasNext()) {
            createNewAssessment(scanner.nextLine(), scanner.nextLine());
        }
        scanner.close();
    }

    private void retrieveCourseSessions() throws Exception {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/session.txt")));
        while(scanner.hasNext()) {
            createNewCourseSession(scanner.nextLine(),scanner.nextLine(), scanner.nextLine());
        }
        scanner.close();
    }

    private void retrieveCourses() throws Exception{
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/course.txt")));
        while(scanner.hasNext()) {
            createNewCourse(scanner.nextLine(), scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
        }
        scanner.close();
    }

    private void retrieveCourseGrading() throws Exception {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/grading.txt")));
        while(scanner.hasNext()) {
            createNewCourseGrading(scanner.nextLine(), scanner.nextLine());
        }
        scanner.close();
    }

    private void retrieveStudentPlacementInCourseSession() throws Exception {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/student_session.txt")));
        int id = 0;
        while(scanner.hasNext()) {
            String studentsString = scanner.nextLine();
            ArrayList<Student> students = parseStringToStudents(studentsString);
            courseSessions.get(id).setEnrolledStudents(students);
            id++;
        }
        scanner.close();
    }

    private void retrieveMarkRecords() throws Exception {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/mark_record.txt")));
        while(scanner.hasNext()) {
            String markRecordString = scanner.nextLine();
            StringTokenizer st = new StringTokenizer(markRecordString, " ");
            int courseGradingId = checkedParseInt(st.nextToken());
            int assessmentId = checkedParseInt(st.nextToken());
            int studentId = checkedParseInt(st.nextToken());
            double mark = checkedParseDouble(st.nextToken());
            CourseGrading courseGrading = courseGradings.get(courseGradingId);
            Assessment assesment = assessments.get(assessmentId);
            Student student = students.get(studentId);
            courseGrading.getStudentMarkRecord(student).setAssessmentMark(assesment, mark);
        }
        scanner.close();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Faculty> getFaculties() {
        return faculties;
    }

    public ArrayList<FacultyMember> getFacultyMembers() {
        return facultyMembers;
    }


    public Student createNewStudent(String firstName, String lastName, String NRIC, String dateOfBirthString, String address, String phoneNumber, String dateOfEnrollmentString, String matriculationNumber, String facultyId) {

        Calendar dateOfBirth = DateAdapter.getCalendar(dateOfBirthString);
        Calendar dateOfEnrollment = DateAdapter.getCalendar("01/"+dateOfEnrollmentString);

        Faculty faculty = null;
        if(!facultyId.isEmpty()) faculty = getFacultyById(checkedParseInt(facultyId));
        Particulars particulars = new Particulars(firstName, lastName, dateOfBirth, address, phoneNumber, NRIC);
        Student student = new Student(particulars, faculty, matriculationNumber, dateOfEnrollment);
        students.add(student);
        identifier.put(student, students.size()-1);
        if(faculty != null) {
            faculty.addStudent(student);
        }
        return student;
    }

    public FacultyMember createNewFacultyMember(String firstName, String lastName, String NRIC, String dateOfBirthString, String address, String phoneNumber, String status, String facultyId) {
        Calendar dateOfBirth = DateAdapter.getCalendar(dateOfBirthString);

        Faculty faculty = null;
        if(!facultyId.isEmpty()) faculty = getFacultyById(checkedParseInt(facultyId));
        Particulars particulars = new Particulars(firstName, lastName, dateOfBirth, address, phoneNumber, NRIC);
        FacultyMember facultyMember = new FacultyMember(particulars, faculty, getFacultyMemberStatus(status));
        facultyMembers.add(facultyMember);
        identifier.put(facultyMember, facultyMembers.size()-1);
        if(faculty != null) {
            faculty.addFacultyMember(facultyMember);
        }
        return facultyMember;
    }

    public Course createNewCourse(String name, String courseCode, String facultyId, String AU, String capacityString, String enrolledStudentsString, String waitlistString, String coordinatorsString, String lectureId, String tutorialGroupsString, String labGroupsString, String courseGradingId) {
        Faculty faculty = getFacultyById(checkedParseInt(facultyId));
        int academicUnit = checkedParseInt(AU);
        int capacity = checkedParseInt(capacityString);
        ArrayList<Student> enrolledStudents = parseStringToStudents(enrolledStudentsString);
        ArrayList<Student> waitlist = parseStringToStudents(waitlistString);
        ArrayList<FacultyMember> coordinators = parseStringToCoordinator(coordinatorsString);
        CourseSession lecture = null;
        if(!lectureId.isEmpty()) lecture  = getCourseSessionById(checkedParseInt(lectureId));
        ArrayList<CourseSession> tutorialGroups = parseStringToCourseSession(tutorialGroupsString);
        ArrayList<CourseSession> labGroups = parseStringToCourseSession(labGroupsString);
        CourseGrading courseGrading = getCourseGradingById(checkedParseInt(courseGradingId));
        return addNewCourse(name, courseCode, capacity, academicUnit, faculty, enrolledStudents, waitlist, coordinators, lecture, tutorialGroups, labGroups, courseGrading);
    }

    public Course addNewCourse(String name, String courseCode, int capacity, int academicUnit, Faculty faculty, ArrayList<Student> enrolledStudents, ArrayList<Student> waitlist, ArrayList<FacultyMember> coordinators, CourseSession lecture, ArrayList<CourseSession> tutorialGroups, ArrayList<CourseSession> labGroups, CourseGrading courseGrading) {
        Course course = new Course(name, courseCode, capacity, academicUnit);
        course.setFaculty(faculty);
        course.setEnrolledStudents(enrolledStudents);
        course.setWaitList(waitlist);
        course.setCoordinators(coordinators);
        course.setLecture(lecture);
        course.setTutorialGroups(tutorialGroups);
        course.setLabGroups(labGroups);
        course.setCourseGrading(courseGrading);
        courses.add(course);
        identifier.put(course, courses.size() - 1);
        if(faculty != null) faculty.addCourse(course);
        for (Student student : enrolledStudents) {
            student.addCourseRegistered(course);
        }
        return course;
    }

    public Assessment createNewAssessment(String name, String weightageString) {
        double weightage = (double) checkedParseInt(weightageString);
        return addNewAssessment(name, weightage);
    }

    public Assessment addNewAssessment(String name, double weightage) {
        Assessment assessment = new Assessment(name);
        assessment.setWeightage(weightage);
        assessments.add(assessment);
        identifier.put(assessment, assessments.size()-1);
        return assessment;
    }

    public CourseGrading createNewCourseGrading(String examId, String courseWorkString) {
        Assessment exam = assessments.get(checkedParseInt(examId));
        ArrayList<Assessment> courseWork = parseStringToAssessment(courseWorkString);
        return addNewCourseGrading(exam, courseWork);
    }

    public CourseGrading addNewCourseGrading(Assessment exam, ArrayList<Assessment> courseWork) {
        CourseGrading courseGrading = new CourseGrading();
        courseGrading.setExam(exam);
        courseGrading.setCourseWork(courseWork);
        courseGradings.add(courseGrading);
        identifier.put(courseGrading, courseGradings.size() - 1);
        return courseGrading;
    }

    public CourseSession createNewCourseSession(String name, String location, String capacityString) {
        int capacity = checkedParseInt(capacityString);
        CourseSession courseSession = new CourseSession(name, location, capacity);
        courseSessions.add(courseSession);
        identifier.put(courseSession, courseSessions.size()-1);
        return courseSession;
    }

    private ArrayList<Student> parseStringToStudents(String s) {
        StringTokenizer st = new StringTokenizer(s, " ");
        ArrayList<Student> list = new ArrayList<Student>();
        while (st.hasMoreTokens()) {
            int id = checkedParseInt(st.nextToken());
            list.add(students.get(id));
        }
        return list;
    }

    private ArrayList<CourseSession> parseStringToCourseSession(String s) {
        StringTokenizer st = new StringTokenizer(s, " ");
        ArrayList<CourseSession> list = new ArrayList<CourseSession>();
        while(st.hasMoreTokens()) {
            int id = checkedParseInt(st.nextToken());
            list.add(courseSessions.get(id));
        }
        return list;
    }

    private ArrayList<FacultyMember> parseStringToCoordinator(String s) {
        StringTokenizer st = new StringTokenizer(s, " ");
        ArrayList<FacultyMember> list = new ArrayList<FacultyMember>();
        while(st.hasMoreTokens()) {
            int id = checkedParseInt(st.nextToken());
            list.add(facultyMembers.get(id));
        }
        return list;
    }

    private ArrayList<Assessment> parseStringToAssessment(String s) {
        StringTokenizer st = new StringTokenizer(s, " ");
        ArrayList<Assessment> list = new ArrayList<Assessment>();
        while(st.hasMoreTokens()) {
            int id = checkedParseInt(st.nextToken());
            list.add(assessments.get(id));
        }
        return list;
    }

    private FacultyMember.STATUS getFacultyMemberStatus(String status) {
        int statusCode = 0;
        if(!status.isEmpty()) statusCode = checkedParseInt(status);
        return FacultyMember.STATUS.values()[statusCode];
    }

    public static int checkedParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static double checkedParseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public Student getStudentById(int id) {
        if(0 <= id && id < students.size()) {
            return students.get(id);
        } else {
            return null;
        }
    }

    public Faculty getFacultyById(int id) {
        if(0 <= id && id < faculties.size()) {
            return faculties.get(id);
        } else {
            return null;
        }
    }

    public FacultyMember getFacultyMemberById(int id) {
        if(0 <= id && id < facultyMembers.size()) {
            return facultyMembers.get(id);
        } else {
            return null;
        }
    }

    public Course getCourseById(int id) {
        if(0 <= id && id < courses.size()) {
            return courses.get(id);
        } else {
            return null;
        }
    }

    public CourseGrading getCourseGradingById(int id) {
        if(0 <= id && id < courseGradings.size()) {
            return courseGradings.get(id);
        } else {
            return null;
        }
    }

    public CourseSession getCourseSessionById(int id) {
        if(0 <= id && id < courseSessions.size()) {
            return courseSessions.get(id);
        } else {
            return null;
        }
    }

    public int getId(Object o) {
        return identifier.get(o);
    }


}

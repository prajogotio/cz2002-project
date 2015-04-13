package rep.scrame.controller;

import rep.scrame.model.*;

import java.io.*;
import java.util.*;

/**
 * The InformationManager class used to manage the system's state.
 */
public class InformationManager {
	/**
	 * The only instance of the information manager.
	 */
    private static final InformationManager INSTANCE = new InformationManager();
    
    /**
     * List of students currently on the system memory.
     */
    private final ArrayList<Student> students;
    
    /**
     * List of courses currently on the system memory.
     */
    private final ArrayList<Course> courses;
    
    /**
     * List of faculties currently on the system memory.
     */
    private final ArrayList<Faculty> faculties;
    
    /**
     * List of faculty members currently on the system memory.
     */
    private final ArrayList<FacultyMember> facultyMembers;
    
    /**
     * List of assessments currently on the system memory.
     */
    private final ArrayList<Assessment> assessments;
    
    /**
     * List of course sessions currently on the system memory.
     */
    private final ArrayList<CourseSession> courseSessions;
    
    /**
     * List of course gradings currently on the system memory.
     */
    private final ArrayList<CourseGrading> courseGradings;
    
    /**
     * Mapping between each object and its identifier in its corresponding list.
     * This is used to support a simple implementation of a database handling mechanism 
     * to store and restore data on txt files.
     */
    private final Map<Object, Integer> identifier;

    /**
     * Gets the only instance of information manager.
     * @return	The instance of information manager of the current application instance.
     */
    public static InformationManager getInstance() {
        return INSTANCE;
    }

    /**
     * Constructor of InformationManager.
     */
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

    /**
     * Saves informations of all information in the system memory.
     */
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


    /**
     * Saves student list.
     * @throws Exception 	If data file not found.
     */
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

    /**
     * Saves faculty members data.
     * @throws Exception	If data file not found.
     */
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

    /**
     * Saves assessments information.
     * @throws Exception	If data file not found.
     */
    private void saveAssessments() throws Exception {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("data/assessment.txt")));
        for (Assessment assessment : assessments) {
            printWriter.println(assessment.getName());
            printWriter.format("%3.5f\n", assessment.getWeightage());
        }
        printWriter.close();
    }

    /**
     * Saves course sessions information.
     * @throws Exception	If data file not found.
     */
    private void saveCourseSessions() throws Exception{
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("data/session.txt")));
        for (CourseSession courseSession : courseSessions) {
            printWriter.println(courseSession.getName());
            printWriter.println(courseSession.getLocation());
            printWriter.println(courseSession.getCapacity());
        }
        printWriter.close();
    }

    /**
     * Saves student placement in course sessions information.
     * @throws Exception	If data file not found.
     */
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

    /**
     * Saves course grading information.
     * @throws Exception	If data file not found.
     */
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
    
    /**
     * Saves courses information.
     * @throws Exception	If data file not found.
     */
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


    /**
     * Saves mark records.
     * @throws Exception	If data file not found.
     */
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


    /**
     * Retrieves informations in the beginning of the system state initialization.
     */
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

    /**
     * Retrieves students information.
     * @throws Exception	If data file not found.
     */
    private void retrieveStudents() throws Exception {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/student.txt")));
        while(scanner.hasNext()) {
            createNewStudent(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
        }
        scanner.close();
    }

    /**
     * Retrieves faculties.
     * @throws Exception	If data file not found.
     */
    private void retrieveFaculties() throws Exception {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/faculty.txt")));
        while(scanner.hasNext()) {
            String name = scanner.nextLine();
            faculties.add(new Faculty(name));
            identifier.put(faculties.get(faculties.size()-1), faculties.size()-1);
        }
        scanner.close();
    }

    /**
     * Retrieve faculty members.
     * @throws Exception	If data file not found.
     */
    private void retrieveFacultyMembers() throws Exception{
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/faculty_member.txt")));
        while(scanner.hasNext()) {
            createNewFacultyMember(scanner.nextLine(), scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine());
        }
        scanner.close();
    }

    /**
     * Retrieve assesmments data.
     * @throws Exception	If data file not found.
     */
    private void retrieveAssessments() throws Exception {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/assessment.txt")));
        while(scanner.hasNext()) {
            createNewAssessment(scanner.nextLine(), scanner.nextLine());
        }
        scanner.close();
    }

    /**
     * Retrieve course sessions information.
     * @throws Exception	If data file not found.
     */
    private void retrieveCourseSessions() throws Exception {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/session.txt")));
        while(scanner.hasNext()) {
            createNewCourseSession(scanner.nextLine(),scanner.nextLine(), scanner.nextLine());
        }
        scanner.close();
    }

    /**
     * Retrieve courses information.
     * @throws Exception	If data file not found.
     */
    private void retrieveCourses() throws Exception{
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/course.txt")));
        while(scanner.hasNext()) {
            createNewCourse(scanner.nextLine(), scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(),scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
        }
        scanner.close();
    }

    /**
     * Retrieve course grading informations.
     * @throws Exception	If data file not found.
     */
    private void retrieveCourseGrading() throws Exception {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/grading.txt")));
        while(scanner.hasNext()) {
            createNewCourseGrading(scanner.nextLine(), scanner.nextLine());
        }
        scanner.close();
    }

    /**
     * Retrieve student placemet in course sessions.
     * @throws Exception	If data file not found.
     */
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

    /**
     * Retrieve mark records.
     * @throws Exception	If data file not found.
     */
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

    /**
     * Gets the list of students.
     * @return	List of students.
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Gets the course list.
     * @return	Course list.
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Gets the faculty list.
     * @return Faculty list.
     */
    public ArrayList<Faculty> getFaculties() {
        return faculties;
    }
    
    /**
     * Gets the faculty member list.
     * @return	Faculty member list.
     */
    public ArrayList<FacultyMember> getFacultyMembers() {
        return facultyMembers;
    }

    /**
     * Creates a new student. This is an adapter method between the input to system representation.
     * @param firstName					Student's first name
     * @param lastName					Student's last name
     * @param NRIC						Student's NRIC
     * @param dateOfBirthString			Student's date of birth string
     * @param address					Student's address
     * @param phoneNumber				Student's phone number
     * @param dateOfEnrollmentString	Student's date of enrollment string
     * @param matriculationNumber		Student's matriculation number
     * @param facultyId					Student's faculty ID
     * @return							Student object.
     */
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

    /**
     * Creates a new faculty member. This is an adapter method between the input to system representation.
     * @param firstName				Member's first name.
     * @param lastName				Member's last name.
     * @param NRIC 					Member's NRIC.
     * @param dateOfBirthString		Member's date of birth string.
     * @param address				Member's address.
     * @param phoneNumber			Member's phone number.
     * @param status				Member's status.
     * @param facultyId				Member's faculty ID
     * @return						Faculty Member object.
     */
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

    /**
     * Creates new course. This is an adapter method between the input to system representation.
     * @param name						Course's name.
     * @param courseCode				Course's course code.
     * @param facultyId					Course's facultyId.
     * @param AU						Course's AU.
     * @param capacityString			Course's capacity.
     * @param enrolledStudentsString	Course's enrolled student string.
     * @param waitlistString			Course's waitlist.
     * @param coordinatorsString		Course's coordinators.
     * @param lectureId					Course's lecture session.
     * @param tutorialGroupsString		Course's tutorial groups.
     * @param labGroupsString			Course's lab groups.
     * @param courseGradingId			Course's course gradings.
     * @return							Course object.
     */
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

    /**
     * Adds a new course.
     * @param name						Course's name.
     * @param courseCode				Course's course code.
     * @param facultyId					Course's facultyId.
     * @param AU						Course's AU.
     * @param capacityString			Course's capacity.
     * @param enrolledStudentsString	Course's enrolled student.
     * @param waitlistString			Course's waitlist.
     * @param coordinatorsString		Course's coordinators.
     * @param lectureId					Course's lecture session.
     * @param tutorialGroupsString		Course's tutorial groups.
     * @param labGroupsString			Course's lab groups.
     * @param courseGradingId			Course's course gradings.
     * @return
     */
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

    /**
     * Creates new assessment.	This is an adapter method between the input to system representation.
     * @param name				Name of the assessment.
     * @param weightageString	Weigthage of the assessment.
     * @return		Assessment object.
     */
    public Assessment createNewAssessment(String name, String weightageString) {
        double weightage = checkedParseDouble(weightageString);
        return addNewAssessment(name, weightage);
    }
    
    /**
     * Adds new assessment.	
     * @param name				Name of the assessment.
     * @param weightageString	Weigthage of the assessment.
     * @return		Assessment object.
     */
    public Assessment addNewAssessment(String name, double weightage) {
        Assessment assessment = new Assessment(name);
        assessment.setWeightage(weightage);
        assessments.add(assessment);
        identifier.put(assessment, assessments.size()-1);
        return assessment;
    }

    /**
     * Creates new course grading. This is an adapter method between the input to system representation.
     * @param examId				Examination of the course grading.
     * @param courseWorkString		CourseWorks of the course grading.
     * @return						CourseGrading object.
     */
    public CourseGrading createNewCourseGrading(String examId, String courseWorkString) {
        Assessment exam = assessments.get(checkedParseInt(examId));
        ArrayList<Assessment> courseWork = parseStringToAssessment(courseWorkString);
        return addNewCourseGrading(exam, courseWork);
    }

    /**
     * Adds new course grading. 
     * @param examId				Examination of the course grading.
     * @param courseWorkString		CourseWorks of the course grading.
     * @return						CourseGrading object.
     */
    public CourseGrading addNewCourseGrading(Assessment exam, ArrayList<Assessment> courseWork) {
        CourseGrading courseGrading = new CourseGrading();
        courseGrading.setExam(exam);
        courseGrading.setCourseWork(courseWork);
        courseGradings.add(courseGrading);
        identifier.put(courseGrading, courseGradings.size() - 1);
        return courseGrading;
    }

    /**
     * Creates new course session.
     * @param name				Name of the course session.
     * @param location			Venue of the course session.
     * @param capacityString	Capacity of the course session.
     * @return					CourseSession object.
     */
    public CourseSession createNewCourseSession(String name, String location, String capacityString) {
        int capacity = checkedParseInt(capacityString);
        CourseSession courseSession = new CourseSession(name, location, capacity);
        courseSessions.add(courseSession);
        identifier.put(courseSession, courseSessions.size()-1);
        return courseSession;
    }

    /**
     * Parses a string of student ids into student objects.
     * @param s	String of student ids.
     * @return	ArrayList<Student> of student objects.
     */
    private ArrayList<Student> parseStringToStudents(String s) {
        StringTokenizer st = new StringTokenizer(s, " ");
        ArrayList<Student> list = new ArrayList<Student>();
        while (st.hasMoreTokens()) {
            int id = checkedParseInt(st.nextToken());
            list.add(students.get(id));
        }
        return list;
    }

    /**
     * Parses a string of course session ids into CourseSession objects.
     * @param s	String of course session ids.
     * @return	array list of course session objects.
     */
    private ArrayList<CourseSession> parseStringToCourseSession(String s) {
        StringTokenizer st = new StringTokenizer(s, " ");
        ArrayList<CourseSession> list = new ArrayList<CourseSession>();
        while(st.hasMoreTokens()) {
            int id = checkedParseInt(st.nextToken());
            list.add(courseSessions.get(id));
        }
        return list;
    }

    /**
     * Parses a string of coordinator ids into FacultyMember objects.
     * @param s	String of coordinator ids.
     * @return	arraylist of FacultyMember objects.
     */
    private ArrayList<FacultyMember> parseStringToCoordinator(String s) {
        StringTokenizer st = new StringTokenizer(s, " ");
        ArrayList<FacultyMember> list = new ArrayList<FacultyMember>();
        while(st.hasMoreTokens()) {
            int id = checkedParseInt(st.nextToken());
            list.add(facultyMembers.get(id));
        }
        return list;
    }

    /**
     * Parses string of assessment ids into Assessment objects.
     * @param s	String of assessment ids.
     * @return	array list of Assessment Objects.
     */
    private ArrayList<Assessment> parseStringToAssessment(String s) {
        StringTokenizer st = new StringTokenizer(s, " ");
        ArrayList<Assessment> list = new ArrayList<Assessment>();
        while(st.hasMoreTokens()) {
            int id = checkedParseInt(st.nextToken());
            list.add(assessments.get(id));
        }
        return list;
    }

    /**
     * Gets faculty member status from the status index.
     * @param status	Faculty member status index.
     * @return		FacultyMember.STATUS object.
     */
    private FacultyMember.STATUS getFacultyMemberStatus(String status) {
        int statusCode = 0;
        if(!status.isEmpty()) statusCode = checkedParseInt(status);
        return FacultyMember.STATUS.values()[statusCode];
    }

    /**
     * Parses of a string into integer safely.
     * @param s String of numbers.
     * @return	int representation of the string.
     */
    public static int checkedParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Parses a string of double safely.
     * @param s	A string of double.
     * @return	double representation of the string.
     */
    public static double checkedParseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Gets student by id.
     * @param id	Id of the student.
     * @return	Student object.
     */
    public Student getStudentById(int id) {
        if(0 <= id && id < students.size()) {
            return students.get(id);
        } else {
            return null;
        }
    }

    /**
     * Gets faculty by id.
     * @param id	Faculty id.
     * @return		Faculty Object.
     */
    public Faculty getFacultyById(int id) {
        if(0 <= id && id < faculties.size()) {
            return faculties.get(id);
        } else {
            return null;
        }
    }

    /**
     * Gets faculty member by id.
     * @param id	Faculty member id.
     * @return		FacultyMember object.
     */
    public FacultyMember getFacultyMemberById(int id) {
        if(0 <= id && id < facultyMembers.size()) {
            return facultyMembers.get(id);
        } else {
            return null;
        }
    }

    /**
     * Gets course by id.
     * @param id	Course id.
     * @return		Course object.
     */
    public Course getCourseById(int id) {
        if(0 <= id && id < courses.size()) {
            return courses.get(id);
        } else {
            return null;
        }
    }

    /**
     * Gets course grading by id.
     * @param id	Course grading id.
     * @return		CourseGrading object.
     */
    public CourseGrading getCourseGradingById(int id) {
        if(0 <= id && id < courseGradings.size()) {
            return courseGradings.get(id);
        } else {
            return null;
        }
    }

    /**
     * Gets course session by id.
     * @param id	Course session id.
     * @return		CourseSession Object.
     */
    public CourseSession getCourseSessionById(int id) {
        if(0 <= id && id < courseSessions.size()) {
            return courseSessions.get(id);
        } else {
            return null;
        }
    }
    
    
    /**
     * Gets the id of an object.
     * @param o		Object.
     * @return		id of the object.
     */
    public int getId(Object o) {
        return identifier.get(o);
    }


}

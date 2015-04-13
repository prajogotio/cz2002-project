package rep.scrame.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *	The course grading policy of a particular Course.
 *
 */
public class CourseGrading {
	/**
	 * The examination of a particular course.
	 */
    private Assessment exam;
    
    /**
     * The set of courseworks in a particular course.
     */
    private ArrayList<Assessment> courseWork;
    
    /**
     * The mark records of students enrolled in this course.
     */
    private Map<Student, MarkRecord> markRecords;

    /**
     * CourseGrading constructor.
     */
    public CourseGrading() {
        exam = null;
        courseWork = new ArrayList<Assessment>();
        markRecords = new HashMap<Student, MarkRecord>();
    }


    /**
     * Adds a sub part of the coursework in a particular course.
     * @param assessment	The coursework to be added.
     */
    public void addCourseWork(Assessment assessment) {
        if(!courseWork.contains(assessment)) courseWork.add(assessment);
    }

    /**
     * Removes a sub part of the coursework.
     * @param assessment	The coursework to be removed.
     */
    public void removeCourseWork(Assessment assessment) {
        courseWork.remove(assessment);
    }

    /**
     * Sets the examination of a particular course.
     * @param exam	The examination to be added.
     */
    public void setExam(Assessment exam) {
        this.exam = exam;
    }

    /**
     * Retrieves the examination of this course grading policy.
     * @return	The examination of this grading policy.
     */
    public Assessment getExam() {
        return exam;
    }

    /**
     * Retreives the list of courseworks of this grading policy.
     * @return	The list of courseworks registered.
     */
    public ArrayList<Assessment> getCourseWork() {
        return courseWork;
    }

    /**
     * Retrieves the mark records associated with a student.
     * @param student	The student to be considered.
     * @return			The mark record of the student in this course.
     */
    public MarkRecord getStudentMarkRecord(Student student) {
        markRecords.putIfAbsent(student, new MarkRecord());
        return markRecords.get(student);
    }

    /**
     * Sets the mark of a particular assessment into the student's mark record.
     * @param student		The student whose mark is to be updated.
     * @param assessment	The assessments which is going to be updated.
     * @param mark			The desired mark of the assessment.
     */
    public void setStudentAssessmentMark(Student student, Assessment assessment, double mark) {
        MarkRecord markRecord = getStudentMarkRecord(student);
        markRecord.setAssessmentMark(assessment, mark);
    }

    /**
     * Sets the courseworks of the course grading policy.
     * @param courseWork	The list of courseworks.
     */
    public void setCourseWork(ArrayList<Assessment> courseWork) {
        this.courseWork = courseWork;
    }
}
package rep.scrame.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CourseGrading {
    private Assessment exam;
    private ArrayList<Assessment> courseWork;
    private Map<Student, MarkRecord> markRecords;

    public CourseGrading() {
        exam = null;
        courseWork = new ArrayList<Assessment>();
        markRecords = new HashMap<Student, MarkRecord>();
    }


    public void addCourseWork(Assessment assessment) {
        if(!courseWork.contains(assessment)) courseWork.add(assessment);
    }

    public void removeCourseWork(Assessment assessment) {
        courseWork.remove(assessment);
    }

    public void setExam(Assessment exam) {
        this.exam = exam;
    }

    public Assessment getExam() {
        return exam;
    }

    public ArrayList<Assessment> getCourseWork() {
        return courseWork;
    }

    public MarkRecord getStudentMarkRecord(Student student) {
        markRecords.putIfAbsent(student, new MarkRecord());
        return markRecords.get(student);
    }

    public void setStudentAssessmentMark(Student student, Assessment assessment, double mark) {
        MarkRecord markRecord = getStudentMarkRecord(student);
        markRecord.setAssessmentMark(assessment, mark);
    }

    public void setCourseWork(ArrayList<Assessment> courseWork) {
        this.courseWork = courseWork;
    }
}
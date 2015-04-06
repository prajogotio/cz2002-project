package rep.scrame.view;

import java.util.ArrayList;

import rep.scrame.model.Assessment;
import rep.scrame.model.Course;
import rep.scrame.model.CourseGrading;
import rep.scrame.model.MarkRecord;
import rep.scrame.model.Student;

public class StudentTranscript implements ScrameView {

	private Student student;
	
	public StudentTranscript(Student student) {
		this.student = student;
	}
	
	@Override
	public void display() {
		System.out.println("========================================================");
        System.out.println("                 Student Transcript");
        System.out.println("========================================================");
        System.out.println("Student name : " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Faculty      : " + (student.getFaculty() == null? "" : student.getFaculty().getName()));
        System.out.println();
        ArrayList<Course> courses = student.getCourseRegistered();
        for (Course course : courses) {
            CourseGrading courseGrading = course.getCourseGrading();
            if(courseGrading == null) continue;
            MarkRecord markRecord = courseGrading.getStudentMarkRecord(student);
            Assessment exam = courseGrading.getExam();
            ArrayList<Assessment> courseWork = courseGrading.getCourseWork();
            double examMark = markRecord.getAssessmentMark(exam);
            double overallMark = examMark * exam.getWeightage() / 100.0;
            double courseWorkOverallMark = 0;
            ArrayList<Double> courseWorkMarks = new ArrayList<Double>();
            for (Assessment assessment : courseWork) {
                courseWorkMarks.add(markRecord.getAssessmentMark(assessment));
                overallMark += courseWorkMarks.get(courseWorkMarks.size()-1) * assessment.getWeightage() * (100.0 - exam.getWeightage()) / 10000.0;
                courseWorkOverallMark += courseWorkMarks.get(courseWorkMarks.size()-1) * assessment.getWeightage() / 100.0;
            }
            System.out.println("Course title     : " + course.getName());
            System.out.println("Course code      : " + course.getCourseCode());
            System.out.format("Overall mark     : %.2f\n", overallMark);
            System.out.format("Exam mark        : %.2f\n", examMark);
            System.out.format("Coursework overall mark : %.2f\n", courseWorkOverallMark);
            if (courseWork.size() > 1) {
                System.out.println("Coursework mark break down:\n");
                for (int i = 0; i < courseWork.size(); ++i) {
                    System.out.format("    %-20s : %.2f\n", courseWork.get(i).getName(), courseWorkMarks.get(i));
                }
                System.out.println();
            }
            System.out.println();
        }
		
	}
	
	
	
}

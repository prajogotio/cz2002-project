package rep.scrame.view;

import java.util.ArrayList;

import rep.scrame.model.Assessment;
import rep.scrame.model.Course;
import rep.scrame.model.MarkRecord;
import rep.scrame.model.Student;

public class CourseStatistic implements ScrameView{
	private Course course;
	
	public CourseStatistic(Course course) {
		this.course = course;
	}
	
	@Override
	public void display() {
		System.out.println("####################################");
		System.out.println("####      Course Statistics     ####");
		System.out.println("####################################");
		System.out.format("\n%s - %s\n\n", course.getCourseCode(), course.getName());
		
		System.out.format("Exam marks statistics of all enrolled students\n");
		printStudentAssessmentMark(course.getCourseGrading().getExam());
		
		
		System.out.println("\nMarks statistics for all coursework components\n");
		for (Assessment assessment : course.getCourseGrading().getCourseWork()) {
			System.out.format("\n%s\n", assessment.getName());
			printStudentAssessmentMark(assessment);
		}
		
		System.out.println("\nMarks statistics for overall coursework component\n");
		printStudentCourseWorkMark();
		
		System.out.println("\nMarks statistics for overall performance of each student\n");
		printStudentOverallPerformance();
	}
	
	
	private void printStudentAssessmentMark(Assessment assessment) {
		ArrayList<Student> students = course.getEnrolledStudents();
		
		double average = 0;
		System.out.println("__________________________________________________");
		System.out.println("            student name                   mark");
		System.out.println("--------------------------------------------------");
		for (Student student : students) {
			MarkRecord record = course.getCourseGrading().getStudentMarkRecord(student);
			double mark = record.getAssessmentMark(assessment);
			System.out.format("%40s  %5.2f\n", student.getFirstName() + " "+ student.getLastName(), mark);
			average += mark;
		}
		average /= students.size();
		System.out.format("\nMean   : %5.2f\n", average);
	}
	
	private void printStudentCourseWorkMark() {
		ArrayList<Student> students = course.getEnrolledStudents();
		
		double average = 0;
		System.out.println("__________________________________________________");
		System.out.println("            student name                   mark");
		System.out.println("--------------------------------------------------");
		for (Student student : students) {
			MarkRecord record = course.getCourseGrading().getStudentMarkRecord(student);
			double mark = 0;
			for (Assessment assessment : course.getCourseGrading().getCourseWork()){
				double cMark = record.getAssessmentMark(assessment);
				mark += cMark * assessment.getWeightage() / 100.0;
			}
			System.out.format("%40s  %5.2f\n", student.getFirstName() + " "+ student.getLastName(), mark);
			average += mark;
		}
		average /= students.size();
		System.out.format("\nMean   : %5.2f\n", average);
	}
	
	private void printStudentOverallPerformance() {
		ArrayList<Student> students = course.getEnrolledStudents();
		Assessment exam = course.getCourseGrading().getExam();
		double average = 0;
		System.out.println("________________________________________");
		System.out.println("            student name         mark");
		System.out.println("----------------------------------------");
		for (Student student : students) {
			MarkRecord record = course.getCourseGrading().getStudentMarkRecord(student);
			double mark = 0;
			for (Assessment assessment : course.getCourseGrading().getCourseWork()){
				double cMark = record.getAssessmentMark(assessment);
				mark += cMark * assessment.getWeightage() / 100.0;
			}
			mark = record.getAssessmentMark(exam) * exam.getWeightage() / 100.0 + mark * (1.0 - exam.getWeightage() / 100.0);
			System.out.format("%30s  %5.2f\n", student.getFirstName() + " "+ student.getLastName(), mark);
			average += mark;
		}
		average /= students.size();
		System.out.format("\nMean   : %5.2f\n", average);
	}
}

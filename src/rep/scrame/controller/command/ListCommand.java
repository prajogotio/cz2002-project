package rep.scrame.controller.command;

import java.util.StringTokenizer;

import rep.scrame.view.CourseListView;
import rep.scrame.view.FacultyListView;
import rep.scrame.view.FacultyMemberListView;
import rep.scrame.view.ScrameView;
import rep.scrame.view.StudentListView;


public class ListCommand implements Command {
	private ScrameView studentListView;
	private ScrameView courseListView;
	private ScrameView facultyMemberListView;
	private ScrameView facultyListView;
	
	public ListCommand() {
		studentListView = new StudentListView();
		courseListView = new CourseListView();
		facultyMemberListView = new FacultyMemberListView();
		facultyListView = new FacultyListView();
	}
	
    @Override
    public void invoke(CommandInterpreter context, StringTokenizer tokens) {
        String domain;
        if(tokens.hasMoreTokens()) {
            domain = tokens.nextToken();
        } else {
            System.out.println("Command does not match any recognised use case: list [students | courses | faculties | faculty-members]");
            return;
        }

        if (domain.equals("students")) {
        	studentListView.display();
        } else if (domain.equals("courses")) {
            courseListView.display();
        } else if (domain.equals("faculties")) {
        	facultyListView.display();
        } else if (domain.equals("faculty-members")) {
            facultyMemberListView.display();
        } else {
            System.out.println("Command does not match any recognised use case: list [students | courses | faculties | faculty-members]");
        }
    }
}
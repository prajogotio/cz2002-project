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
        	displayErrorMessage();
            return;
        }

        if (domain.equals("-s")) {
        	studentListView.display();
        } else if (domain.equals("-c")) {
            courseListView.display();
        } else if (domain.equals("-f")) {
        	facultyListView.display();
        } else if (domain.equals("-fm")) {
            facultyMemberListView.display();
        } else {
            displayErrorMessage();
        }
    }
    
    private void displayErrorMessage() {
        System.out.println("Command does not match any recognised use case: ls [-s | -c | -f | -fm]");

    }
}
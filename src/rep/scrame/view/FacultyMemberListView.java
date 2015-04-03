package rep.scrame.view;

import java.util.ArrayList;

import rep.scrame.controller.InformationManager;
import rep.scrame.model.FacultyMember;

public class FacultyMemberListView implements ScrameView {

	public FacultyMemberListView() {}
	
	@Override
	public void display() {
		System.out.println();
        ArrayList<FacultyMember> facultyMembers = InformationManager.getInstance().getFacultyMembers();

        int id = 0;
        System.out.println("  id  first name            last name            NRIC            faculty                                              status");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
        for (FacultyMember facultyMember : facultyMembers) {
            String facultyName = "";
            if(facultyMember.getFaculty() != null) facultyName = facultyMember.getFaculty().getName();
            System.out.format("%4d  %-20s  %-20s %-15s %-51s  %-15s\n",id++, facultyMember.getFirstName(), facultyMember.getLastName(), facultyMember.getNRIC(), facultyName, facultyMember.getStatus());
        }
	}

}
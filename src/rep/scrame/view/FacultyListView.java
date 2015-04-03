package rep.scrame.view;

import java.util.ArrayList;

import rep.scrame.controller.InformationManager;
import rep.scrame.model.Faculty;

public class FacultyListView implements ScrameView{

	public FacultyListView() {}
	
	@Override
	public void display() {
		System.out.println();
        ArrayList<Faculty> faculties = InformationManager.getInstance().getFaculties();
        int id = 0;
        System.out.println("  id                       Faculty Name");
        System.out.println("-----------------------------------------------------------------");
        for (Faculty faculty : faculties) {
            System.out.format("%4d    %s\n", id++, faculty.getName());
        }
	}

}

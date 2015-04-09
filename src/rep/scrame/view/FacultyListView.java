package rep.scrame.view;

import java.util.ArrayList;

import rep.scrame.controller.InformationManager;
import rep.scrame.model.Faculty;

public class FacultyListView extends ListView{

	public FacultyListView() { super(InformationManager.getInstance().getFaculties());}
	
	/*
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
	*/
	
	
	@Override
	protected String formatHeader(){
		String result = "  id                       Faculty Name\n-----------------------------------------------------------------";
		return result;
	}
	
	@Override
	protected String formatLine(){
		ArrayList<Faculty> faculties = InformationManager.getInstance().getFaculties();
		Faculty faculty = faculties.get(currentIndex);
        String result = String.format("%4d    %s", currentIndex, faculty.getName());
        return result;
        
	}

}

package rep.scrame.view;

import rep.scrame.model.FacultyMember;

/**
 *	Displays the faculty member information in a certain format.
 */
public class FacultyMemberInformationView implements ScrameView{
	/**
	 * The faculty whose information is to be displayed.
	 */
	private FacultyMember facultyMember;
	
	/**
	 * FacultyMemberInformationView constructor
	 * @param facultyMember	The faculty whose information is to be displayed.
	 */
	public FacultyMemberInformationView(FacultyMember facultyMember) {
		this.facultyMember = facultyMember;
	}
	
	@Override
	public void display() {
		System.out.println();
        System.out.println("First name       : " + facultyMember.getFirstName());
        System.out.println("Last name        : " + facultyMember.getLastName());
        System.out.println("Date of Birth    : " + facultyMember.getDateOfBirthAsString());
        System.out.println("Rank             : " + facultyMember.getStatus());
        System.out.println("Faculty          : " + (facultyMember.getFaculty() != null ? facultyMember.getFaculty().getName() : ""));
        System.out.println("NRIC             : " + facultyMember.getNRIC());
        System.out.println("Phone No.        : " + facultyMember.getPhoneNumber());
        System.out.println("Address          : " + facultyMember.getAddress());
	}
}

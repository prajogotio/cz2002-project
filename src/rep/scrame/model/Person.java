package rep.scrame.model;


import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class representation of a person.
 */
public class Person {
	/**
	 * The particulars belonging to the person.
	 */
    private Particulars particulars;

    /**
     * Constructor of the person.
     * @param particulars
     */
    public Person(Particulars particulars) {
        this.particulars = particulars;
    }

    /**
     * Gets the first name of the person.
     * @return	The first name of the person.
     */
    public String getFirstName() {
        return particulars.getFirstName();
    }

    /**
     * Gets the last name of the person.
     * @return	The last name of the person
     */
    public String getLastName() {
        return particulars.getLastName();
    }

    /**
     * Gets the date of birth of the person.
     * @return	The date of birth of the person.
     */
    public Calendar getDateOfBirth() {
        return particulars.getDateOfBirth();
    }

    /**
     * Gets the address of the person.
     * @return	The address of the person.
     */
    public String getAddress() {
        return particulars.getAddress();
    }

    /**
     * Gets the phone number of the person.
     * @return	The phone number of the person.
     */
    public String getPhoneNumber() {
        return particulars.getPhoneNumber();
    }

    /**
     * Gets the NRIC of the person.
     * @return	The NRIC of the person.
     */
    public String getNRIC() {
        return particulars.getNRIC();
    }

    /**
     * Gets the date of birth of the person, in dd/MM/yyyy format.
     * @return	The date of birth of the person in dd/MM/yyyy format.
     */
    public String getDateOfBirthAsString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateOfBirthAsString = dateFormatter.format(getDateOfBirth().getTime());
        return dateOfBirthAsString;
    }
    
    /**
     * Sets the address of the person.
     * @param address	Address of the person.
     */
    public void setAddress(String address) {
    	particulars.setAddress(address);
    }
    
    /**
     * Sets the date of birth of the person.
     * @param date	Date from 1 - 31.
     * @param month	Month from 0 - 11.
     * @param year	Year in YYYY format.
     */
    public void setDateOfBirth(int date, int month, int year) {
    	particulars.setDateOfBirth(date, month, year);
    }
    
}

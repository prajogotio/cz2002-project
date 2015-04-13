package rep.scrame.model;

import java.util.Calendar;

/**
 * A class for the particulars and information of a person.
 */
public class Particulars {
	/**
	 * A person's first name.
	 */
    private String firstName;
    
    /**
     * A persons' last name.
     */
    private String lastName;
    
    /**
     * A persons' date of birth.
     */
    private Calendar dateOfBirth;
    
    /**
     * A person's address.
     */
    private String address;
    
    /**
     * A person's phone number.
     */
    private String phoneNumber;
    
    /**
     * A person's NRIC.
     */
    private String NRIC;

    /**
     * Constructor of the particulars.
     * @param firstName		His first name.
     * @param lastName		His last name.
     * @param dateOfBirth	His date of birth.
     * @param address		His address.
     * @param phoneNumber	His phone number.
     * @param NRIC			His NRIC.
     */
    public Particulars(String firstName, String lastName, Calendar dateOfBirth, String address, String phoneNumber, String NRIC) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.NRIC = NRIC;
    }

    /**
     * Sets the first name of the person.
     * @param firstName	The first name of the person.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the last name of the person.
     * @param lastName	The last name of the person.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the date of birth of a person.
     * @param date		Date from 1 to 31.
     * @param month		Month from 0 to 11.
     * @param year		Year in YYYY format.
     */
    public void setDateOfBirth(int date, int month, int year) {
        this.dateOfBirth.set(year, month, date);
    }

    /**
     * Sets the address of the person.
     * @param address	Address of the person.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the phone number of the person.
     * @param phoneNumber	The phone number of the person.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the NRIC of the person.
     * @param NRIC	The NRIC of the person.
     */
    public void setNRIC(String NRIC) {
        this.NRIC = NRIC;
    }

    /**
     * Gets the first name of the person.
     * @return	The first name of the person.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name of the person.
     * @return	The last name of the person.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the date of birth of the person.
     * @return	The date of birth of the person.
     */
    public Calendar getDateOfBirth() {
        Calendar copyOfDate = Calendar.getInstance();
        copyOfDate.set(dateOfBirth.get(Calendar.YEAR), dateOfBirth.get(Calendar.MONTH), dateOfBirth.get(Calendar.DAY_OF_MONTH));
        return copyOfDate;
    }

    /**
     * Gets the address of the person.
     * @return	The address of the person.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the phone number of the person.
     * @return	The phone number of the person.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the NRIC of the person.
     * @return	The NRIC of the person.
     */
    public String getNRIC() {
        return NRIC;
    }

}
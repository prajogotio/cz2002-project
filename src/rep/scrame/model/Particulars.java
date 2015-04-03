package rep.scrame.model;

import java.util.Calendar;


public class Particulars {
    private String firstName;
    private String lastName;
    private Calendar dateOfBirth;
    private String address;
    private String phoneNumber;
    private String NRIC;

    public Particulars(String firstName, String lastName, Calendar dateOfBirth, String address, String phoneNumber, String NRIC) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.NRIC = NRIC;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(int date, int month, int year) {
        this.dateOfBirth.set(year, month, date);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNRIC(String NRIC) {
        this.NRIC = NRIC;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Calendar getDateOfBirth() {
        Calendar copyOfDate = Calendar.getInstance();
        copyOfDate.set(dateOfBirth.get(Calendar.YEAR), dateOfBirth.get(Calendar.MONTH), dateOfBirth.get(Calendar.DAY_OF_MONTH));
        return copyOfDate;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getNRIC() {
        return NRIC;
    }

}
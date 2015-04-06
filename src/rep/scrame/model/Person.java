package rep.scrame.model;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Person {
    private Particulars particulars;

    public Person(Particulars particulars) {
        this.particulars = particulars;
    }

    public String getFirstName() {
        return particulars.getFirstName();
    }

    public String getLastName() {
        return particulars.getLastName();
    }

    public Calendar getDateOfBirth() {
        return particulars.getDateOfBirth();
    }

    public String getAddress() {
        return particulars.getAddress();
    }

    public String getPhoneNumber() {
        return particulars.getPhoneNumber();
    }

    public String getNRIC() {
        return particulars.getNRIC();
    }

    public String getDateOfBirthAsString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateOfBirthAsString = dateFormatter.format(getDateOfBirth().getTime());
        return dateOfBirthAsString;
    }
    
    public void setAddress(String address) {
    	particulars.setAddress(address);
    }
    
    public void setDateOfBirth(int date, int month, int year) {
    	particulars.setDateOfBirth(date, month, year);
    }
    
}

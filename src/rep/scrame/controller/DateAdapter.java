package rep.scrame.controller;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * The adapter patter for managing calendar interface. It adapts our system interface
 * with java.util.Calendar interface.
 */
public class DateAdapter {
	/**
	 * The constants used to identify the months.
	 */
    private static int[] MONTH = new int[12];
    static {
        MONTH[0] = Calendar.JANUARY;
        MONTH[1] = Calendar.FEBRUARY;
        MONTH[2] = Calendar.MARCH;
        MONTH[3] = Calendar.APRIL;
        MONTH[4] = Calendar.MAY;
        MONTH[5] = Calendar.JUNE;
        MONTH[6] = Calendar.JULY;
        MONTH[7] = Calendar.AUGUST;
        MONTH[8] = Calendar.SEPTEMBER;
        MONTH[9] = Calendar.OCTOBER;
        MONTH[10] = Calendar.NOVEMBER;
        MONTH[11] = Calendar.DECEMBER;
    }

    /**
     * Gets the calendar representation of the date string.
     * @param ddmmyyyy Date strings to be converted.
     * @return	Calendar object after conversion.
     */
    public static Calendar getCalendar(String ddmmyyyy) {
        Calendar calendar = Calendar.getInstance();
        StringTokenizer st = new StringTokenizer(ddmmyyyy, "/");
        int date = 0;
        if(st.hasMoreTokens()) date = InformationManager.checkedParseInt(st.nextToken());
        if(date < 0) date = 0;
        int month = 0;
        if(st.hasMoreTokens()) month = getMonth(InformationManager.checkedParseInt(st.nextToken()));
        if(month < 0) month = 0;
        int year = 0;
        if(st.hasMoreTokens()) year = InformationManager.checkedParseInt(st.nextToken());
        if(year < 0) year = 0;
        calendar.set(year, month, date);
        return calendar;
    }

    /**
     * Gets the month as expected by Calendar interface.
     * @param mm	The month index.
     * @return	corresponding Calendar month constant.
     */
    private static int getMonth(int mm) {
        if(mm-1 < 0 || mm-1 > 11) return 0;
        return MONTH[mm-1];
    }

}

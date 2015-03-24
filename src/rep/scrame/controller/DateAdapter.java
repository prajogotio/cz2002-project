package rep.scrame.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Created by prajogotio on 22/3/15.
 */
public class DateAdapter {
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

    private static int getMonth(int mm) {
        if(mm-1 < 0 || mm-1 > 11) return 0;
        return MONTH[mm-1];
    }

}

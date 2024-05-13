package id.com.sonarplatform.programmingtest1.utils;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 5/14/2024 12:38 AM
@Last Modified 5/14/2024 12:38 AM
Version 1.0
*/

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private DateUtils() {
    }

    public static Date getDateFromString(String input) {
       try {
           String cleaned = input.replace("WIB", "").replace("|", "").trim();
           String[] data = cleaned.split(" ");

           int day = Integer.parseInt(data[0]);
           int month = getMonthFromString(data[1]);
           int year = Integer.parseInt(data[2]);

           String[] time = data[4].split(":");
           int hours = Integer.parseInt(time[0]);
           int minutes = Integer.parseInt(time[1]);

           Calendar calendar = Calendar.getInstance();
           calendar.set(year, month, day, hours, minutes);
           return calendar.getTime();
       } catch (Exception e) {
           return null;
       }
    }

    public static int getMonthFromString(String shortMonthName) {
        String[] shortMonthsName = new DateFormatSymbols().getShortMonths();

        for (int i = 0; i < shortMonthName.length(); i++) {
            if (shortMonthsName[i].equalsIgnoreCase(shortMonthName)) {
                return i;
            }
        }
        return 0;
    }




}

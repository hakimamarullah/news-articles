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

    /**
     * Fungsi ini digunakan untuk mentransformasikan custom string (dd MM yyyy | HH:mm WIB) ke java Date.
     * @param input input ini didapatkan ketika mengambil data dari web index (ex: 13 May 2024 | 23:00 WIB)
     * @return Date
     */
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

    /**
     * Returns the index of the given month (0-indexed)
     * @param shortMonthName (ex: Jan, Feb, etc)
     * @return int
     */
    public static int getMonthFromString(String shortMonthName) {
        String[] shortMonthsName = new DateFormatSymbols().getShortMonths();

        for (int i = 0; i < shortMonthsName.length; i++) {
            if (shortMonthsName[i].equalsIgnoreCase(shortMonthName)) {
                return i;
            }
        }
        return 0;
    }




}

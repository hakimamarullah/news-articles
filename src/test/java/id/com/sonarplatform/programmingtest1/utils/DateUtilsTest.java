package id.com.sonarplatform.programmingtest1.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void testGetDateFromString() {
        String tc1 = "13 May 2024 | 23:00 WIB";
        String tc2 = "12 Mei 2024 23:00 WIB";

        // Assert
        assertNotNull(DateUtils.getDateFromString(tc1));
        assertNull(DateUtils.getDateFromString(tc2));
    }

    @Test
    void testGetMonthFromString() {
        assertEquals(0, DateUtils.getMonthFromString("Jan"));
        assertEquals(0, DateUtils.getMonthFromString("jan"));

        assertEquals(11, DateUtils.getMonthFromString("Dec"));
    }
}
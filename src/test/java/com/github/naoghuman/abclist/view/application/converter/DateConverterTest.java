/*
 * Copyright (C) 2017 Naoghuman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.naoghuman.abclist.view.application.converter;

import com.github.naoghuman.converter.DateConverter;
import static org.junit.Assert.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

/**
 *
 * @author Naoghuman
 */
public class DateConverterTest {
    
    public DateConverterTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetDefault() {
        DateConverter result = DateConverter.getDefault();
        assertNotNull(result);
    }

    @Test
    public void testAddDays() {
        final LocalDateTime ldtNowPlus4Days = LocalDateTime.now().plusDays(4);
        
        final long millis4DaysInFuture = DateConverter.getDefault().addDays(4);
        final LocalDateTime ldt4DaysInFuture = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis4DaysInFuture), ZoneId.systemDefault());
        
        assertEquals(ldtNowPlus4Days.getDayOfYear(), ldt4DaysInFuture.getDayOfYear());
    }

    @Test
    public void testConvertDateTimeToLong() {
        String dateTime = "02-03-2004 05:06:07"; // MM-dd-yyyy HH:mm:ss
        final long result = DateConverter.getDefault().convertDateTimeToLong(dateTime, DateConverter.PATTERN__DATETIME);
        final LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(result), ZoneId.systemDefault());
        
        assertEquals(2, ldt.getMonthValue());
        assertEquals(3, ldt.getDayOfMonth());
        assertEquals(2004, ldt.getYear());
        
        assertEquals(5, ldt.getHour());
        assertEquals(6, ldt.getMinute());
        assertEquals(7, ldt.getSecond());
    }

    @Test
    public void testConvertLongToDateTime() {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateConverter.PATTERN__DATETIME);
        final String expected = localDateTime.format(formatter);
        
        final long millies = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String result = DateConverter.getDefault().convertLongToDateTime(millies, DateConverter.PATTERN__DATETIME);
        
        assertEquals(expected, result);
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetLongInPeriodFromNowToThrowsIllegalArgumentException() {
        DateConverter.getDefault().getLongInPeriodFromNowTo(-1L);
    }

    @Test
    @Ignore
    /*
    TODO what is the meaning from the method to test?
    */
    public void testGetLongInPeriodFromNowTo() {
        final LocalDateTime localDateTimeNow = LocalDateTime.now();
        final long milliesNow = localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        
        final LocalDateTime localDateTimePast = LocalDateTime.now().minusMonths(2);
        final long milliesPast = localDateTimePast.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        
        long result = DateConverter.getDefault().getLongInPeriodFromNowTo(milliesPast);
        System.out.println("now : " + milliesNow);
        System.out.println("past: " + milliesPast);
        System.out.println("re:   " + result);
        
        assertTrue(result < milliesNow);
        assertTrue(milliesPast < result);
    }

    @Test
    public void testIsDateInNewRange() {
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        long milliesNow = localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        boolean isDateInNewRange = DateConverter.getDefault().isDateInNewRange(milliesNow);
        assertTrue(isDateInNewRange);
        
        localDateTimeNow = LocalDateTime.now().minusDays(2).minusHours(23);
        milliesNow = localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        isDateInNewRange = DateConverter.getDefault().isDateInNewRange(milliesNow);
        assertTrue(isDateInNewRange);
        
        localDateTimeNow = LocalDateTime.now().minusDays(4);
        milliesNow = localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        isDateInNewRange = DateConverter.getDefault().isDateInNewRange(milliesNow);
        assertFalse(isDateInNewRange);
    }
    
}

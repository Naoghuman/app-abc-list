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

import static org.junit.Assert.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        final long result = DateConverter.getDefault().convertDateTimeToLong(dateTime, IDateConverter.PATTERN__DATETIME);
        final LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(result), ZoneId.systemDefault());
        
        assertEquals(2, ldt.getMonthValue());
        assertEquals(3, ldt.getDayOfMonth());
        assertEquals(2004, ldt.getYear());
        
        assertEquals(5, ldt.getHour());
        assertEquals(6, ldt.getMinute());
        assertEquals(7, ldt.getSecond());
    }

    @Test
    @Ignore
    public void testConvertLongToDateTime() {
        System.out.println("convertLongToDateTime");
        Long millis = null;
        String pattern = "";
        DateConverter instance = null;
        String expResult = "";
        String result = instance.convertLongToDateTime(millis, pattern);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testConvertLongToDateTimeForPerformance() {
        System.out.println("convertLongToDateTimeForPerformance");
        Long millis = null;
        String pattern = "";
        DateConverter instance = null;
        String expResult = "";
        String result = instance.convertLongToDateTimeForPerformance(millis, pattern);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testGetLongInPeriodFromNowTo() {
        System.out.println("getLongInPeriodFromNowTo");
        Long startTime = null;
        DateConverter instance = null;
        long expResult = 0L;
        long result = instance.getLongInPeriodFromNowTo(startTime);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testIsAfter() {
        System.out.println("isAfter");
        int days = 0;
        Long millis = null;
        DateConverter instance = null;
        Boolean expResult = null;
        Boolean result = instance.isAfter(days, millis);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testIsBefore() {
        System.out.println("isBefore");
        int days = 0;
        Long millis = null;
        DateConverter instance = null;
        Boolean expResult = null;
        Boolean result = instance.isBefore(days, millis);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testIsDateInNewRange() {
        System.out.println("isDateInNewRange");
        long generationTime = 0L;
        DateConverter instance = null;
        boolean expResult = false;
        boolean result = instance.isDateInNewRange(generationTime);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testIsValid() {
        System.out.println("isValid");
        String pattern = "";
        String dateTime = "";
        DateConverter instance = null;
        Boolean expResult = null;
        Boolean result = instance.isValid(pattern, dateTime);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}

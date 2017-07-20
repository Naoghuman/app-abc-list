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

import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

/**
 *
 * @author Naoghuman
 */
public final class DateConverter {
    
    public static final String PATTERN__DATE = "MM-dd-yyyy"; // NOI18N
    public static final String PATTERN__DATE__COMMENT = "EEEE, MMM d yyyy"; // NOI18N
    public static final String PATTERN__DATE__HISTORY = "EEEE, MMM d yyyy"; // NOI18N
    public static final String PATTERN__DATETIME = "MM-dd-yyyy HH:mm:ss"; // NOI18N
    public static final String PATTERN__GENERATIONTIME = "MM-dd-yyyy  HH:mm:ss"; // NOI18N
    public static final String PATTERN__TIME = "HH:mm:ss"; // NOI18N
    public static final String PATTERN__TIME_IS_EMPTY = "00:00:00"; // NOI18N
    public static final String PATTERN__TIME_WITH_MILLIS = "HH:mm:ss.SSS"; // NOI18N
    
    private static final Optional<DateConverter> instance = Optional.of(new DateConverter());

    public static final DateConverter getDefault() {
        return instance.get();
    }
    
    private static final Random RANDOM = new Random();
    
    private DateConverter() {
        
    }
    
    public Long addDays(int days) {
        final LocalDateTime localDateTime = LocalDateTime.now().plusDays(days);
        final long generationTime3DaysInFuture = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        
        return generationTime3DaysInFuture;
    }
    
    public Long convertDateTimeToLong(String dateTime, String pattern) {
        LoggerFacade.getDefault().trace(this.getClass(), String.format(
                    "Convert %s with %s to Long", dateTime, pattern)); // NOI18N
        
        try {
            final DateFormat formatter = new SimpleDateFormat(pattern);
            final Date converted = formatter.parse(dateTime);
            return converted.getTime();
        } catch (ParseException pe) {
            LoggerFacade.getDefault().error(this.getClass(), String.format(
                    "Can't convert %s with %s to Long", dateTime, pattern), pe); // NOI18N
        }
        
        return 0L;
    }
    
    public String convertLongToDateTime(Long millis, String pattern) {
        final LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        
        return localDateTime.format(formatter);
    }
    
    public long getLongInPeriodFromNowTo(Long startTime) {
        if (startTime <=0) {
            throw new IllegalArgumentException("startTime must be greater zero"); // NOI18N
        }
        
        long bits, val;
        do {
           bits = (RANDOM.nextLong() << 1) >>> 1;
           val = bits % startTime;
        } while (bits - val + (startTime - 1) < 0L);
        
        return val;
    }
    
//    public Boolean isAfter(int days, Long millis) {
//        final LocalDateTime localDateTimeNow = LocalDateTime.now();
//        localDateTimeNow.plusDays(days);
//        
//        final LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
//        
//        return localDateTime.isAfter(localDateTimeNow);
//    }
    
//    public Boolean isBefore(int days, Long millis) {
//        final LocalDateTime localDateTimeNow = LocalDateTime.now();
//        localDateTimeNow.plusDays(days);
//        
//        final LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
//        
//        return localDateTime.isBefore(localDateTimeNow);
//    }
    
    public boolean isDateInNewRange(long generationTime) {
        final LocalDateTime localDateTime = LocalDateTime.now().minusDays(3L);
        final long generationTime3DaysInPast = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        final boolean isDateInNewRange = generationTime3DaysInPast < generationTime;
        
        return isDateInNewRange;
    }
    
//    public Boolean isValid(String pattern, String dateTime) {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
//            if (pattern.equals(PATTERN__DATE)) {
//                LocalDate.parse(dateTime, formatter);
//                return Boolean.TRUE;
//            }
//            if (pattern.equals(PATTERN__TIME)) {
//                LocalTime.parse(dateTime, formatter);
//                return Boolean.TRUE;
//            }
//        } catch (DateTimeParseException ex) {
//            /* not needed */
//        }
//        
//        return Boolean.FALSE;
//    }
    
}

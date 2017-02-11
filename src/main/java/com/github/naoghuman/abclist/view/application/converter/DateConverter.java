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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

/**
 *
 * @author Naoghuman
 */
public final class DateConverter {
    
    private static final Optional<DateConverter> instance = Optional.of(new DateConverter());

    public static final DateConverter getDefault() {
        return instance.get();
    }
    
    private DateConverter() {
        
    }
    
    public boolean isDateInNewRange(long generationTime) {
        final LocalDateTime localDateTime = LocalDateTime.now().minusDays(3L);
        final long generationTime3DaysInPast = localDateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
        final boolean isDateInNewRange = generationTime3DaysInPast < generationTime;
        
        return isDateInNewRange;
    }
    
}

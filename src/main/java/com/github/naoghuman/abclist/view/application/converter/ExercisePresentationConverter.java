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

import com.github.naoghuman.abclist.model.Exercise;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Naoghuman
 */
public final class ExercisePresentationConverter implements IPresentationConverter {
    
    private final Date date = new Date();
    private final StringBuilder representation = new StringBuilder();
    private final StringBuilder tooltip = new StringBuilder();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // NOI18N

    public ExercisePresentationConverter(Exercise exercise) {
        this.convert(exercise);
    }
    
    @Override
    public void convert(Object entity) throws IllegalArgumentException {
        // Check instanceof
        if (!(entity instanceof Exercise)) {
            throw new IllegalArgumentException("Object [entity] must from class [Exercise]!"); // NOI18N
        }
        
        // Convert the representation
        final Exercise exercise = (Exercise) entity;
        date.setTime(exercise.getGenerationTime());
        representation.append(simpleDateFormat.format(date));
        representation.append(" ("); // NOI18N
        representation.append(exercise.isReady() ? "v" : "-"); // NOI18N
        representation.append(")"); // NOI18N
        
        // Convert the tooltip
//        tooltip.append(""); // NOI18N
    }

    @Override
    public String getRepresentation() {
        return representation.toString();
    }

    @Override
    public String getTooltip() {
        return tooltip.toString();
    }
    
}

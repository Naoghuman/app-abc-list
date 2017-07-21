/*
 * Copyright (C) 2017 PRo
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

import com.github.naoghuman.converter.ExercisePresentationConverter;
import com.github.naoghuman.abclist.model.Exercise;
import com.github.naoghuman.abclist.model.ModelProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author PRo
 */
public class ExercisePresentationConverterTest {
    
    public ExercisePresentationConverterTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvert() {
        ExercisePresentationConverter e = new ExercisePresentationConverter(null);
    }

    @Test
    public void testGetPresentationEndWithFalse() {
        /*
        // Convert the representation
        final Exercise exercise = (Exercise) entity;
        date.setTime(exercise.getGenerationTime());
        presentation.append(simpleDateFormat.format(date));
        presentation.append(" ("); // NOI18N
        presentation.append(exercise.isReady() ? "v" : "-"); // NOI18N
        presentation.append(")"); // NOI18N
        */
        Exercise e = ModelProvider.getDefault().getExercise();
        ExercisePresentationConverter ec = new ExercisePresentationConverter(e);
        
        String endWith = "(-)";
        assertTrue(ec.getPresentation().endsWith(endWith));
    }

    @Test
    public void testGetPresentationEndWithTrue() {
        Exercise e = ModelProvider.getDefault().getExercise();
        e.setReady(true);
        
        ExercisePresentationConverter ec = new ExercisePresentationConverter(e);
        
        String endWith = "(v)";
        assertTrue(ec.getPresentation().endsWith(endWith));
    }

    @Test
    public void testGetTooltipIsEmpty() {
        // Convert the tooltip
//        tooltip.append(""); // NOI18N

        Exercise e = ModelProvider.getDefault().getExercise();
        ExercisePresentationConverter ec = new ExercisePresentationConverter(e);
        
        assertEquals("", ec.getTooltip());

    }
    
}

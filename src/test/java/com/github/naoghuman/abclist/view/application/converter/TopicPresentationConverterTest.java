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

import com.github.naoghuman.converter.TopicPresentationConverter;
import com.github.naoghuman.abclist.model.ModelProvider;
import com.github.naoghuman.abclist.model.Topic;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Naoghuman
 */
public class TopicPresentationConverterTest {
    
    public TopicPresentationConverterTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvert() {
        TopicPresentationConverter t = new TopicPresentationConverter(null);
    }

    @Test
    public void testGetPresentationWith0Exercises() {
        Topic topic = ModelProvider.getDefault().getTopic("hello");
        topic.setGenerationTime(0L);
        
        TopicPresentationConverter topicPresentationConverter = new TopicPresentationConverter(topic);
        assertEquals("hello (0)", topicPresentationConverter.getPresentation());
    }

    @Test
    public void testGetPresentationWith11Exercises() {
        Topic topic = ModelProvider.getDefault().getTopic("hello");
        topic.setExercises(11);
        topic.setGenerationTime(0L);
        
        TopicPresentationConverter topicPresentationConverter = new TopicPresentationConverter(topic);
        assertEquals("hello (11)", topicPresentationConverter.getPresentation());
    }

    @Test
    public void testGetPresentationWithNewPrefix() {
        Topic topic = ModelProvider.getDefault().getTopic("hello");
        
        // in range means > 3 days
        LocalDateTime localDateTimeNow = LocalDateTime.now().minusDays(2).minusHours(23);
        long generationTime = localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        topic.setGenerationTime(generationTime);
        
        TopicPresentationConverter topicPresentationConverter = new TopicPresentationConverter(topic);
        assertEquals("New | hello (0)", topicPresentationConverter.getPresentation());
    }

    @Test
    public void testGetTooltipWith0Exercises() {
        Topic topic = ModelProvider.getDefault().getTopic("hello");
        topic.setGenerationTime(0L);
        
        TopicPresentationConverter topicPresentationConverter = new TopicPresentationConverter(topic);
        assertEquals("Topic 'hello' contains 0 exercises.", topicPresentationConverter.getTooltip());
    }

    @Test
    public void testGetTooltipWith15Exercises() {
        Topic topic = ModelProvider.getDefault().getTopic("hello");
        topic.setExercises(15);
        topic.setGenerationTime(0L);
        
        TopicPresentationConverter topicPresentationConverter = new TopicPresentationConverter(topic);
        assertEquals("Topic 'hello' contains 15 exercises.", topicPresentationConverter.getTooltip());
    }
    
}

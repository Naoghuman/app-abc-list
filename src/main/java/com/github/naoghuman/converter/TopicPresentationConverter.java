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
package com.github.naoghuman.converter;

import com.github.naoghuman.abclist.configuration.IPropertiesConfiguration;
import com.github.naoghuman.abclist.i18n.Properties;
import com.github.naoghuman.abclist.model.Topic;

/**
 *
 * @author Naoghuman
 */
public final class TopicPresentationConverter implements IPresentationConverter, IPropertiesConfiguration {
    
    public static String getI18nMsgFoundedEntities(String propertyKey, int countFoundedEntities) {
        final String text = Properties.getPropertyForConverter(propertyKey);
        final String textFoundedEntities = text.replaceFirst(STRING_DEFAULT_REGEX, String.valueOf(countFoundedEntities));
        
        return textFoundedEntities;
    }
    
    private final StringBuilder presentation = new StringBuilder();
    private final StringBuilder tooltip = new StringBuilder();
    
    public TopicPresentationConverter(Topic topic) {
        this.convert(topic);
    }

    @Override
    public void convert(Object entity) throws IllegalArgumentException {
        // Check instanceof
        if (!(entity instanceof Topic)) {
            throw new IllegalArgumentException("Object [entity] must instance from class [Topic]!"); // NOI18N
        }
        
        // Convert the representation
        final Topic topic = (Topic) entity;
        if (DateConverter.getDefault().isDateInNewRange(topic.getGenerationTime())) {
            presentation.append("New | "); // NOI18N
        }
        presentation.append(topic.getTitle());
        presentation.append(" ("); // NOI18N
        presentation.append(topic.getExercises());
        presentation.append(")"); // NOI18N
        
        // Convert the tooltip
        tooltip.append("Topic '"); // NOI18N
        tooltip.append(topic.getTitle());
        tooltip.append("' contains "); // NOI18N
        tooltip.append(topic.getExercises());
        tooltip.append(" exercises."); // NOI18N
    }

    @Override
    public String getPresentation() {
        return presentation.toString();
    }

    @Override
    public String getTooltip() {
        return tooltip.toString();
    }
    
}

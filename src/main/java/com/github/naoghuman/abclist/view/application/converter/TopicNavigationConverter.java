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

import com.github.naoghuman.abclist.model.Topic;

/**
 *
 * @author Naoghuman
 */
public final class TopicNavigationConverter implements INavigationConverter {
    
    private final StringBuilder representation = new StringBuilder();
    private final StringBuilder tooltip = new StringBuilder();
    
    public TopicNavigationConverter(Topic topic) {
        this.convert(topic);
    }

    @Override
    public void convert(Object entity) throws IllegalArgumentException {
        // Check instanceof
        if (!(entity instanceof Topic)) {
            throw new IllegalArgumentException("Object [entity] must from class [Topic]!"); // NOI18N
        }
        
        // Convert the representation
        final Topic topic = (Topic) entity;
        if (DateConverter.getDefault().isDateInNewRange(topic.getGenerationTime())) {
            representation.append("New | "); // NOI18N
        }
        representation.append(topic.getTitle());
        representation.append(" ("); // NOI18N
        representation.append(topic.getExercises());
        representation.append(")"); // NOI18N
        
        // Convert the tooltip
        tooltip.append(""); // NOI18N
        tooltip.append("Topic '"); // NOI18N
        tooltip.append(topic.getTitle());
        tooltip.append("' contains "); // NOI18N
        tooltip.append(topic.getExercises());
        tooltip.append(" exercises."); // NOI18N
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

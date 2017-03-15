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
package com.github.naoghuman.abclist.model;

import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Naoghuman
 */
public enum LinkMappingType {
    
    EXERCISE    (Exercise.class.getSimpleName()),
    LINK        (Link.class.getSimpleName()),
    NOT_DEFINED ("NotDefined"), // NOI18N
    TERM        (Term.class.getSimpleName()),
    TOPIC       (Topic.class.getSimpleName());
    
    public static Optional<LinkMappingType> getType(final String type) {
        Optional<LinkMappingType> optional = Optional.empty();
        for (LinkMappingType value : values()) {
            if (value.getType().equals(type)) {
                optional = Optional.of(value);
                break;
            }
        }
        
        return optional;
    }
    
    private final String type;
    
    LinkMappingType(final String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", this.getType()) // NOI18N
                .toString();
    }
    
}

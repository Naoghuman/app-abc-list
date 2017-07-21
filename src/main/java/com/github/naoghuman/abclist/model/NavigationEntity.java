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

import com.github.naoghuman.abclist.view.application.Navigation;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.github.naoghuman.converter.IPresentationConverter;

/**
 *
 * @author Naoghuman
 */
public final class NavigationEntity implements Comparable<NavigationEntity> {
    
    private final IPresentationConverter entityConverter;
    private final Navigation navigation;
    
    public NavigationEntity(Navigation navigation, IPresentationConverter entityConverter) {
        this.navigation = navigation;
        this.entityConverter = entityConverter;
    }

    public IPresentationConverter getEntityConverter() {
        return entityConverter;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    @Override
    public int compareTo(NavigationEntity other) {
        return new CompareToBuilder()
                .append(this.getNavigation().getEntityId(), other.getNavigation().getEntityId())
                .append(this.getNavigation().getNavigationType(), other.getNavigation().getNavigationType())
                .toComparison();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (
                obj == null
                || this.getClass() != obj.getClass()
	) {
            return false;
        }
        
        final NavigationEntity other = (NavigationEntity) obj;
        return new EqualsBuilder()
                .append(this.getNavigation().getEntityId(), other.getNavigation().getEntityId())
                .append(this.getNavigation().getNavigationType(), other.getNavigation().getNavigationType())
                .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getNavigation().getEntityId())
                .append(this.getNavigation().getNavigationType())
                .toHashCode();
    }
	
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("EntityId", this.getNavigation().getEntityId()) // NOI18N
                .append("NavigationType", this.getNavigation().getNavigationType()) // NOI18N
                .toString();
    }
    
}

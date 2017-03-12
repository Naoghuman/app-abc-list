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

import com.github.naoghuman.abclist.configuration.IDefaultConfiguration;
import com.github.naoghuman.abclist.configuration.ILinkMappingConfiguration;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author Naoghuman
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = ILinkMappingConfiguration.ENTITY__TABLE_NAME__LINK_MAPPING)
@NamedQueries({
    @NamedQuery(
            name = ILinkMappingConfiguration.NAMED_QUERY__NAME__COUNT_ALL_LINK_MAPPINGS_WITH_LINK_ID,
            query = ILinkMappingConfiguration.NAMED_QUERY__QUERY__COUNT_ALL_LINK_MAPPINGS_WITH_LINK_ID),
    @NamedQuery(
            name = ILinkMappingConfiguration.NAMED_QUERY__NAME__FIND_ALL_LINK_MAPPINGS_WITH_PARENT_ID,
            query = ILinkMappingConfiguration.NAMED_QUERY__QUERY__FIND_ALL_LINK_MAPPINGS_WITH_PARENT_ID),
    @NamedQuery(
            name = ILinkMappingConfiguration.NAMED_QUERY__NAME__FIND_LINK_MAPPING_WITH_PARENT_ID_AND_LINK_ID,
            query = ILinkMappingConfiguration.NAMED_QUERY__QUERY__FIND_LINK_MAPPING_WITH_PARENT_ID_AND_LINK_ID)
})
public class LinkMapping implements Comparable<LinkMapping>, Externalizable, IDefaultConfiguration, ILinkMappingConfiguration {
        
    public LinkMapping() {
        this(DEFAULT_ID);
    }
    
    public LinkMapping(long id) {
        this(id, DEFAULT_ID, DEFAULT_ID);
    }
    
    public LinkMapping(long linkId, long parentId) {
        this(DEFAULT_ID, linkId, parentId);
    }
    
    public LinkMapping(long id, long linkId, long parentId) {
        this.setId(id);
        this.setLinkId(linkId);
        this.setParentId(parentId);
    }
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = DEFAULT_ID;

    @Id
    @Column(name = LINK_MAPPING__COLUMN_NAME__ID)
    public long getId() {
        if (idProperty == null) {
            return _id;
        } else {
            return idProperty.get();
        }
    }

    public final void setId(long id) {
        if (idProperty == null) {
            _id = id;
        } else {
            idProperty.set(id);
        }
    }

    public LongProperty idProperty() {
        if (idProperty == null) {
            idProperty = new SimpleLongProperty(this, LINK_MAPPING__COLUMN_NAME__ID, _id);
        }
        
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
    
    // START  LINK-ID ----------------------------------------------------------
    private LongProperty linkIdProperty;
    private long _linkId = DEFAULT_ID;

    @Id
    @Column(name = LINK_MAPPING__COLUMN_NAME__LINK_ID)
    public long getLinkId() {
        if (linkIdProperty == null) {
            return _linkId;
        } else {
            return linkIdProperty.get();
        }
    }

    public final void setLinkId(long linkId) {
        if (linkIdProperty == null) {
            _linkId = linkId;
        } else {
            linkIdProperty.set(linkId);
        }
    }

    public LongProperty linkIdProperty() {
        if (linkIdProperty == null) {
            linkIdProperty = new SimpleLongProperty(this, LINK_MAPPING__COLUMN_NAME__LINK_ID, _linkId);
        }
        
        return linkIdProperty;
    }
    // END  LINK-ID ------------------------------------------------------------
    
    // START  PARENT-ID --------------------------------------------------------
    private LongProperty parentIdProperty;
    private long _parentId = DEFAULT_ID;

    @Id
    @Column(name = LINK_MAPPING__COLUMN_NAME__PARENT_ID)
    public long getParentId() {
        if (parentIdProperty == null) {
            return _parentId;
        } else {
            return parentIdProperty.get();
        }
    }

    public final void setParentId(long parentId) {
        if (parentIdProperty == null) {
            _parentId = parentId;
        } else {
            parentIdProperty.set(parentId);
        }
    }

    public LongProperty parentIdProperty() {
        if (parentIdProperty == null) {
            parentIdProperty = new SimpleLongProperty(this, LINK_MAPPING__COLUMN_NAME__PARENT_ID, _parentId);
        }
        
        return parentIdProperty;
    }
    // END  PARENT-ID ----------------------------------------------------------
	
    @Override
    public int compareTo(LinkMapping other) {
        return new CompareToBuilder()
                .append(this.getParentId(), other.getParentId())
                .append(this.getLinkId(), other.getLinkId())
                .append(this.getId(), other.getId())
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
        
        final LinkMapping other = (LinkMapping) obj;
        return new EqualsBuilder()
                .append(this.getId(), other.getId())
                .append(this.getParentId(), other.getParentId())
                .append(this.getLinkId(), other.getLinkId())
                .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getId())
                .append(this.getParentId())
                .append(this.getLinkId())
                .toHashCode();
    }
	
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(LINK_MAPPING__COLUMN_NAME__ID, this.getId())
                .append(LINK_MAPPING__COLUMN_NAME__PARENT_ID, this.getParentId())
                .append(LINK_MAPPING__COLUMN_NAME__LINK_ID, this.getLinkId())
                .toString();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getParentId());
        out.writeLong(this.getLinkId());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setParentId(in.readLong());
        this.setLinkId(in.readLong());
    }
    
}

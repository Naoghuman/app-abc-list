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
import java.util.Optional;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
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
            name = ILinkMappingConfiguration.NAMED_QUERY__NAME__FIND_ALL_WITH_PARENTTYPE_AND_CHILDTYPE,
            query = ILinkMappingConfiguration.NAMED_QUERY__QUERY__FIND_ALL_WITH_PARENTTYPE_AND_CHILDTYPE)
})
public class LinkMapping implements Comparable<LinkMapping>, Externalizable, IDefaultConfiguration, ILinkMappingConfiguration {
        
    public LinkMapping() {
        this(DEFAULT_ID);
    }
    
    public LinkMapping(final long id) {
        this(id, DEFAULT_ID, DEFAULT_ID);
    }
    
    public LinkMapping(final long parentId, final long childId) {
        this(DEFAULT_ID, parentId, childId);
    }
    
    public LinkMapping(final long id, final long parentId, final long childId) {
        this.setId(id);
        this.setParentId(parentId);
        this.setChildId(childId);
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

    public final void setParentId(final long parentId) {
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
    
    // START  PARENT TYPE ------------------------------------------------------
    private ObjectProperty parentTypeProperty = null;
    private LinkMappingType _parentType;
    
    @Column(name = LINK_MAPPING__COLUMN_NAME__PARENT_TYPE)
    public LinkMappingType getParentType() {
        if (parentTypeProperty == null) {
            return _parentType;
        } else {
            return (LinkMappingType) parentTypeProperty.get();
        }
    }
    
    public void setParentType(final LinkMappingType parentType) {
        if (parentTypeProperty == null) {
            _parentType = parentType;
        } else {
            parentTypeProperty.set(parentType);
        }
    }
    
    public ObjectProperty parentTypeProperty() {
        if (parentTypeProperty == null) {
            parentTypeProperty = new SimpleObjectProperty(this, LINK_MAPPING__COLUMN_NAME__PARENT_TYPE, _parentType);
        }
        
        return parentTypeProperty;
    }
    // END  PARENT TYPE --------------------------------------------------------
    
    // START  CHILD-ID ---------------------------------------------------------
    private LongProperty childIdProperty;
    private long _childId = DEFAULT_ID;

    @Id
    @Column(name = LINK_MAPPING__COLUMN_NAME__CHILD_ID)
    public long getChildId() {
        if (childIdProperty == null) {
            return _childId;
        } else {
            return childIdProperty.get();
        }
    }

    public final void setChildId(final long childId) {
        if (childIdProperty == null) {
            _childId = childId;
        } else {
            childIdProperty.set(childId);
        }
    }

    public LongProperty childIdProperty() {
        if (childIdProperty == null) {
            childIdProperty = new SimpleLongProperty(this, LINK_MAPPING__COLUMN_NAME__CHILD_ID, _childId);
        }
        
        return childIdProperty;
    }
    // END  CHILD-ID -----------------------------------------------------------
    
    // START  CHILD TYPE -------------------------------------------------------
    private ObjectProperty childTypeProperty = null;
    private LinkMappingType _childType;
    
    @Column(name = LINK_MAPPING__COLUMN_NAME__CHILD_TYPE)
    public LinkMappingType getChildType() {
        if (childTypeProperty == null) {
            return _childType;
        } else {
            return (LinkMappingType) childTypeProperty.get();
        }
    }
    
    public void setChildType(final LinkMappingType childType) {
        if (childTypeProperty == null) {
            _childType = childType;
        } else {
            childTypeProperty.set(childType);
        }
    }
    
    public ObjectProperty childTypeProperty() {
        if (childTypeProperty == null) {
            childTypeProperty = new SimpleObjectProperty(this, LINK_MAPPING__COLUMN_NAME__CHILD_TYPE, _childType);
        }
        
        return childTypeProperty;
    }
    // END  CHILD TYPE ---------------------------------------------------------
	
    @Override
    public int compareTo(LinkMapping other) {
        return new CompareToBuilder()
                .append(this.getParentId(),             other.getParentId())
                .append(this.getChildId(),              other.getChildId())
                .append(this.getParentType().getType(), other.getParentType().getType())
                .append(this.getChildType().getType(),  other.getChildType().getType())
                .append(this.getId(),                   other.getId())
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
                .append(this.getId(),                   other.getId())
                .append(this.getParentId(),             other.getParentId())
                .append(this.getParentType().getType(), other.getParentType().getType())
                .append(this.getChildId(),              other.getChildId())
                .append(this.getChildType().getType(),  other.getChildType().getType())
                .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getId())
                .append(this.getParentId())
                .append(this.getParentType().getType())
                .append(this.getChildId())
                .append(this.getChildType().getType())
                .toHashCode();
    }
	
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(LINK_MAPPING__COLUMN_NAME__ID,          this.getId())
                .append(LINK_MAPPING__COLUMN_NAME__PARENT_ID,   this.getParentId())
                .append(LINK_MAPPING__COLUMN_NAME__PARENT_TYPE, this.getParentType().toString())
                .append(LINK_MAPPING__COLUMN_NAME__CHILD_ID,    this.getChildId())
                .append(LINK_MAPPING__COLUMN_NAME__CHILD_TYPE,  this.getChildType().toString())
                .toString();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong  (this.getId());
        out.writeLong  (this.getParentId());
        out.writeObject(this.getParentType().getType());
        out.writeLong  (this.getChildId());
        out.writeObject(this.getChildType().getType());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId      (in.readLong());
        this.setParentId(in.readLong());
        
        final Optional<LinkMappingType> primaryType = LinkMappingType.getType(String.valueOf(in.readObject()));
        this.setParentType(primaryType.isPresent() ? primaryType.get() : LinkMappingType.NOT_DEFINED);
        
        this.setChildId(in.readLong());
        
        final Optional<LinkMappingType> childType = LinkMappingType.getType(String.valueOf(in.readObject()));
        this.setChildType(childType.isPresent() ? childType.get() : LinkMappingType.NOT_DEFINED);
    }
    
}

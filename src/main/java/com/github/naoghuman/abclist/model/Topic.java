/*
 * Copyright (C) 2016 Naoghuman
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
import com.github.naoghuman.abclist.configuration.ITopicConfiguration;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = ITopicConfiguration.ENTITY__TABLE_NAME__TOPIC)
@NamedQueries({
    @NamedQuery(
            name = ITopicConfiguration.NAMED_QUERY__NAME__FIND_ALL,
            query = ITopicConfiguration.NAMED_QUERY__QUERY__FIND_ALL)
})
public class Topic implements Comparable<Topic>, Externalizable, IDefaultConfiguration, ITopicConfiguration {
    
    public Topic() {
        this(SIGN__EMPTY);
    }

    public Topic(String title) {
        this(DEFAULT_ID, title);
    }

    public Topic(long id, String title) {
        this(id, DEFAULT_ID, title);
    }

    public Topic(long id, long parentId, String title) {
        this.init(id, parentId, title);
    }
    
    private void init(long id, long parentId, String title) {
        this.setId(id);
        this.setParentId(parentId);
        this.setTitle(title);
        
        markAsChangedProperty = new SimpleBooleanProperty(Boolean.FALSE);
    }
       
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = DEFAULT_ID;

    @Id
    @Column(name = TOPIC__COLUMN_NAME__ID)
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
            idProperty = new SimpleLongProperty(this, TOPIC__COLUMN_NAME__ID, _id);
        }
        
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
       
    // START  PARENT-ID --------------------------------------------------------
    // DEFAULT_ID means this [Topic] hasn't another [Topic] as parent.
    private LongProperty parentIdProperty;
    private long _parentId = DEFAULT_ID;

    @Column(name = TOPIC__COLUMN_NAME__ID)
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
            parentIdProperty = new SimpleLongProperty(this, TOPIC__COLUMN_NAME__ID, _parentId);
        }
        
        return parentIdProperty;
    }
    // END  PARENT-ID ----------------------------------------------------------
	
    // START  GENERATIONTIME ---------------------------------------------------
    private LongProperty generationTimeProperty;
    private long _generationTime = System.currentTimeMillis();

    @Column(name = TOPIC__COLUMN_NAME__GENERATION_TIME)
    public long getGenerationTime() {
        if (generationTimeProperty == null) {
            return _generationTime;
        } else {
            return generationTimeProperty.get();
        }
    }

    public final void setGenerationTime(long generationTime) {
        if (generationTimeProperty == null) {
            _generationTime = generationTime;
        } else {
            generationTimeProperty.set(generationTime);
        }
    }

    public LongProperty generationTimeProperty() {
        if (generationTimeProperty == null) {
            generationTimeProperty = new SimpleLongProperty(this, TOPIC__COLUMN_NAME__GENERATION_TIME, _generationTime);
        }
        return generationTimeProperty;
    }
    // END  GENERATIONTIME -----------------------------------------------------
    
    // START  DESCRIPTION ------------------------------------------------------
    private StringProperty descriptionProperty = null;
    private String _description = SIGN__EMPTY;
    
    @Column(name = TOPIC__COLUMN_NAME__DESCRIPTION)
    public String getDescription() {
        if (descriptionProperty == null) {
            return _description;
        } else {
            return descriptionProperty.get();
        }
    }
    
    public void setDescription(String description) {
        if (descriptionProperty == null) {
            _description = description;
        } else {
            descriptionProperty.set(description);
        }
    }
    
    public StringProperty descriptionProperty() {
        if (descriptionProperty == null) {
            descriptionProperty = new SimpleStringProperty(this, TOPIC__COLUMN_NAME__DESCRIPTION, _description);
        }
        
        return descriptionProperty;
    }
    // END  DESCRIPTION --------------------------------------------------------
    
    // START  TITLE ------------------------------------------------------------
    private StringProperty titleProperty = null;
    private String _title = SIGN__EMPTY;
    
    @Column(name = TOPIC__COLUMN_NAME__TITLE)
    public String getTitle() {
        if (titleProperty == null) {
            return _title;
        } else {
            return titleProperty.get();
        }
    }
    
    public void setTitle(String title) {
        if (titleProperty == null) {
            _title = title;
        } else {
            titleProperty.set(title);
        }
    }
    
    public StringProperty titleProperty() {
        if (titleProperty == null) {
            titleProperty = new SimpleStringProperty(this, TOPIC__COLUMN_NAME__TITLE, _title);
        }
        
        return titleProperty;
    }
    // END  TITLE --------------------------------------------------------------
    
    // START  EXERCISES-SIZE ---------------------------------------------------
    private transient int exercisesSize = 0;
    
    @Transient
    public int getExercises() {
        return exercisesSize;
    }
    
    public void setExercises(int exercisesSize) {
        this.exercisesSize = exercisesSize;
    }
    // END  EXERCISES-SIZE -----------------------------------------------------
    
    // START  MARK-AS-CHANGED --------------------------------------------------
    private transient BooleanProperty markAsChangedProperty = null;

    @Transient
    public boolean isMarkAsChanged() {
        return markAsChangedProperty.getValue();
    }
    
    public BooleanProperty markAsChangedProperty() {
        return markAsChangedProperty;
    }
    
    public void setMarkAsChanged(boolean isMarkAsChanged) {
        markAsChangedProperty.setValue(isMarkAsChanged);
    }
    // END  MARK-AS-CHANGED ----------------------------------------------------
    
    @Override
    public int compareTo(Topic other) {
        return new CompareToBuilder()
                .append(this.getTitle(), other.getTitle())
                .append(this.getId(), other.getId())
                .append(this.getParentId(), other.getParentId())
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
        
        final Topic other = (Topic) obj;
        return new EqualsBuilder()
                .append(this.getId(), other.getId())
                .append(this.getParentId(), this.getParentId())
                .append(this.getGenerationTime(), other.getGenerationTime())
                .append(this.getTitle(), other.getTitle())
                .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getId())
                .append(this.getParentId())
                .append(this.getGenerationTime())
                .append(this.getTitle())
                .toHashCode();
    }
	
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(TOPIC__COLUMN_NAME__ID, this.getId())
                .append(TOPIC__COLUMN_NAME__PARENT_ID, this.getId())
                .append(TOPIC__COLUMN_NAME__GENERATION_TIME, this.getGenerationTime())
                .append(TOPIC__COLUMN_NAME__TITLE, this.getTitle())
                .append(TOPIC__COLUMN_NAME__DESCRIPTION, this.getDescription())
                .toString();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getParentId());
        out.writeLong(this.getGenerationTime());
        out.writeObject(this.getDescription());
        out.writeObject(this.getTitle());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setParentId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setDescription(String.valueOf(in.readObject()));
        this.setTitle(String.valueOf(in.readObject()));
    }
    
}

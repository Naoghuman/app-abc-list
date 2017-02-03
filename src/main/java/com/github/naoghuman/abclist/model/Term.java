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
import com.github.naoghuman.abclist.configuration.ITermConfiguration;
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
@Table(name = ITermConfiguration.ENTITY__TABLE_NAME__TERM)
@NamedQueries({
    @NamedQuery(
            name = ITermConfiguration.NAMED_QUERY__NAME__FIND_ALL,
            query = ITermConfiguration.NAMED_QUERY__QUERY__FIND_ALL),
    @NamedQuery(
            name = ITermConfiguration.NAMED_QUERY__NAME__FIND_ALL_WITH_TITLE,
            query = ITermConfiguration.NAMED_QUERY__QUERY__FIND_ALL_WITH_TITLE)
})
public class Term implements Comparable<Term>, Externalizable, IDefaultConfiguration, ITermConfiguration {
    
    public Term() {
        this(SIGN__EMPTY);
    }

    public Term(String title) {
        this(DEFAULT_ID, System.currentTimeMillis(), title);
    }

    public Term(long id, String title) {
        this(id, System.currentTimeMillis(), title);
    }

    public Term(long id, long generationTime, String title) {
        this.init(id, generationTime, title);
    }
    
    private void init(long id, long generationTime, String title) {
        this.setId(id);
        this.setGenerationTime(generationTime);
        this.setTitle(title);
        
        markAsChangedProperty = new SimpleBooleanProperty(Boolean.FALSE);
    }
       
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = DEFAULT_ID;

    @Id
    @Column(name = TERM__COLUMN_NAME__ID)
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
            idProperty = new SimpleLongProperty(this, TERM__COLUMN_NAME__ID, _id);
        }
        
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
	
    // START  GENERATIONTIME ---------------------------------------------------
    private LongProperty generationTimeProperty;
    private long _generationTime = System.currentTimeMillis();

    @Column(name = TERM__COLUMN_NAME__GENERATION_TIME)
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
            generationTimeProperty = new SimpleLongProperty(this, TERM__COLUMN_NAME__GENERATION_TIME, _generationTime);
        }
        return generationTimeProperty;
    }
    // END  GENERATIONTIME -----------------------------------------------------
    
    // START  DESCRIPTION ------------------------------------------------------
    private StringProperty descriptionProperty = null;
    private String _description = SIGN__EMPTY;
    
    @Column(name = TERM__COLUMN_NAME__DESCRIPTION)
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
            descriptionProperty = new SimpleStringProperty(this, TERM__COLUMN_NAME__DESCRIPTION, _description);
        }
        
        return descriptionProperty;
    }
    // END  DESCRIPTION --------------------------------------------------------
    
    // START  TITLE ------------------------------------------------------------
    private StringProperty titleProperty = null;
    private String _title = SIGN__EMPTY;
    
    @Column(name = TERM__COLUMN_NAME__TITLE)
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
            titleProperty = new SimpleStringProperty(this, TERM__COLUMN_NAME__TITLE, _title);
        }
        
        return titleProperty;
    }
    // END  TITLE --------------------------------------------------------------
    
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
    
    public void copy(Term other) {
        this.setDescription(other.getDescription());
        this.setGenerationTime(other.getGenerationTime());
        this.setId(other.getId());
        this.setTitle(other.getTitle());
    }
    
    @Override
    public int compareTo(Term other) {
        return new CompareToBuilder()
                .append(this.getTitle(), other.getTitle())
                .append(this.getId(), other.getId())
                .append(this.getGenerationTime(), other.getGenerationTime())
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
        
        final Term other = (Term) obj;
        return new EqualsBuilder()
                .append(this.getTitle(), other.getTitle())
                .append(this.getId(), other.getId())
                .append(this.getGenerationTime(), other.getGenerationTime())
                .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getTitle())
                .append(this.getId())
                .append(this.getGenerationTime())
                .toHashCode();
    }
	
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(TERM__COLUMN_NAME__ID, this.getId())
                .append(TERM__COLUMN_NAME__GENERATION_TIME, this.getGenerationTime())
                .append(TERM__COLUMN_NAME__TITLE, this.getTitle())
                .append(TERM__COLUMN_NAME__DESCRIPTION, this.getDescription())
                .toString();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getGenerationTime());
        out.writeObject(this.getDescription());
        out.writeObject(this.getTitle());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setDescription(String.valueOf(in.readObject()));
        this.setTitle(String.valueOf(in.readObject()));
    }
    
}

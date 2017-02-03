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
import com.github.naoghuman.abclist.configuration.IExerciseConfiguration;
import com.github.naoghuman.abclist.view.exercise.ETime;
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
@Table(name = IExerciseConfiguration.ENTITY__TABLE_NAME__EXERCISE)
@NamedQueries({
    @NamedQuery(
            name = IExerciseConfiguration.NAMED_QUERY__NAME__FIND_ALL_WITH_TOPIC_ID,
            query = IExerciseConfiguration.NAMED_QUERY__QUERY__FIND_ALL_WITH_TOPIC_ID)
})
public class Exercise implements Comparable<Exercise>, Externalizable, IDefaultConfiguration, IExerciseConfiguration {
    
    public Exercise() {
        this(DEFAULT_ID);
    }
    
    public Exercise(long id) {
        this(id, DEFAULT_ID);
    }
    
    public Exercise(long id, long topicId) {
        this(id, topicId, System.currentTimeMillis());
    }
    
    public Exercise(long id, long topicId, long generationTime) {
        this(id, topicId, generationTime, false, false);
    }
    
    public Exercise(long id, long topicId, long generationTime, boolean consolidated, boolean ready) {
        this.init(id, topicId, generationTime, consolidated, ready);
    }
    
    private void init(long id, long topicId, long generationTime, boolean consolidated, boolean ready) {
        this.setId(id);
        this.setTopicId(topicId);
        this.setGenerationTime(generationTime);
        this.setConsolidated(consolidated);
        this.setReady(ready);
    }
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = DEFAULT_ID;

    @Id
    @Column(name = EXERCISE__COLUMN_NAME__ID)
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
            idProperty = new SimpleLongProperty(this, EXERCISE__COLUMN_NAME__ID, _id);
        }
        
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
    
    // START  TOPIC-ID ---------------------------------------------------------
    private LongProperty topicIdProperty;
    private long _topicId = DEFAULT_ID;

    @Column(name = EXERCISE__COLUMN_NAME__TOPIC_ID)
    public long getTopicId() {
        if (topicIdProperty == null) {
            return _topicId;
        } else {
            return topicIdProperty.get();
        }
    }

    public final void setTopicId(long topicId) {
        if (topicIdProperty == null) {
            _topicId = topicId;
        } else {
            topicIdProperty.set(topicId);
        }
    }

    public LongProperty topicIdProperty() {
        if (topicIdProperty == null) {
            topicIdProperty = new SimpleLongProperty(this, EXERCISE__COLUMN_NAME__TOPIC_ID, _topicId);
        }
        
        return topicIdProperty;
    }
    // END  TOPIC-ID -----------------------------------------------------------
	
    // START  GENERATIONTIME ---------------------------------------------------
    private LongProperty generationTimeProperty;
    private long _generationTime = System.currentTimeMillis();

    @Column(name = EXERCISE__COLUMN_NAME__GENERATION_TIME)
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
            generationTimeProperty = new SimpleLongProperty(this, EXERCISE__COLUMN_NAME__GENERATION_TIME, _generationTime);
        }
        return generationTimeProperty;
    }
    // END  GENERATIONTIME -----------------------------------------------------
	
    // START  FINISHEDTIME -----------------------------------------------------
    private LongProperty finishedTimeProperty;
    private long _finishedTime = System.currentTimeMillis();

    @Column(name = EXERCISE__COLUMN_NAME__FINISHED_TIME)
    public long getFinishedTime() {
        if (finishedTimeProperty == null) {
            return _finishedTime;
        } else {
            return finishedTimeProperty.get();
        }
    }

    public final void setFinishedTime(long finishedTime) {
        if (finishedTimeProperty == null) {
            _finishedTime = finishedTime;
        } else {
            finishedTimeProperty.set(finishedTime);
        }
    }

    public LongProperty finishedTimeProperty() {
        if (finishedTimeProperty == null) {
            finishedTimeProperty = new SimpleLongProperty(this, EXERCISE__COLUMN_NAME__FINISHED_TIME, _finishedTime);
        }
        return finishedTimeProperty;
    }
    // END  FINISHEDTIME -------------------------------------------------------
    
    // START  CONSOLIDATED -----------------------------------------------------
    private BooleanProperty consolidatedProperty = null;
    private boolean _consolidated = false;
    
    @Column(name = EXERCISE__COLUMN_NAME__CONSOLIDATED)
    public boolean isConsolidated() {
        if (consolidatedProperty == null) {
            return _consolidated;
        } else {
            return consolidatedProperty.get();
        }
    }
    
    public void setConsolidated(boolean consolidated) {
        if (consolidatedProperty == null) {
            _consolidated = consolidated;
        } else {
            consolidatedProperty.set(consolidated);
        }
    }
    
    public BooleanProperty consolidatedProperty() {
        if (consolidatedProperty == null) {
            consolidatedProperty = new SimpleBooleanProperty(this, EXERCISE__COLUMN_NAME__CONSOLIDATED, _consolidated);
        }
        return consolidatedProperty;
    }
    // END  CONSOLIDATED -------------------------------------------------------
    
    // START  READY ------------------------------------------------------------
    private BooleanProperty readyProperty = null;
    private boolean _ready = false;
    
    @Column(name = EXERCISE__COLUMN_NAME__READY)
    public boolean isReady() {
        if (this.readyProperty == null) {
            return _ready;
        } else {
            return readyProperty.get();
        }
    }
    
    public void setReady(boolean ready) {
        if (readyProperty == null) {
            _ready = ready;
        } else {
            readyProperty.set(ready);
        }
    }
    
    public BooleanProperty readyProperty() {
        if (readyProperty == null) {
            readyProperty = new SimpleBooleanProperty(this, EXERCISE__COLUMN_NAME__READY, _ready);
        }
        return readyProperty;
    }
    // END  READY --------------------------------------------------------------
    
    // START  CHOOSENTIME ------------------------------------------------------
    private StringProperty choosenTimeProperty = null;
    private String _choosenTime = ETime.MIN_01_30.toString();
    
    @Column(name = EXERCISE__COLUMN_NAME__CHOOSEN_TIME)
    public String getChoosenTime() {
        if (choosenTimeProperty == null) {
            return _choosenTime;
        } else {
            return choosenTimeProperty.get();
        }
    }
    
    public void setChoosenTime(String choosenTime) {
        if (choosenTimeProperty == null) {
            _choosenTime = choosenTime;
        } else {
            choosenTimeProperty.set(choosenTime);
        }
    }
    
    public StringProperty choosenTimeProperty() {
        if (choosenTimeProperty == null) {
            choosenTimeProperty = new SimpleStringProperty(this, EXERCISE__COLUMN_NAME__CHOOSEN_TIME, _choosenTime);
        }
        
        return choosenTimeProperty;
    }
    // END  CHOOSENTIME --------------------------------------------------------

    @Override
    public int compareTo(Exercise other) {
        return new CompareToBuilder()
                .append(other.getGenerationTime(), this.getGenerationTime())
                .append(other.getId(), this.getId())
                .append(other.getTopicId(), this.getTopicId())
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
        
        final Exercise other = (Exercise) obj;
        return new EqualsBuilder()
                .append(this.getId(), other.getId())
                .append(this.getTopicId(), other.getTopicId())
                .append(this.getGenerationTime(), other.getGenerationTime())
                .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getId())
                .append(this.getTopicId())
                .append(this.getGenerationTime())
                .toHashCode();
    }
	
	@Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(EXERCISE__COLUMN_NAME__ID, this.getId())
                .append(EXERCISE__COLUMN_NAME__TOPIC_ID, this.getTopicId())
                .append(EXERCISE__COLUMN_NAME__GENERATION_TIME, this.getGenerationTime())
                .append(EXERCISE__COLUMN_NAME__FINISHED_TIME, this.getFinishedTime())
                .append(EXERCISE__COLUMN_NAME__CONSOLIDATED, this.isConsolidated())
                .append(EXERCISE__COLUMN_NAME__READY, this.isReady())
                .append(EXERCISE__COLUMN_NAME__CHOOSEN_TIME, this.getChoosenTime())
                .toString();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getTopicId());
        out.writeLong(this.getGenerationTime());
        out.writeLong(this.getFinishedTime());
        out.writeBoolean(this.isConsolidated());
        out.writeBoolean(this.isReady());
        out.writeObject(this.getChoosenTime());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setTopicId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setFinishedTime(in.readLong());
        this.setConsolidated(in.readBoolean());
        this.setReady(in.readBoolean());
        this.setChoosenTime(String.valueOf(in.readObject()));
    }
    
}

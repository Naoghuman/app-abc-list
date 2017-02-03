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
import com.github.naoghuman.abclist.configuration.IExerciseTermConfiguration;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Naoghuman
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = IExerciseTermConfiguration.ENTITY__TABLE_NAME__EXERCISE_TERM)
@NamedQueries({
    @NamedQuery(
            name = IExerciseTermConfiguration.NAMED_QUERY__NAME__COUNT_ALL_EXERCISE_TERMS_WITH_TERM_ID,
            query = IExerciseTermConfiguration.NAMED_QUERY__QUERY__COUNT_ALL_EXERCISE_TERMS_WITH_TERM_ID),
    @NamedQuery(
            name = IExerciseTermConfiguration.NAMED_QUERY__NAME__FIND_ALL_EXERCISE_TERMS_WITH_EXERCISE_ID,
            query = IExerciseTermConfiguration.NAMED_QUERY__QUERY__FIND_ALL_EXERCISE_TERMS_WITH_EXERCISE_ID),
    @NamedQuery(
            name = IExerciseTermConfiguration.NAMED_QUERY__NAME__FIND_EXERCISE_TERM_WITH_EXERCISE_ID_AND_TERM_ID,
            query = IExerciseTermConfiguration.NAMED_QUERY__QUERY__FIND_EXERCISE_TERM_WITH_EXERCISE_ID_AND_TERM_ID),
    @NamedQuery(
            name = IExerciseTermConfiguration.NAMED_QUERY__NAME__IS_EXERCISE_TERM_MARK_AS_WRONG,
            query = IExerciseTermConfiguration.NAMED_QUERY__QUERY__IS_EXERCISE_TERM_MARK_AS_WRONG)
})
public class ExerciseTerm implements Comparable<ExerciseTerm>, Externalizable, IDefaultConfiguration, IExerciseTermConfiguration {
    
    public ExerciseTerm() {
        this(DEFAULT_ID);
    }
    
    public ExerciseTerm(long id) {
        this(id, DEFAULT_ID, DEFAULT_ID);
    }
    
    public ExerciseTerm(long exerciseId, long termId) {
        this(DEFAULT_ID, exerciseId, termId);
    }
    
    public ExerciseTerm(long id, long exerciseId, long termId) {
        this.init(id, exerciseId, termId);
    }
    
    private void init(long id, long exerciseId, long termId) {
        this.setId(id);
        this.setExerciseId(exerciseId);
        this.setTermId(termId);
    }
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = DEFAULT_ID;

    @Id
    @Column(name = EXERCISE_TERM__COLUMN_NAME__ID)
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
            idProperty = new SimpleLongProperty(this, EXERCISE_TERM__COLUMN_NAME__ID, _id);
        }
        
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
    
    // START  EXERCISE-ID ------------------------------------------------------
    private LongProperty exerciseIdProperty;
    private long _exerciseId = DEFAULT_ID;

    @Column(name = EXERCISE_TERM__COLUMN_NAME__EXERCISE_ID)
    public long getExerciseId() {
        if (exerciseIdProperty == null) {
            return _exerciseId;
        } else {
            return exerciseIdProperty.get();
        }
    }

    public final void setExerciseId(long exerciseId) {
        if (exerciseIdProperty == null) {
            _exerciseId = exerciseId;
        } else {
            exerciseIdProperty.set(exerciseId);
        }
    }

    public LongProperty exerciseIdProperty() {
        if (exerciseIdProperty == null) {
            exerciseIdProperty = new SimpleLongProperty(this, EXERCISE_TERM__COLUMN_NAME__EXERCISE_ID, _exerciseId);
        }
        
        return exerciseIdProperty;
    }
    // END  EXERCISE-ID --------------------------------------------------------
	
    // START  TERM-ID ----------------------------------------------------------
    private LongProperty termIdProperty;
    private long _termId = DEFAULT_ID;

    @Column(name = EXERCISE_TERM__COLUMN_NAME__TERM_ID)
    public long getTermId() {
        if (termIdProperty == null) {
            return _termId;
        } else {
            return termIdProperty.get();
        }
    }

    public final void setTermId(long termId) {
        if (termIdProperty == null) {
            _termId = termId;
        } else {
            termIdProperty.set(termId);
        }
    }

    public LongProperty termIdProperty() {
        if (termIdProperty == null) {
            termIdProperty = new SimpleLongProperty(this, EXERCISE_TERM__COLUMN_NAME__TERM_ID, _termId);
        }
        
        return termIdProperty;
    }
    // END  TERM-ID ------------------------------------------------------------
    
    // START  MARK-AS-WRONG ----------------------------------------------------
    private BooleanProperty markAsWrongProperty = null;
    private boolean _markAsWrong = false;
    
    @Column(name = EXERCISE_TERM__COLUMN_NAME__MARK_AS_WRONG)
    public boolean isMarkAsWrong() {
        if (this.markAsWrongProperty == null) {
            return _markAsWrong;
        } else {
            return markAsWrongProperty.get();
        }
    }
    
    public void setMarkAsWrong(boolean markAsWrong) {
        if (markAsWrongProperty == null) {
            _markAsWrong = markAsWrong;
        } else {
            markAsWrongProperty.set(markAsWrong);
        }
    }
    
    public BooleanProperty markAsWrongProperty() {
        if (markAsWrongProperty == null) {
            markAsWrongProperty = new SimpleBooleanProperty(this, EXERCISE_TERM__COLUMN_NAME__MARK_AS_WRONG, _markAsWrong);
        }
        return markAsWrongProperty;
    }
    // END  MARK-AS-WRONG ------------------------------------------------------
	
    @Override
    public int compareTo(ExerciseTerm other) {
        return new CompareToBuilder()
                .append(this.getExerciseId(), other.getExerciseId())
                .append(this.getTermId(), other.getTermId())
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
        
        final ExerciseTerm other = (ExerciseTerm) obj;
        return new EqualsBuilder()
                .append(this.getId(), other.getId())
                .append(this.getExerciseId(), other.getExerciseId())
                .append(this.getTermId(), other.getTermId())
                .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getId())
                .append(this.getExerciseId())
                .append(this.getTermId())
                .toHashCode();
    }
	
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(EXERCISE_TERM__COLUMN_NAME__ID, this.getId())
                .append(EXERCISE_TERM__COLUMN_NAME__EXERCISE_ID, this.getExerciseId())
                .append(EXERCISE_TERM__COLUMN_NAME__TERM_ID, this.getTermId())
                .append(EXERCISE_TERM__COLUMN_NAME__MARK_AS_WRONG, this.isMarkAsWrong())
                .toString();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getExerciseId());
        out.writeLong(this.getTermId());
        out.writeBoolean(this.isMarkAsWrong());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setExerciseId(in.readLong());
        this.setTermId(in.readLong());
        this.setMarkAsWrong(in.readBoolean());
    }
    
}

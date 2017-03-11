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
import com.github.naoghuman.abclist.configuration.ILinkConfiguration;
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
@Table(name = ILinkConfiguration.ENTITY__TABLE_NAME__LINK)
@NamedQueries({
    @NamedQuery(
            name = ILinkConfiguration.NAMED_QUERY__NAME__FIND_ALL,
            query = ILinkConfiguration.NAMED_QUERY__QUERY__FIND_ALL)
})
public class Link implements Comparable<Link>, Externalizable, IDefaultConfiguration, ILinkConfiguration {
    
    public Link() {
        this(DEFAULT_ID);
    }
    
    public Link(final long id) {
        this.setId(id);
        this.setGenerationTime(System.currentTimeMillis());
        
        markAsChangedProperty = new SimpleBooleanProperty(Boolean.FALSE);
    }
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = DEFAULT_ID;

    @Id
    @Column(name = LINK__COLUMN_NAME__ID)
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
            idProperty = new SimpleLongProperty(this, LINK__COLUMN_NAME__ID, _id);
        }
        
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
    
    // START  GENERATIONTIME ---------------------------------------------------
    private LongProperty generationTimeProperty;
    private long _generationTime = System.currentTimeMillis();

    @Column(name = LINK__COLUMN_NAME__GENERATION_TIME)
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
            generationTimeProperty = new SimpleLongProperty(this, LINK__COLUMN_NAME__GENERATION_TIME, _generationTime);
        }
        return generationTimeProperty;
    }
    // END  GENERATIONTIME -----------------------------------------------------
    
    // START  ALIAS ------------------------------------------------------------
    private StringProperty aliasProperty = null;
    private String _alias = SIGN__EMPTY;
    
    @Column(name = LINK__COLUMN_NAME__ALIAS)
    public String getAlias() {
        if (aliasProperty == null) {
            return _alias;
        } else {
            return aliasProperty.get();
        }
    }
    
    public void setAlias(String alias) {
        if (aliasProperty == null) {
            _alias = alias;
        } else {
            aliasProperty.set(alias);
        }
    }
    
    public StringProperty aliasProperty() {
        if (aliasProperty == null) {
            aliasProperty = new SimpleStringProperty(this, LINK__COLUMN_NAME__ALIAS, _alias);
        }
        
        return aliasProperty;
    }
    // END  ALIAS --------------------------------------------------------------
    
    // START  DESCRIPTION ------------------------------------------------------
    private StringProperty descriptionProperty = null;
    private String _description = SIGN__EMPTY;
    
    @Column(name = LINK__COLUMN_NAME__DESCRIPTION)
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
            descriptionProperty = new SimpleStringProperty(this, LINK__COLUMN_NAME__DESCRIPTION, _description);
        }
        
        return descriptionProperty;
    }
    // END  DESCRIPTION --------------------------------------------------------
    
    // START  URL --------------------------------------------------------------
    private StringProperty urlProperty = null;
    private String _url = SIGN__EMPTY;
    
    @Column(name = LINK__COLUMN_NAME__URL)
    public String getUrl() {
        if (urlProperty == null) {
            return _url;
        } else {
            return urlProperty.get();
        }
    }
    
    public void setUrl(String url) {
        if (urlProperty == null) {
            _url = url;
        } else {
            urlProperty.set(url);
        }
    }
    
    public StringProperty urlProperty() {
        if (urlProperty == null) {
            urlProperty = new SimpleStringProperty(this, LINK__COLUMN_NAME__URL, _url);
        }
        
        return urlProperty;
    }
    // END  URL ----------------------------------------------------------------
    
    // START  IMAGE ------------------------------------------------------------
    private StringProperty imageProperty = null;
    private String _image = SIGN__EMPTY;
    
    @Column(name = LINK__COLUMN_NAME__IMAGE)
    public String getImage() {
        if (imageProperty == null) {
            return _image;
        } else {
            return imageProperty.get();
        }
    }
    
    public void setImage(String image) {
        if (imageProperty == null) {
            _image = image;
        } else {
            imageProperty.set(image);
        }
    }
    
    public StringProperty imageProperty() {
        if (imageProperty == null) {
            imageProperty = new SimpleStringProperty(this, LINK__COLUMN_NAME__IMAGE, _image);
        }
        
        return imageProperty;
    }
    // END  IMAGE --------------------------------------------------------------
    
    // START  FAVORITE ---------------------------------------------------------
    private BooleanProperty favoriteProperty = null;
    private boolean _favorite = Boolean.FALSE;
    
    @Column(name = LINK__COLUMN_NAME__FAVORITE)
    public boolean getFavorite() {
        if (favoriteProperty == null) {
            return _favorite;
        } else {
            return favoriteProperty.get();
        }
    }
    
    public void setFavorite(boolean favorite) {
        if (favoriteProperty == null) {
            _favorite = favorite;
        } else {
            favoriteProperty.set(favorite);
        }
    }
    
    public BooleanProperty favoriteProperty() {
        if (favoriteProperty == null) {
            favoriteProperty = new SimpleBooleanProperty(this, LINK__COLUMN_NAME__FAVORITE, _favorite);
        }
        
        return favoriteProperty;
    }
    // END  FAVORITE -----------------------------------------------------------
    
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
    public int compareTo(Link other) {
        return new CompareToBuilder()
                .append(this.getId(), other.getId())
                .append(this.getGenerationTime(), other.getGenerationTime())
                .append(this.getAlias(), other.getAlias())
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
        
        final Link other = (Link) obj;
        return new EqualsBuilder()
                .append(this.getId(), other.getId())
                .append(this.getGenerationTime(), other.getGenerationTime())
                .append(this.getAlias(), other.getAlias())
                .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getId())
                .append(this.getGenerationTime())
                .append(this.getAlias())
                .toHashCode();
    }
	
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(LINK__COLUMN_NAME__ID, this.getId())
                .append(LINK__COLUMN_NAME__GENERATION_TIME, this.getGenerationTime())
                .append(LINK__COLUMN_NAME__ALIAS, this.getAlias())
                .append(LINK__COLUMN_NAME__URL, this.getUrl())
                .append(LINK__COLUMN_NAME__IMAGE, this.getImage())
                .append(LINK__COLUMN_NAME__FAVORITE, this.getFavorite())
                .append(LINK__COLUMN_NAME__DESCRIPTION, this.getDescription())
                .toString();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getGenerationTime());
        out.writeObject(this.getAlias()); // TODO (de-/encrypt for db)
        out.writeObject(this.getDescription()); // TODO (de-/encrypt for db)
        out.writeObject(this.getUrl()); // TODO (de-/encrypt for db)
        out.writeObject(this.getImage());
        out.writeBoolean(this.getFavorite());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setAlias(String.valueOf(in.readObject())); // TODO (de-/encrypt for db)
        this.setDescription(String.valueOf(in.readObject())); // TODO (de-/encrypt for db)
        this.setUrl(String.valueOf(in.readObject())); // TODO (de-/encrypt for db)
        this.setImage(String.valueOf(in.readObject()));
        this.setFavorite(in.readBoolean());
    }
    
}

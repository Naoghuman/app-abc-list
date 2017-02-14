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
package com.github.naoghuman.abclist.testdata.listview;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Naoghuman
 */
public class CheckBoxListCellModel {
    
    private final BooleanProperty selectedProperty = new SimpleBooleanProperty();
    private final StringProperty name = new SimpleStringProperty();
    
    private String id = null;
    
    public final String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public final boolean isSelected() {
        return this.selectedProperty().get();
    }
    
    public final BooleanProperty selectedProperty() {
        return selectedProperty;
    }

    public final void setSelected(final boolean selected) {
        this.selectedProperty().set(selected);
    }
    
    public final StringProperty nameProperty() {
        return name;
    }

    public final String getName() {
        return this.nameProperty().get();
    }

    public final void setName(final String name) {
        this.nameProperty().set(name);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}

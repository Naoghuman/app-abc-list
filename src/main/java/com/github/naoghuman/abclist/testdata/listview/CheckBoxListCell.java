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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * A class containing a {@link ListCell} implementation that draws a 
 * {@link CheckBox} node inside the cell, optionally with a label to indicate 
 * what the checkbox represents.
 * 
 * <p>The CheckBoxListCell is rendered with a CheckBox on the left-hand side of 
 * the {@link ListView}, and the text related to the list item taking up all 
 * remaining horizontal space. 
 * 
 * <p>To construct an instance of this class, it is necessary to provide a 
 * {@link Callback} that, given an object of type T, will return a 
 * {@code ObservableValue<Boolean>} that represents whether the given item is 
 * selected or not. This ObservableValue will be bound bidirectionally (meaning 
 * that the CheckBox in the cell will set/unset this property based on user 
 * interactions, and the CheckBox will reflect the state of the 
 * ObservableValue<Boolean>, if it changes externally).
 *
 * <p>Note that the CheckBoxListCell renders the CheckBox 'live', meaning that
 * the CheckBox is always interactive and can be directly toggled by the user.
 * This means that it is not necessary that the cell enter its
 * {@link #editingProperty() editing state} (usually by the user double-clicking
 * on the cell). A side-effect of this is that the usual editing callbacks
 * (such as {@link javafx.scene.control.ListView#onEditCommitProperty() on edit commit})
 * will <strong>not</strong> be called. If you want to be notified of changes,
 * it is recommended to directly observe the boolean properties that are
 * manipulated by the CheckBox.</p>
 * 
 * @see CheckBox
 * @see ListCell
 * @param <T> The type of the elements contained within the ListView.
 * @since JavaFX 2.2
 */
public class CheckBoxListCell<T> extends ListCell<T> {
    
    public static <T> Callback<ListView<T>, ListCell<T>> forListView(
            final Callback<T, ObservableValue<Boolean>> getSelectedProperty, 
            final BooleanProperty disableProperty
    ) {
        return list -> new CheckBoxListCell<>(getSelectedProperty, disableProperty, CellUtils.<T>defaultStringConverter());
    }
    
    /***************************************************************************
     *                                                                         *
     * Fields                                                                  *
     *                                                                         *
     **************************************************************************/
    
    private final CheckBox checkBox;
    
    private ObservableValue<Boolean> booleanProperty;
    
    public CheckBoxListCell(
            final Callback<T, ObservableValue<Boolean>> getSelectedProperty, 
            final BooleanProperty disableProperty,
            final StringConverter<T> converter
    ) {
        this.getStyleClass().add("check-box-list-cell");
        setSelectedStateCallback(getSelectedProperty);
        setConverter(converter);
        
        checkBox = new CheckBox();
        checkBox.disableProperty().bind(disableProperty);
        
        setAlignment(Pos.CENTER_LEFT);
        setContentDisplay(ContentDisplay.LEFT);

        // by default the graphic is null until the cell stops being empty
        setGraphic(null);
    }
    
    
    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
    
    // --- converter
    private final ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty<>(this, "converter");

    /**
     * The {@link StringConverter} property.
     * @return 
     */
    public final ObjectProperty<StringConverter<T>> converterProperty() { 
        return converter; 
    }
    
    /** 
     * Sets the {@link StringConverter} to be used in this cell.
     * @param value
     */
    public final void setConverter(StringConverter<T> value) { 
        converterProperty().set(value); 
    }
    
    /**
     * Returns the {@link StringConverter} used in this cell.
     * @return 
     */
    public final StringConverter<T> getConverter() { 
        return converterProperty().get(); 
    }
    
    // --- selected state callback property
    private final ObjectProperty<Callback<T, ObservableValue<Boolean>>> selectedStateCallback 
            = new SimpleObjectProperty<>(this, "selectedStateCallback");

    /**
     * Property representing the {@link Callback} that is bound to by the 
     * CheckBox shown on screen.
     * @return 
     */
    public final ObjectProperty<Callback<T, ObservableValue<Boolean>>> selectedStateCallbackProperty() { 
        return selectedStateCallback; 
    }
    
    /** 
     * Sets the {@link Callback} that is bound to by the CheckBox shown on screen.
     * @param value
     */
    public final void setSelectedStateCallback(Callback<T, ObservableValue<Boolean>> value) { 
        selectedStateCallbackProperty().set(value); 
    }
    
    /**
     * Returns the {@link Callback} that is bound to by the CheckBox shown on screen.
     * @return 
     */
    public final Callback<T, ObservableValue<Boolean>> getSelectedStateCallback() { 
        return selectedStateCallbackProperty().get(); 
    }

    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/
    
    /** {@inheritDoc} */
    @Override public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        
        if (! empty) {
            StringConverter<T> c = getConverter();
            Callback<T, ObservableValue<Boolean>> callback = getSelectedStateCallback();
            if (callback == null) {
                throw new NullPointerException(
                        "The CheckBoxListCell selectedStateCallbackProperty can not be null");
            }
            
            setGraphic(checkBox);
            setText(c != null ? c.toString(item) : (item == null ? "" : item.toString()));
            
            if (booleanProperty != null) {
                checkBox.selectedProperty().unbindBidirectional((BooleanProperty)booleanProperty);
            }
            booleanProperty = callback.call(item);
            if (booleanProperty != null) {
                checkBox.selectedProperty().bindBidirectional((BooleanProperty)booleanProperty);
            }
        } else {
            setGraphic(null);
            setText(null);
        }
    }
}

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
package com.github.naoghuman.abclist.view.exercise;

import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Naoghuman
 */
public final class StateFacade {
    
    private static final Optional<StateFacade> instance = Optional.of(new StateFacade());

    public static final StateFacade getDefault() {
        return instance.get();
    }
    
    private final BooleanProperty disablePauseButtonProperty = new SimpleBooleanProperty();
    private final BooleanProperty disableStartButtonProperty = new SimpleBooleanProperty();
    private final BooleanProperty disableStopButtonProperty = new SimpleBooleanProperty();
    private final BooleanProperty disableTimeChooserComboBoxProperty = new SimpleBooleanProperty();
    private final BooleanProperty disableUserInputTextFieldProperty = new SimpleBooleanProperty();
    
    private StateFacade() {
        
    }

    public BooleanProperty disablePauseButtonProperty() {
        return disablePauseButtonProperty;
    }

    public BooleanProperty disableStartButtonProperty() {
        return disableStartButtonProperty;
    }

    public BooleanProperty disableStopButtonProperty() {
        return disableStopButtonProperty;
    }
    
    public BooleanProperty disableTimeChooserComboBoxProperty() {
        return disableTimeChooserComboBoxProperty;
    }
    
    public BooleanProperty disableUserInputTextFieldProperty() {
        return disableUserInputTextFieldProperty;
    }
    
    public void setDisablePauseButton(boolean disablePauseButton) {
        disablePauseButtonProperty.set(disablePauseButton);
    }
    
    public void setDisableStartButton(boolean disableStartButton) {
        disableStartButtonProperty.set(disableStartButton);
    }
    
    public void setDisableStopButton(boolean disableStopButton) {
        disableStopButtonProperty.set(disableStopButton);
    }
    
    public void setDisableTimeChooserComboBox(boolean disableTimeChooserComboBox) {
        disableTimeChooserComboBoxProperty.set(disableTimeChooserComboBox);
    }
    
    public void setDisableUserInputTextField(boolean disableUserInputTextField) {
        disableUserInputTextFieldProperty.set(disableUserInputTextField);
    }
    
}

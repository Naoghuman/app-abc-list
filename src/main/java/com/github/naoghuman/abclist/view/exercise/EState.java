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

/**
 * 
 * @author Naoghuman
 */
public enum EState {
    
    PREPARE_STATE_FOR__INITIALIZE  (true,  false, true,  false, true),
    PREPARE_STATE_FOR__IS_READY    (true,  true,  true,  true,  true),
    PREPARE_STATE_FOR__IS_STARTED  (false, true,  false, true,  false),
    PREPARE_STATE_FOR__SHOULD_PAUSE(true,  false, true,  true,  true),
    PREPARE_STATE_FOR__START_AGAIN (false, true,  false, true,  false);
   
    private final boolean disablePauseButton;
    private final boolean disableStartButton;
    private final boolean disableStopButton;
    private final boolean disableTimeChooserComboBox;
    private final boolean disableUserInputTextField;

    private EState(
            boolean disablePauseButton, boolean disableStartButton,
            boolean disableStopButton, boolean disableTimeChooserComboBox,
            boolean disableUserInputTextField
    ) {
        this.disablePauseButton = disablePauseButton;
        this.disableStartButton = disableStartButton;
        this.disableStopButton = disableStopButton;
        this.disableTimeChooserComboBox = disableTimeChooserComboBox;
        this.disableUserInputTextField = disableUserInputTextField;
    }

    public boolean isDisablePauseButton() {
        return disablePauseButton;
    }

    public boolean isDisableStartButton() {
        return disableStartButton;
    }

    public boolean isDisableStopButton() {
        return disableStopButton;
    }

    public boolean isDisableTimeChooserComboBox() {
        return disableTimeChooserComboBox;
    }

    public boolean isDisableUserInputTextField() {
        return disableUserInputTextField;
    }
    
}

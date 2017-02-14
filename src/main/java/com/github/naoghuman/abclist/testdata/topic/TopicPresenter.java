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
package com.github.naoghuman.abclist.testdata.topic;

import com.github.naoghuman.abclist.configuration.IPreferencesConfiguration;
import com.github.naoghuman.abclist.testdata.entity.EntityHelper;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.preferences.api.PreferencesFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.util.Callback;

/**
 *
 * @author PRo
 */
public class TopicPresenter implements Initializable, IPreferencesConfiguration {
    
    @FXML private ComboBox cbQuantityEntities;
    @FXML private ComboBox cbQuantityTimePeriod;
    @FXML private Label lProgressBarInformation;
    @FXML private Label lProgressBarPercentInformation;
    @FXML private ProgressBar pbEntityTopic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize DreamPresenter"); // NOI18N
        
        assert (cbQuantityEntities != null)             : "fx:id=\"cbQuantityEntities\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (cbQuantityTimePeriod != null)           : "fx:id=\"cbQuantityTimePeriod\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (lProgressBarInformation != null)        : "fx:id=\"lProgressBarInformation\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (lProgressBarPercentInformation != null) : "fx:id=\"lProgressBarPercentInformation\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
        assert (pbEntityTopic != null)                  : "fx:id=\"pbEntityTopic\" was not injected: check your FXML file 'Dream.fxml'."; // NOI18N
    
        this.initializeComboBoxes();
    }

    private void initializeComboBoxes() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ComboBoxes"); // NOI18N
        
        cbQuantityEntities.getItems().addAll(EntityHelper.getDefault().getQuantityEntities());
        cbQuantityEntities.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {

            @Override
            public ListCell<Integer> call(ListView<Integer> param) {
                return new ListCell<Integer>() {

                        @Override
                        protected void updateItem(Integer item, boolean empty) {
                            super.updateItem(item, empty);
                            
                            if (item == null) {
                                super.setText(null);
                                return;
                            }
                            
                            super.setText("" + item); // NOI18N
                        }
                    };
            }
        });
        
        final Integer quantityEntities = PreferencesFacade.getDefault().getInt(
                PREF__TESTDATA__QUANTITY_ENTITIES__DREAM,
                PREF__TESTDATA__QUANTITY_ENTITIES__DREAM__DEFAULT_VALUE);
        cbQuantityEntities.getSelectionModel().select(quantityEntities);
        
        cbQuantityTimePeriod.getItems().addAll(EntityHelper.getDefault().getQuantityTimePeriods());
        cbQuantityTimePeriod.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {

            @Override
            public ListCell<Integer> call(ListView<Integer> param) {
                return new ListCell<Integer>() {

                        @Override
                        protected void updateItem(Integer item, boolean empty) {
                            super.updateItem(item, empty);
                            
                            if (item == null) {
                                super.setText(null);
                                return;
                            }
                            
                            super.setText("" + item); // NOI18N
                        }
                    };
            }
        });
        
        final Integer quantityTimePeriod = PreferencesFacade.getDefault().getInt(
                PREF__TESTDATA__QUANTITY_TIMEPERIOD__DREAM,
                PREF__TESTDATA__QUANTITY_TIMEPERIOD__DREAM__DEFAULT_VALUE);
        cbQuantityTimePeriod.getSelectionModel().select(quantityTimePeriod);
    }

    public void bind(BooleanProperty disableProperty) {
        cbQuantityEntities.disableProperty().unbind();
        cbQuantityEntities.disableProperty().bind(disableProperty);
        
        cbQuantityTimePeriod.disableProperty().unbind();
        cbQuantityTimePeriod.disableProperty().bind(disableProperty);
    }
    
    public Label getProgressBarPercentInformation() {
        return lProgressBarPercentInformation;
    }

    public int getSaveMaxEntities() {
        Integer saveMaxEntitites = (Integer) cbQuantityEntities.getSelectionModel().getSelectedItem();
        if (saveMaxEntitites == null) {
            saveMaxEntitites = 0;
        }
        
        return saveMaxEntitites;
    }

    public int getTimePeriod() {
        Integer timePeriod = (Integer) cbQuantityTimePeriod.getSelectionModel().getSelectedItem();
        if (timePeriod == null) {
            timePeriod = 0;
        }
        
        return timePeriod;
    }
    
    public void onActionQuantityEntities() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action Quantity Entities"); // NOI18N
        
        final Integer quantityEntities = (Integer) cbQuantityEntities.getSelectionModel().getSelectedItem();
        PreferencesFacade.getDefault().putInt(PREF__TESTDATA__QUANTITY_ENTITIES__DREAM, quantityEntities);
    }
    
    public void onActionQuantityTimePeriod() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action Quantity TimePeriod"); // NOI18N
        
        final Integer quantityTimePeriod = (Integer) cbQuantityTimePeriod.getSelectionModel().getSelectedItem();
        PreferencesFacade.getDefault().putInt(PREF__TESTDATA__QUANTITY_TIMEPERIOD__DREAM, quantityTimePeriod);
    }
    
    public DoubleProperty progressPropertyFromEntityDream() {
        return pbEntityTopic.progressProperty();
    }
    
    public void setProgressBarInformation(String message) {
        lProgressBarInformation.setText(message);
    }
    
}

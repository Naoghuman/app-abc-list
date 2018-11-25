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
package com.github.naoghuman.abclist.view.topic;

import com.github.naoghuman.abclist.configuration.IActionConfiguration;
import com.github.naoghuman.abclist.dialog.DialogProvider;
import com.github.naoghuman.abclist.model.Topic;
import com.github.naoghuman.abclist.sql.SqlProvider;
import com.github.naoghuman.lib.action.core.ActionHandlerFacade;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Naoghuman
 */
public class TopicPresenter implements Initializable, IActionConfiguration {
    
    @FXML private FlowPane fpThumbnails;
    @FXML private Button bSave;
    @FXML private TextArea taDescription;
    @FXML private TextField tfTitle;
    @FXML private VBox vbLinkArea;
    
    private StringChangeListener stringChangeListener = null;
    private Topic topic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [TopicPresenter]"); // NOI18N
        
        this.initializeListeners();
    }
    
    private void initializeListeners() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize listeners"); // NOI18N
        
        stringChangeListener = new StringChangeListener();
    }
    
    public void configure(Topic topic) {
        LoggerFacade.getDefault().debug(this.getClass(), "configure"); // NOI18N
        
        this.topic = topic;
        
        bSave.disableProperty().bind(topic.markAsChangedProperty().not());
        
        tfTitle.setText(topic.getTitle());
        tfTitle.textProperty().addListener(stringChangeListener);
        topic.titleProperty().bind(tfTitle.textProperty());
        
        taDescription.setText(topic.getDescription());
        taDescription.textProperty().addListener(stringChangeListener);
        topic.descriptionProperty().bind(taDescription.textProperty());
    }
    
    public long getId() {
        return topic.getId();
    }
    
    public void onActionCreateNewLinkPane() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action create new [LinkPane]"); // NOI18N
       
        DialogProvider.getDefault().showNewLinkWizardForTopic(topic);
    }
    
    public void onActionCreateNewThumbnail() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action create new [Thumbnail]"); // NOI18N
    }
    
    public void onActionDeleteThumbnail() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action delete [Thumbnail]"); // NOI18N
        // TODO
    }
    
    public void onActionEditThumbnail() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action edit [Thumbnail]"); // NOI18N
        // TODO
    }
    
    public void onActionSaveTopic() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action save [Topic]"); // NOI18N
        
        // TODO check if title is valid
        // Data in the model is updated through binding
        SqlProvider.getDefault().updateTopic(topic);
        
        // Reset [MarkAsChanged]
        topic.setMarkAsChanged(Boolean.FALSE);
        
        // Refresh the [Navigation]
        ActionHandlerFacade.getDefault().handle(ACTION__APPLICATION__REFRESH_NAVIGATION_TAB_TOPICS);
    }
    
    private final class StringChangeListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (
                    topic != null
                    && !topic.isMarkAsChanged()
            ) { 
                topic.setMarkAsChanged(Boolean.TRUE);
            }
        }
    }
    
}

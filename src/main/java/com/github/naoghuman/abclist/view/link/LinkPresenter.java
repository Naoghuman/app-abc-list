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
package com.github.naoghuman.abclist.view.link;

import com.github.naoghuman.abclist.model.Link;
import com.github.naoghuman.abclist.view.components.linkpane.LinkPanePresenter;
import com.github.naoghuman.abclist.view.components.linkpane.LinkPaneView;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author Naoghuman
 */
public class LinkPresenter implements Initializable {
    
    @FXML private FlowPane fpThumbnails;
    @FXML private VBox vbLinkArea;
    @FXML private VBox vbLinkDetails;

    private StringChangeListener stringChangeListener = null;
    
    private Link link;
    private LinkPanePresenter presenter;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [LinkPresenter]"); // NOI18N
        
        this.initializeDetailsArea();
        this.initializeListeners();
    }
    
    private void initializeDetailsArea() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Details] area"); // NOI18N
        
        final LinkPaneView view = new LinkPaneView();
        presenter = view.getRealPresenter();
        
        final TitledPane titledPane = presenter.getTitledPane();
        titledPane.setExpanded(Boolean.TRUE);
        titledPane.setCollapsible(Boolean.FALSE);
        VBox.setVgrow(titledPane, Priority.ALWAYS);
        
        vbLinkDetails.getChildren().add(titledPane);
    }
    
    private void initializeListeners() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize listeners"); // NOI18N
        
        stringChangeListener = new StringChangeListener();
    }
    
    public void configure(final Link link) {
        LoggerFacade.getDefault().debug(this.getClass(), "configure"); // NOI18N
        
        this.link = link;
        
        presenter.configure(this.link);
    }
    
    public void onActionCreateNewThumbnail() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action create new [Thumbnail]"); // NOI18N
        // TODO
    }
    
    public void onActionDeleteThumbnail() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action delete [Thumbnail]"); // NOI18N
        // TODO
    }
    
    public void onActionEditThumbnail() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action edit [Thumbnail]"); // NOI18N
        // TODO
    }
    
    public void onActionSaveLink() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action save [Link]"); // NOI18N
        // TODO
    }
    
    private final class StringChangeListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (
                    link != null
                    && !link.isMarkAsChanged()
            ) { 
                link.setMarkAsChanged(Boolean.TRUE);
            }
        }
    }
    
}

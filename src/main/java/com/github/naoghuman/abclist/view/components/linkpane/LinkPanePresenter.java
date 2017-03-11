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
package com.github.naoghuman.abclist.view.components.linkpane;

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 *
 * @author Naoghuman
 */
public class LinkPanePresenter implements Initializable {
    
    @FXML private Button bSave;
    @FXML private ComboBox cbImages;
    @FXML private Hyperlink hyperlink;
    @FXML private ImageView ivFavorite;
    @FXML private ImageView ivImage;
    @FXML private TextArea taDescription;
    @FXML private TextField tfAlias;
    @FXML private TextField tfUrl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize LinkPanePresenter"); // NOI18N
        
        
    }
    
    public void onActionClickAliasHyperlink() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action click [Alias] Hyperlink"); // NOI18N
        
        
    }
    
    public void onActionClickFavoriteCheckBox() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action click [Favorite] CheckBox"); // NOI18N
        
    }
    
    public void onActionClickImagesComboBox() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action click [Save] Button"); // NOI18N
        
    }
    
    public void onActionClickSaveButton() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action click [Save] Button"); // NOI18N
        
    }
    
}

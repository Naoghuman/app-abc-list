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

import com.github.naoghuman.abclist.configuration.IImagesConfiguration;
import com.github.naoghuman.abclist.images.ImageLoader;
import com.github.naoghuman.abclist.model.Link;
import com.github.naoghuman.abclist.sql.SqlProvider;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Naoghuman
 */
public class LinkPanePresenter implements Initializable {
    
    private static final Optional<Image> IMG_FAVORITE = ImageLoader.getDefault().load(IImagesConfiguration.IMG__FAVORITE);
    
    @FXML private Button bSave;
    @FXML private CheckBox cbFavorite;
    @FXML private ComboBox<LinkType> cbTypes;
    @FXML private Hyperlink hyperlink;
    @FXML private ImageView ivFavorite;
    @FXML private ImageView ivImage;
    @FXML private TextArea taDescription;
    @FXML private TextField tfAlias;
    @FXML private TextField tfUrl;
    @FXML private TitledPane tpLinkPane;
    
    private ImageView ivComboBoxElement;
    private Link link;
    
    private BooleanChangeListener booleanChangeListener = null;
    private StringChangeListener stringChangeListener = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize LinkPanePresenter"); // NOI18N
        
        this.initializeComboBoxTypes();
        this.initializeLoadImageFavorite();
        this.initializeListeners();
    }
    
    private void initializeComboBoxTypes() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ComboBox [Types]"); // NOI18N
        
        // ImageView
        ivComboBoxElement = new ImageView();
        ivComboBoxElement.setFitHeight(16.0d);
        ivComboBoxElement.setFitWidth(16.0d);
        
        // Configure ComboBox
        cbTypes.setButtonCell(new ImageListCell());
        cbTypes.setCellFactory(listView -> new ImageListCell());
        
        // Fill ComboBox
        final List<LinkType> linkTypes = Arrays.asList(LinkType.values());
        cbTypes.getItems().addAll(linkTypes);
    }
    
    private void initializeLoadImageFavorite() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize load image [Favorite]"); // NOI18N
        
        ivFavorite.setFitHeight(16.0d);
        ivFavorite.setFitWidth(16.0d);
        
        ivFavorite.setImage(IMG_FAVORITE.get());
    }
    
    private void initializeListeners() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize listeners"); // NOI18N
        
        booleanChangeListener = new BooleanChangeListener();
        stringChangeListener = new StringChangeListener();
    }
    
    public void configure(final Link link) {
        LoggerFacade.getDefault().debug(this.getClass(), "configure"); // NOI18N
        
        this.link = link;
        
        ivFavorite.visibleProperty().bind(cbFavorite.selectedProperty());
        ivFavorite.managedProperty().bind(cbFavorite.selectedProperty());
        
        ivImage.visibleProperty().bind(cbTypes.getSelectionModel().selectedItemProperty().isNotNull());
        ivImage.managedProperty().bind(cbTypes.getSelectionModel().selectedItemProperty().isNotNull());
        
        hyperlink.textProperty().bind(tfAlias.textProperty());
        
        tfAlias.setText(this.link.getAlias());
        tfAlias.textProperty().addListener(stringChangeListener);
        this.link.aliasProperty().bind(tfAlias.textProperty());
        
        if (!link.getImage().isEmpty()) {
            final Optional<LinkType> optional = LinkType.getLinkType(link.getImage());
            if (optional.isPresent()) {
                cbTypes.getSelectionModel().select(optional.get());
                ivImage.setImage(optional.get().getImage().get());
            }
        }
        
        tfUrl.setText(this.link.getUrl());
        tfUrl.textProperty().addListener(stringChangeListener);
        this.link.urlProperty().bind(tfUrl.textProperty());
        
        taDescription.setText(this.link.getDescription());
        taDescription.textProperty().addListener(stringChangeListener);
        this.link.descriptionProperty().bind(taDescription.textProperty());
        
        cbFavorite.setSelected(this.link.getFavorite());
        cbFavorite.selectedProperty().addListener(booleanChangeListener);
        this.link.favoriteProperty().bind(cbFavorite.selectedProperty());
        
        bSave.disableProperty().bind(this.link.markAsChangedProperty().not());
    }
    
    public TitledPane getTitledPane() {
        return tpLinkPane;
    }
    
    public void onActionClickAliasHyperlink() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action click [Alias] Hyperlink"); // NOI18N
        
        if (
                Desktop.isDesktopSupported()
                && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)
        ) {
            try {
                final URL url = new URL(link.getUrl());
                Desktop.getDesktop().browse(url.toURI());
            } catch (IOException | URISyntaxException ex) {
                LoggerFacade.getDefault().error(this.getClass(), "Can't open url: " + link.getUrl(), ex); // NOI18N
            }
        } else {
            LoggerFacade.getDefault().warn(this.getClass(), "Desktop.isDesktopSupported() isn't supported");
        }
    }
    
    public void onActionClickDeleteButton() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action click [Delete] Button"); // NOI18N
        
    }
    
    public void onActionClickFavoriteCheckBox() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action click [Favorite] CheckBox"); // NOI18N
        
    }
    
    public void onActionClickTypesComboBox() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action click [Types] ComboBox"); // NOI18N
        
        final LinkType linkType = cbTypes.getSelectionModel().getSelectedItem();
        ivImage.setImage(linkType.getImage().get());
        
        link.setImage(linkType.getImageName());
        link.setMarkAsChanged(Boolean.TRUE);
    }
    
    public void onActionClickSaveButton() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action click [Save] Button"); // NOI18N
        
        SqlProvider.getDefault().updateLink(link);
        
        link.setMarkAsChanged(Boolean.FALSE);
    }
    
    private final class BooleanChangeListener implements ChangeListener<Boolean> {

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (
                    link != null
                    && !link.isMarkAsChanged()
            ) { 
                link.setMarkAsChanged(Boolean.TRUE);
            }
        }
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

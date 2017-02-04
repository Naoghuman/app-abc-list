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
package com.github.naoghuman.abclist.view.term;

import com.github.naoghuman.abclist.configuration.IActionConfiguration;
import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.abclist.sql.SqlProvider;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Naoghuman
 */
public class TermPresenter implements Initializable, IActionConfiguration {
    
    @FXML private Button bSave;
    @FXML private TextArea taDescription;
    @FXML private TextField tfTitle;
    
    private StringChangeListener stringChangeListener = null;
    private Term term;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [TermPresenter]"); // NOI18N
        
        this.initializeListeners();
    }
    
    private void initializeListeners() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize listeners"); // NOI18N
        
        stringChangeListener = new StringChangeListener();
    }
    
    public void configure(Term term) {
        LoggerFacade.getDefault().debug(this.getClass(), "configure"); // NOI18N
        
        this.term = term;
        
        bSave.disableProperty().bind(term.markAsChangedProperty().not());
        
        tfTitle.setText(term.getTitle());
        tfTitle.textProperty().addListener(stringChangeListener);
        term.titleProperty().bind(tfTitle.textProperty());
        
        taDescription.setText(term.getDescription());
        taDescription.textProperty().addListener(stringChangeListener);
        term.descriptionProperty().bind(taDescription.textProperty());
    }
    
    public long getId() {
        return term.getId();
    }
    
    public void onActionSaveTerm() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action save [Term]"); // NOI18N
        
        // TODO check if title is valid
        // Data in the model is updated through binding
        SqlProvider.getDefault().updateTerm(term);
        
        // Reset [MarkAsChanged]
        term.setMarkAsChanged(Boolean.FALSE);
        
        // Refresh the [Navigation]
        final TransferData transferData = new TransferData();
        transferData.setActionId(ACTION__APPLICATION__REFRESH_NAVIGATION_TAB_TERMS_WITH_SELECTION);
        transferData.setObject(term);
        
        ActionFacade.getDefault().handle(transferData);
    }
    
    private final class StringChangeListener implements ChangeListener<String> {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (
                    term != null
                    && !term.isMarkAsChanged()
            ) { 
                term.setMarkAsChanged(Boolean.TRUE);
            }
        }
    }
    
}

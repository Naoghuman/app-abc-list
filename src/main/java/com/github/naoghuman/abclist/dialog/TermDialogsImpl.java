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
package com.github.naoghuman.abclist.dialog;

import com.github.naoghuman.abclist.model.ModelProvider;
import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.abclist.sql.SqlProvider;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;

/**
 *
 * @author Naoghuman
 */
public class TermDialogsImpl implements TermDialogs {

    @Override
    public void showNewTermWizard() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action show new [Term] wizard"); // NOI18N
        
        // TODO replace it with AnchorPane
        final TextInputDialog dialog = new TextInputDialog(); // NOI18N
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setHeaderText("Create Term"); // NOI18N
        dialog.setResizable(false);
        dialog.setTitle("Term Wizard"); // NOI18N
        
        final Optional<String> result = dialog.showAndWait();
        if (
                result.isPresent()
                && !result.get().isEmpty()
        ) {
            // Check if the [Term] always exists
            final ObservableList<Term> terms = SqlProvider.getDefault().findAllTerms();
            final String title = result.get();
            for (Term term : terms) {
                if (term.getTitle().equals(title)) {
                    return;
                }
            }
            
            // Create a new [Term]
            final Term term = ModelProvider.getDefault().getTerm(title);
            SqlProvider.getDefault().createTerm(term);
        }
    }
    
}

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

import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.abclist.model.Topic;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.util.Optional;

/**
 *
 * @author Naoghuman
 */
public final class DialogProvider implements LinkDialog, TermDialog, TopicDialog {
    
    private static final Optional<DialogProvider> INSTANCE = Optional.of(new DialogProvider());

    public static final DialogProvider getDefault() {
        return INSTANCE.get();
    }
    
    private LinkDialog  linkDialog;
    private TermDialog  termDialog;
    private TopicDialog topicDialog;
    
    private DialogProvider() {
        this.initialize();
    }
    
    private void initialize() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize DialogProvider"); // NOI18N
        
        linkDialog  = new DefaultLinkDialog();
        termDialog  = new DefaultTermDialog();
        topicDialog = new DefaultTopicDialog();
    }

    @Override
    public void showNewLinkWizardForTerm(final Term term) {
        linkDialog.showNewLinkWizardForTerm(term);
    }

    @Override
    public void showNewLinkWizardForTopic(final Topic topic) {
        linkDialog.showNewLinkWizardForTopic(topic);
    }

    @Override
    public void showNewTermWizard() {
        termDialog.showNewTermWizard();
    }

    @Override
    public void showNewTopicWizard() {
        topicDialog.showNewTopicWizard();
    }
    
}

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

/**
 *
 * @author Naoghuman
 */
final class DefaultLinkDialog implements LinkDialog {

    @Override
    public void showNewLinkWizardForTerm(final Term term) {
        LoggerFacade.getDefault().debug(this.getClass(), String.format("showNewLinkWizardForTerm(Term=%s)", term.getTitle())); // NOI18N
        
    }

    @Override
    public void showNewLinkWizardForTopic(final Topic topic) {
        LoggerFacade.getDefault().debug(this.getClass(), String.format("showNewLinkWizardForTerm(Term=%s)", topic.getTitle())); // NOI18N
        /*
        TODO
         - vbLinkArea: container for all links
        
         1) show dialog which allowed to create a link.
             a) user can choose an existing link.
             b) user can create a new link.
             c) user can edit an existing link.
         2) the user-choose (link) will be mapped with the topic.
        
         3) reload the vbLinkArea
             - container for all links
             - new IActionConfiguration.CONSTANT for refreshing the container
             - need here a DYNAMIC action?
        */
    }
    
}

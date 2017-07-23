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

import static com.github.naoghuman.abclist.configuration.IActionConfiguration.ACTION__APPLICATION__REFRESH_NAVIGATION_TAB_TOPICS;
import com.github.naoghuman.abclist.model.ModelProvider;
import com.github.naoghuman.abclist.model.Topic;
import com.github.naoghuman.abclist.sql.SqlProvider;
import com.github.naoghuman.lib.action.core.ActionHandlerFacade;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;

/**
 *
 * @author Naoghuman
 */
final class TopicDialogImpl implements TopicDialogs {

    @Override
    public void showTopicWizard() {
        // TODO replace it with AnchorPane (transparent dialog), show warning when title exists
        final TextInputDialog dialog = new TextInputDialog(); // NOI18N
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setHeaderText("Create Topic"); // NOI18N
        dialog.setResizable(false);
        dialog.setTitle("Topic Wizard"); // NOI18N
        
        final Optional<String> result = dialog.showAndWait();
        if (
                result.isPresent()
                && !result.get().isEmpty()
        ) {
            // Check if the [Topic] always exists
            final ObservableList<Topic> topics = SqlProvider.getDefault().findAllTopics();
            final String title = result.get();
            for (Topic topic : topics) {
                if (topic.getTitle().equals(title)) {
                    return;
                }
            }
            
            // Create a new [Topic]
            final Topic topic = ModelProvider.getDefault().getTopic(title);
            SqlProvider.getDefault().createTopic(topic);
            
            // Update gui
            ActionHandlerFacade.getDefault().handle(ACTION__APPLICATION__REFRESH_NAVIGATION_TAB_TOPICS);
        }
    }
    
}

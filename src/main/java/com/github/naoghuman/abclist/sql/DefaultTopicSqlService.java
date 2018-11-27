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
package com.github.naoghuman.abclist.sql;

import com.github.naoghuman.abclist.configuration.IDefaultConfiguration;
import com.github.naoghuman.abclist.configuration.ITopicConfiguration;
import com.github.naoghuman.abclist.model.Topic;
import com.github.naoghuman.lib.database.core.DatabaseFacade;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
final class DefaultTopicSqlService implements IDefaultConfiguration, TopicSqlService {
    
    @Override
    public void createTopic(Topic topic) {
        if (Objects.equals(topic.getId(), DEFAULT_ID)) {
            topic.setId(System.currentTimeMillis());
            DatabaseFacade.getDefault().getCrudService().create(topic);
        }
        else {
            this.updateTopic(topic);
        }
    }
    
    @Override
    public ObservableList<Topic> findAllTopics() {
        final ObservableList<Topic> allTopics = FXCollections.observableArrayList();
        final List<Topic> topics = DatabaseFacade.getDefault().getCrudService()
                .findByNamedQuery(Topic.class, ITopicConfiguration.NAMED_QUERY__NAME__FIND_ALL);
        
        allTopics.addAll(topics);
        Collections.sort(allTopics);

        return allTopics;
    }
    
    @Override
    public void updateTopic(Topic topic) {
        DatabaseFacade.getDefault().getCrudService().update(topic);
    }
    
}

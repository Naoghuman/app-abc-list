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
import com.github.naoghuman.abclist.configuration.IExerciseConfiguration;
import com.github.naoghuman.abclist.model.Exercise;
import com.github.naoghuman.lib.database.core.DatabaseFacade;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
final class ExerciseSqlService implements IDefaultConfiguration, IExerciseConfiguration {
    
    private static final Optional<ExerciseSqlService> INSTANCE = Optional.of(new ExerciseSqlService());

    public static final ExerciseSqlService getDefault() {
        return INSTANCE.get();
    }
    
    private ExerciseSqlService() {
        
    }
    
    void create(Exercise exercise) {
        if (Objects.equals(exercise.getId(), DEFAULT_ID)) {
            exercise.setId(System.currentTimeMillis());
            DatabaseFacade.getDefault().getCrudService().create(exercise);
        }
        else {
            this.update(exercise);
        }
    }
    
    ObservableList<Exercise> findAllExercisesWithTopicId(long topicId) {
        final ObservableList<Exercise> allExercisesWithTopicId = FXCollections.observableArrayList();
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(EXERCISE__COLUMN_NAME__TOPIC_ID, topicId);
        
        final List<Exercise> exercises = DatabaseFacade.getDefault().getCrudService()
                .findByNamedQuery(Exercise.class, NAMED_QUERY__NAME__FIND_ALL_WITH_TOPIC_ID, parameters);

        allExercisesWithTopicId.addAll(exercises);
        Collections.sort(allExercisesWithTopicId);

        return allExercisesWithTopicId;
    }
    
    void update(Exercise exercise) {
        DatabaseFacade.getDefault().getCrudService().update(exercise);
    }
    
}

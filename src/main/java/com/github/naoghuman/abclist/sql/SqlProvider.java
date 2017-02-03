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
import com.github.naoghuman.abclist.configuration.IExerciseTermConfiguration;
import com.github.naoghuman.abclist.model.Exercise;
import com.github.naoghuman.abclist.model.ExerciseTerm;
import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.abclist.model.Topic;
import com.github.naoghuman.lib.database.api.DatabaseFacade;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
public class SqlProvider implements IDefaultConfiguration, IExerciseTermConfiguration {
    
    private static final Optional<SqlProvider> instance = Optional.of(new SqlProvider());

    public static final SqlProvider getDefault() {
        return instance.get();
    }
    
    private SqlProvider() {
        
    }
    
    public long countAllExerciseTermsWithTermId(long termId) {
        return ExerciseTermSqlService.getDefault().countAllExerciseTermsWithTermId(termId);
    }
    
    public void createExercise(Exercise exercise) {
        ExerciseSqlService.getDefault().create(exercise);
    }
    
    public void createExerciseTerm(ExerciseTerm exerciseTerm) {
        ExerciseTermSqlService.getDefault().create(exerciseTerm);
    }
    
    public void createTerm(Term term) {
        TermSqlService.getDefault().create(term);
    }
    
    public void createTopic(Topic topic) {
        TopicSqlService.getDefault().create(topic);
    }

    public void deleteAllExerciseTermsWithExerciseId(long exerciseId) {
        ExerciseTermSqlService.getDefault().deleteAllExerciseTermsWithExerciseId(exerciseId);
    }
    
    public ObservableList<ExerciseTerm> findAllExerciseTermsWithExerciseId(long exerciseId) {
        return ExerciseTermSqlService.getDefault().findAllExerciseTermsWithExerciseId(exerciseId);
    }
    
    public ObservableList<Exercise> findAllExercisesWithTopicId(long topicId) {
        return ExerciseSqlService.getDefault().findAllExercisesWithTopicId(topicId);
    }
    
    public ObservableList<Term> findAllTerms() {
        return TermSqlService.getDefault().findAllTerms();
    }
    
    public ObservableList<Term> findAllTermsInExerciseTerm(ObservableList<ExerciseTerm> exerciseTerms) {
        return ExerciseTermSqlService.getDefault().findAllTermsInExerciseTerm(exerciseTerms);
    }

    public ObservableList<Term> findAllTermsInExerciseTermWithoutParent() {
        final ObservableList<Term> terms = FXCollections.observableArrayList();
        terms.addAll(this.findAllTerms());
        
        return ExerciseTermSqlService.getDefault().findAllTermsInExerciseTermWithoutParent(terms);
    }
	
    public ObservableList<Term> findAllTermsWithTitle(String title) {
        return TermSqlService.getDefault().findAllTermsWithTitle(title);
    }

    public ObservableList<Term> findAllTermsWithTopicId(long topicId) {
        final ObservableList<Term> allTermsWithTopicId = FXCollections.observableArrayList();
        
        final ObservableList<Exercise> observableListExercises = this.findAllExercisesWithTopicId(topicId);
        final Set<Long> uniqueTermIds = new LinkedHashSet<>();
        observableListExercises.stream()
                .forEach(exercise -> {
                    final ObservableList<ExerciseTerm> exerciseTerms = this.findAllExerciseTermsWithExerciseId(exercise.getId());
                    exerciseTerms.stream()
                            .forEach(exerciseTerm -> {
                                uniqueTermIds.add(exerciseTerm.getTermId());
                            });
                });
        
        uniqueTermIds.stream()
                .forEach(termId -> {
                    allTermsWithTopicId.add(TermSqlService.getDefault().findById(termId));
                });
        Collections.sort(allTermsWithTopicId);

        return allTermsWithTopicId;
    }
    
    public ObservableList<Topic> findAllTopics() {
        return TopicSqlService.getDefault().findAllTopics();
    }

    public <T extends Object> Optional<T> findById(Class<T> type, long entityId) {
        return Optional.ofNullable(DatabaseFacade.getDefault().getCrudService().findById(type, entityId));
    }

    public Optional<ExerciseTerm> findExerciseTerm(long exerciseId, long termId) {
        return ExerciseTermSqlService.getDefault().findExerciseTerm(exerciseId, termId);
    }

    public boolean isExerciseTermMarkAsWrong(long exerciseId, long termId) {
        return ExerciseTermSqlService.getDefault().isExerciseTermMarkAsWrong(exerciseId, termId);
    }
    
    public void updateExercise(Exercise exercise) {
        ExerciseSqlService.getDefault().update(exercise);
    }

    public void updateExerciseTerm(ExerciseTerm exerciseTerm) {
        ExerciseTermSqlService.getDefault().update(exerciseTerm);
    }
    
    public void updateTerm(Term term) {
        TermSqlService.getDefault().update(term);
    }
    
    public void updateTopic(Topic topic) {
        TopicSqlService.getDefault().update(topic);
    }
    
}

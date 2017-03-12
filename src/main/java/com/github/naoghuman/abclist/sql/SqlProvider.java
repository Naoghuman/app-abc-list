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
import com.github.naoghuman.abclist.model.Link;
import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.abclist.model.Topic;
import com.github.naoghuman.lib.database.api.DatabaseFacade;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang3.time.StopWatch;

/**
 *
 * @author Naoghuman
 */
public class SqlProvider implements IDefaultConfiguration, IExerciseTermConfiguration {
    
    private static final Optional<SqlProvider> INSTANCE = Optional.of(new SqlProvider());

    public static final SqlProvider getDefault() {
        return INSTANCE.get();
    }
    
    private SqlProvider() {
        
    }
    
//    public long countAllExerciseTermsWithTermId(long termId) {
//        return ExerciseTermSqlService.getDefault().countAllExerciseTermsWithTermId(termId);
//    }
    
    public void createExercise(Exercise exercise) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
                
        ExerciseSqlService.getDefault().create(exercise);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "createExercise(Exercise exercise)"); // NOI18N
        stopWatch.stop();
    }
    
    public void createExerciseTerm(ExerciseTerm exerciseTerm) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
            
        ExerciseTermSqlService.getDefault().create(exerciseTerm);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "createExerciseTerm(ExerciseTerm exerciseTerm)"); // NOI18N
        stopWatch.stop();
    }
    
    public void createTerm(Term term) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        TermSqlService.getDefault().create(term);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "createTerm(Term term)"); // NOI18N
        stopWatch.stop();
    }
    
    public void createTopic(Topic topic) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        TopicSqlService.getDefault().create(topic);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "createTopic(Topic topic)"); // NOI18N
        stopWatch.stop();
    }

    public void deleteAllExerciseTermsWithExerciseId(long exerciseId) {
        // StopWatch is in delegated method
        ExerciseTermSqlService.getDefault().deleteAllExerciseTermsWithExerciseId(exerciseId);
    }
    
    public ObservableList<ExerciseTerm> findAllExerciseTermsWithExerciseId(long exerciseId) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final ObservableList<ExerciseTerm>  exerciseTerms = ExerciseTermSqlService.getDefault().findAllExerciseTermsWithExerciseId(exerciseId);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), exerciseTerms.size(), "findAllExerciseTermsWithExerciseId(long exerciseId)"); // NOI18N
        stopWatch.stop();
        
        return exerciseTerms;
    }
    
    public ObservableList<Exercise> findAllExercisesWithTopicId(long topicId) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final ObservableList<Exercise> exercises =  ExerciseSqlService.getDefault().findAllExercisesWithTopicId(topicId);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), exercises.size(), "findAllExercisesWithTopicId(long topicId)"); // NOI18N
        stopWatch.stop();
        
        return exercises;
    }
    
    public ObservableList<Link> findAllLinks() {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final ObservableList<Link> links = LinkSqlService.getDefault().findAllLinks();
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), links.size(), "findAllLinks()"); // NOI18N
        stopWatch.stop();
        
        return links;
    }
    
    public ObservableList<Term> findAllTerms() {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final ObservableList<Term> terms = TermSqlService.getDefault().findAllTerms();
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), terms.size(), "findAllTerms()"); // NOI18N
        stopWatch.stop();
        
        return terms;
    }
    
    public ObservableList<Term> findAllTermsInExerciseTerm(ObservableList<ExerciseTerm> exerciseTerms) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final ObservableList<Term> terms = ExerciseTermSqlService.getDefault().findAllTermsInExerciseTerm(exerciseTerms);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), terms.size(), "findAllTermsInExerciseTerm(ObservableList<ExerciseTerm> exerciseTerms)"); // NOI18N
        stopWatch.stop();
        
        return terms;
    }

    public ObservableList<Term> findAllTermsInExerciseTermsWithoutParent() {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final ObservableList<Term> allTerms = FXCollections.observableArrayList();
        allTerms.addAll(this.findAllTerms());
        
        final ObservableList<Term> terms = ExerciseTermSqlService.getDefault().findAllTermsInExerciseTermsWithoutParent(allTerms);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), terms.size(), "findAllTermsInExerciseTermsWithoutParent()"); // NOI18N
        stopWatch.stop();
        
        return terms;
    }
	
    public ObservableList<Term> findAllTermsWithTitle(String title) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final ObservableList<Term> terms = TermSqlService.getDefault().findAllTermsWithTitle(title);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), terms.size(), "findAllTermsWithTitle(String title)"); // NOI18N
        stopWatch.stop();
        
        return terms;
    }

    public ObservableList<Term> findAllTermsWithTopicId(long topicId) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
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
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), allTermsWithTopicId.size(), "findAllTermsWithTopicId(long topicId)"); // NOI18N
        stopWatch.stop();

        return allTermsWithTopicId;
    }
    
    public ObservableList<Topic> findAllTopics() {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final ObservableList<Topic> topics = TopicSqlService.getDefault().findAllTopics();
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), topics.size(), "findAllTopics()"); // NOI18N
        stopWatch.stop();
        
        return topics;
    }
    
    // TODO UnitTest
    public <T extends Object> Optional<T> findById(Class<T> type, long entityId) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final Optional<T> optional = Optional.ofNullable(DatabaseFacade.getDefault().getCrudService().findById(type, entityId));
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "findById(Class<T> type, long entityId)"); // NOI18N
        stopWatch.stop();
        
        return optional;
    }

    public Optional<ExerciseTerm> findExerciseTerm(long exerciseId, long termId) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final Optional<ExerciseTerm> optional = ExerciseTermSqlService.getDefault().findExerciseTerm(exerciseId, termId);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "findExerciseTerm(long exerciseId, long termId)"); // NOI18N
        stopWatch.stop();
        
        return optional;
    }

//    public boolean isExerciseTermMarkAsWrong(long exerciseId, long termId) {
//        return ExerciseTermSqlService.getDefault().isExerciseTermMarkAsWrong(exerciseId, termId);
//    }
    
    private void printToLog(String split, int entities, String method) {
        final StringBuilder sb = new StringBuilder();
        sb.append("  + Need "); // NOI18N
        sb.append(split);
        sb.append(" for [");
        sb.append(entities);
        sb.append("] entities in [");
        sb.append(method);
        sb.append("]");
        
        LoggerFacade.getDefault().debug(this.getClass(), sb.toString());
    }
    
    public void updateExercise(Exercise exercise) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        ExerciseSqlService.getDefault().update(exercise);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "updateExercise(Exercise exercise)"); // NOI18N
        stopWatch.stop();
    }

    public void updateExerciseTerm(ExerciseTerm exerciseTerm) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        ExerciseTermSqlService.getDefault().update(exerciseTerm);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "updateExerciseTerm(ExerciseTerm exerciseTerm)"); // NOI18N
        stopWatch.stop();
    }
    
    public void updateTerm(Term term) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        TermSqlService.getDefault().update(term);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "updateTerm(Term term)"); // NOI18N
        stopWatch.stop();
    }
    
    public void updateTopic(Topic topic) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        TopicSqlService.getDefault().update(topic);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "updateTopic(Topic topic)"); // NOI18N
        stopWatch.stop();
    }
    
}

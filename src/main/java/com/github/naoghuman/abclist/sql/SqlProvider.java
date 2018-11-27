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
import com.github.naoghuman.abclist.model.LinkMapping;
import com.github.naoghuman.abclist.model.LinkMappingType;
import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.abclist.model.Topic;
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
public class SqlProvider implements 
        IDefaultConfiguration, IExerciseTermConfiguration,
        ExerciseSqlService,    ExerciseTermSqlService,     LinkMappingSqlService,
        LinkSqlService,        TermSqlService,             TopicSqlService
{
    private static final Optional<SqlProvider> INSTANCE = Optional.of(new SqlProvider());

    public static final SqlProvider getDefault() {
        return INSTANCE.get();
    }
    
    private final ExerciseSqlService     exerciseSqlService     = new DefaultExerciseSqlService();
    private final ExerciseTermSqlService exerciseTermSqlService = new DefaultExerciseTermSqlService();
    private final LinkMappingSqlService  linkMappingSqlService  = new DefaultLinkMappingSqlService();
    private final LinkSqlService         linkSqlService         = new DefaultLinkSqlService();
    private final TermSqlService         termSqlService         = new DefaultTermSqlService();
    private final TopicSqlService        topicSqlService        = new DefaultTopicSqlService();
    
    private SqlProvider() {
        
    }
    
    @Override
    public long countAllExerciseTermsWithTermId(final long termId) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final long countedExerciseTerms = exerciseTermSqlService.countAllExerciseTermsWithTermId(termId);
        
        sqlPerformance.stop(-1, "countAllExerciseTermsWithTermId(long)"); // NOI18N
        
        return countedExerciseTerms;
    }
    
    @Override
    public void createExercise(final Exercise exercise) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        exerciseSqlService.createExercise(exercise);
        
        sqlPerformance.stop(1, "createExercise(Exercise)"); // NOI18N
    }
    
    @Override
    public void createExerciseTerm(final ExerciseTerm exerciseTerm) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        exerciseTermSqlService.createExerciseTerm(exerciseTerm);
        
        sqlPerformance.stop(1, "createExerciseTerm(ExerciseTerm)"); // NOI18N
    }

    @Override
    public void createLink(final Link link) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        linkSqlService.createLink(link);
        
        sqlPerformance.stop(1, "createLink(Link)"); // NOI18N
    }

    @Override
    public void createLinkMapping(final LinkMapping linkMapping) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        linkMappingSqlService.createLinkMapping(linkMapping);
        
        sqlPerformance.stop(1, "createLinkMapping(LinkMapping)"); // NOI18N
    }
    
    @Override
    public void createTerm(final Term term) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        termSqlService.createTerm(term);
        
        sqlPerformance.stop(1, "createTerm(Term)"); // NOI18N
    }
    
    @Override
    public void createTopic(final Topic topic) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        topicSqlService.createTopic(topic);
        
        sqlPerformance.stop(1, "createTopic(Topic)"); // NOI18N
    }

    @Override
    public int deleteAllExerciseTermsWithExerciseId(final long exerciseId) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        int deletedEntities = exerciseTermSqlService.deleteAllExerciseTermsWithExerciseId(exerciseId);
        
        sqlPerformance.stop(deletedEntities, "deleteAllExerciseTermsWithExerciseId(long)"); // NOI18N
        
        return deletedEntities;
    }
    
    @Override
    public ObservableList<ExerciseTerm> findAllExerciseTermsWithExerciseId(final long exerciseId) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final ObservableList<ExerciseTerm>  exerciseTerms = exerciseTermSqlService.findAllExerciseTermsWithExerciseId(exerciseId);
        
        sqlPerformance.stop(exerciseTerms.size(), "deleteAllExerciseTermsWithExerciseId(long)"); // NOI18N
        
        return exerciseTerms;
    }
    
    @Override
    public ObservableList<Exercise> findAllExercisesWithTopicId(final long topicId) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final ObservableList<Exercise> exercises =  exerciseSqlService.findAllExercisesWithTopicId(topicId);
        
        sqlPerformance.stop(exercises.size(), "findAllExercisesWithTopicId(long)"); // NOI18N
        
        return exercises;
    }
    
    @Override
    public ObservableList<Link> findAllLinks() {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final ObservableList<Link> links = linkSqlService.findAllLinks();
        
        sqlPerformance.stop(links.size(), "findAllLinks()"); // NOI18N
        
        return links;
    }

    @Override
    public ObservableList<Link> findAllLinks(final ObservableList<LinkMapping> linkMappings) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final ObservableList<Link> links = linkSqlService.findAllLinks(linkMappings);
        
        sqlPerformance.stop(links.size(), "findAllLinks(ObservableList<LinkMapping>)"); // NOI18N
        
        return links;
    }

    @Override
    public ObservableList<LinkMapping> findAllLinksWithParent(final long parentId, final LinkMappingType parentType) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final ObservableList<LinkMapping> linkMappings = linkMappingSqlService.findAllLinksWithParent(parentId, parentType);

        sqlPerformance.stop(linkMappings.size(), "findAllLinksInLinkMappingWithParent(long, LinkMappingType)"); // NOI18N
        
        return linkMappings;
    }

    @Override
    public ObservableList<LinkMapping> findAllLinksWithoutParent() {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final ObservableList<LinkMapping> linkMappings = linkMappingSqlService.findAllLinksWithoutParent();

        sqlPerformance.stop(linkMappings.size(), "findAllLinksInLinkMappingWithoutParent()"); // NOI18N
        
        return linkMappings;
    }
    
    @Override
    public ObservableList<Term> findAllTerms() {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final ObservableList<Term> terms = termSqlService.findAllTerms();
        
        sqlPerformance.stop(terms.size(), "findAllTerms()"); // NOI18N
        
        return terms;
    }
    
    @Override
    public ObservableList<Term> findAllTermsInExerciseTerms(final ObservableList<ExerciseTerm> exerciseTerms) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final ObservableList<Term> terms = exerciseTermSqlService.findAllTermsInExerciseTerms(exerciseTerms);
        
        sqlPerformance.stop(terms.size(), "findAllTermsInExerciseTerms(ObservableList<ExerciseTerm>)"); // NOI18N
        
        return terms;
    }
    
    @Override
    public ObservableList<Term> findAllTermsInExerciseTermsWithoutParent(final ObservableList<Term> terms) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final ObservableList<Term> termsWithoutParent = exerciseTermSqlService.findAllTermsInExerciseTermsWithoutParent(terms);
        
        sqlPerformance.stop(termsWithoutParent.size(), "findAllTermsInExerciseTermsWithoutParent()"); // NOI18N
        
        return termsWithoutParent;
    }
	
    @Override
    public ObservableList<Term> findAllTermsWithTitle(final String title) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final ObservableList<Term> terms = termSqlService.findAllTermsWithTitle(title);
        
        sqlPerformance.stop(terms.size(), "findAllTermsWithTitle(String)"); // NOI18N
        
        return terms;
    }

    public ObservableList<Term> findAllTermsWithTopicId(final long topicId) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
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
                    final Optional<Term> optional = termSqlService.findTerm(termId);
                    if (optional.isPresent()) {
                        allTermsWithTopicId.add(optional.get());
                    }
                });
        Collections.sort(allTermsWithTopicId);
        
        sqlPerformance.stop(allTermsWithTopicId.size(), "findAllTermsWithTopicId(long)"); // NOI18N

        return allTermsWithTopicId;
    }
    
    @Override
    public ObservableList<Topic> findAllTopics() {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final ObservableList<Topic> topics = topicSqlService.findAllTopics();
        
        sqlPerformance.stop(topics.size(), "findAllTopics()"); // NOI18N
        
        return topics;
    }

    @Override
    public Optional<Exercise> findExercise(long exerciseId) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final Optional<Exercise> optional = exerciseSqlService.findExercise(exerciseId);
        
        sqlPerformance.stop(1, "findExercise(long)"); // NOI18N
        
        return optional;
    }

    @Override
    public Optional<ExerciseTerm> findExerciseTerm(final long exerciseId, final long termId) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final Optional<ExerciseTerm> optional = exerciseTermSqlService.findExerciseTerm(exerciseId, termId);
        
        sqlPerformance.stop(1, "findExerciseTerm(long, long)"); // NOI18N
        
        return optional;
    }

    @Override
    public Optional<Term> findTerm(final long termId) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final Optional<Term> optional = termSqlService.findTerm(termId);
        
        sqlPerformance.stop(1, "findTerm(long)"); // NOI18N
        
        return optional;
    }

    @Override
    public boolean isExerciseTermMarkAsWrong(final long exerciseId, final long termId) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        final boolean isExerciseTermMarkAsWrong = exerciseTermSqlService.isExerciseTermMarkAsWrong(exerciseId, termId);
        
        sqlPerformance.stop(1, "isExerciseTermMarkAsWrong(long, long)"); // NOI18N
        
        return isExerciseTermMarkAsWrong;
    }
    
    @Override
    public void updateExercise(final Exercise exercise) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        exerciseSqlService.updateExercise(exercise);
        
        sqlPerformance.stop(1, "updateExercise(Exercise)"); // NOI18N
    }

    @Override
    public void updateExerciseTerm(final ExerciseTerm exerciseTerm) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        exerciseTermSqlService.updateExerciseTerm(exerciseTerm);
        
        sqlPerformance.stop(1, "updateExerciseTerm(ExerciseTerm)"); // NOI18N
    }

    @Override
    public void updateLink(final Link link) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        linkSqlService.updateLink(link);
        
        sqlPerformance.stop(1, "updateLink(Link)"); // NOI18N
    }

    @Override
    public void updateLinkMapping(final LinkMapping linkMapping) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        linkMappingSqlService.updateLinkMapping(linkMapping);
        
        sqlPerformance.stop(1, "updateLinkMapping(Link)"); // NOI18N
    }
    
    @Override
    public void updateTerm(final Term term) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        termSqlService.updateTerm(term);
        
        sqlPerformance.stop(1, "updateTerm(Term )"); // NOI18N
    }
    
    @Override
    public void updateTopic(final Topic topic) {
        final SqlPerformance sqlPerformance = SqlPerformance.create();
        sqlPerformance.start();
        
        topicSqlService.updateTopic(topic);
        
        sqlPerformance.stop(1, "updateTopic(Topic)"); // NOI18N
    }
    
}

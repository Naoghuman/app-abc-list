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
import com.github.naoghuman.abclist.model.ExerciseTerm;
import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.lib.database.api.DatabaseFacade;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.Query;
import org.apache.commons.lang3.time.StopWatch;

/**
 *
 * @author Naoghuman
 */
public class ExerciseTermSqlService implements IDefaultConfiguration, IExerciseTermConfiguration {
    
    private static final Optional<ExerciseTermSqlService> instance = Optional.of(new ExerciseTermSqlService());

    public static final ExerciseTermSqlService getDefault() {
        return instance.get();
    }
    
    private ExerciseTermSqlService() {
        this.initialize();
    }
    
    private void initialize() {
        // Create a [dummy] ExerciseTerm - so the table exists in the database
        this.create(new ExerciseTerm());
    }
    
    long countAllExerciseTermsWithTermId(long termId) {
        final Query query = DatabaseFacade.getDefault().getCrudService()
                .getEntityManager()
                .createNamedQuery(NAMED_QUERY__NAME__COUNT_ALL_EXERCISE_TERMS_WITH_TERM_ID);
        query.setParameter(EXERCISE_TERM__COLUMN_NAME__TERM_ID, termId);
        
        long countedTermsWithoutParent = NO_TERMS_FOUND;
        try {
            countedTermsWithoutParent = (long) query.getSingleResult();
        } catch (Exception e) {
            
        }
        
        return countedTermsWithoutParent;
    }
    
    void create(ExerciseTerm exerciseTerm) {
        exerciseTerm.setId(System.currentTimeMillis());
        DatabaseFacade.getDefault().getCrudService().create(exerciseTerm);
    }

    void deleteAllExerciseTermsWithExerciseId(long exerciseId) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final ObservableList<ExerciseTerm> exerciseTerms = SqlProvider.getDefault().findAllExerciseTermsWithExerciseId(exerciseId);
        
        DatabaseFacade.getDefault().getCrudService().beginTransaction();
        exerciseTerms.stream()
                .forEach(exerciseTerm -> {
                    DatabaseFacade.getDefault().getCrudService().getEntityManager().remove(exerciseTerm);
                });
        DatabaseFacade.getDefault().getCrudService().commitTransaction();
        
        stopWatch.split();
        LoggerFacade.getDefault().debug(this.getClass(), "  + Need " + stopWatch.toSplitString() + " for executing [deleteAllExerciseTermsWithExerciseId(long)]"); // NOI18N
        this.printToLog(stopWatch.toSplitString(), exerciseTerms.size(), "deleteAllExerciseTermsWithExerciseId(long exerciseId)"); // NOI18N
        stopWatch.stop();
    }
    
    ObservableList<ExerciseTerm> findAllExerciseTermsWithExerciseId(long exerciseId) {
        final ObservableList<ExerciseTerm> allTermsWithExerciseId = FXCollections.observableArrayList();
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(IExerciseTermConfiguration.EXERCISE_TERM__COLUMN_NAME__EXERCISE_ID, exerciseId);
        
        final List<ExerciseTerm> exerciseTerms = DatabaseFacade.getDefault().getCrudService()
                .findByNamedQuery(ExerciseTerm.class, IExerciseTermConfiguration.NAMED_QUERY__NAME__FIND_ALL_EXERCISE_TERMS_WITH_EXERCISE_ID, parameters);
        
        allTermsWithExerciseId.addAll(exerciseTerms);
        Collections.sort(allTermsWithExerciseId);

        return allTermsWithExerciseId;
    }
    
    ObservableList<Term> findAllTermsInExerciseTerm(ObservableList<ExerciseTerm> exerciseTerms) {
        final ObservableList<Term> allTermsInExerciseTerms = FXCollections.observableArrayList();
        exerciseTerms.stream()
                .map((exerciseTerm) -> DatabaseFacade.getDefault().getCrudService().findById(Term.class, exerciseTerm.getTermId()))
                .forEach((term) -> {
                    allTermsInExerciseTerms.add(term);
                });
        
        Collections.sort(allTermsInExerciseTerms);
        
        return allTermsInExerciseTerms;
    }
    
    ObservableList<Term> findAllTermsInExerciseTermsWithoutParent(ObservableList<Term> terms) {
        final ObservableList<Term> allTermsWithOutParent = FXCollections.observableArrayList();
        
        long counterTermInExercise = NO_TERMS_FOUND;
        for (Term term : terms) {
            counterTermInExercise = NO_TERMS_FOUND;
            counterTermInExercise = ExerciseTermSqlService.getDefault().countAllExerciseTermsWithTermId(term.getId());
            if (Objects.equals(counterTermInExercise, NO_TERMS_FOUND)) {
                allTermsWithOutParent.add(term);
            }
        }
        
        Collections.sort(allTermsWithOutParent);

        return allTermsWithOutParent;
    }

    Optional<ExerciseTerm> findExerciseTerm(long exerciseId, long termId) {
        final Query query = DatabaseFacade.getDefault().getCrudService()
                .getEntityManager()
                .createNamedQuery(NAMED_QUERY__NAME__FIND_EXERCISE_TERM_WITH_EXERCISE_ID_AND_TERM_ID);
        query.setParameter(EXERCISE_TERM__COLUMN_NAME__EXERCISE_ID, exerciseId);
        query.setParameter(EXERCISE_TERM__COLUMN_NAME__TERM_ID, termId);
        
        Optional<ExerciseTerm> optional = Optional.empty();
        try {
            optional = Optional.ofNullable((ExerciseTerm) query.getSingleResult());
        } catch (Exception e) {
            
        }
        
        return optional;
    }

    boolean isExerciseTermMarkAsWrong(long exerciseId, long termId) {
        final Query query = DatabaseFacade.getDefault().getCrudService()
                .getEntityManager()
                .createNamedQuery(NAMED_QUERY__NAME__IS_EXERCISE_TERM_MARK_AS_WRONG);
        query.setParameter(EXERCISE_TERM__COLUMN_NAME__EXERCISE_ID, exerciseId);
        query.setParameter(EXERCISE_TERM__COLUMN_NAME__TERM_ID, termId);
        
        boolean isExerciseTermMarkAsWrong = false;
        try {
            isExerciseTermMarkAsWrong = (boolean) query.getSingleResult();
        } catch (Exception e) {
            
        }
        
        return isExerciseTermMarkAsWrong;
    }
    
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

    void update(ExerciseTerm exerciseTerm) {
        DatabaseFacade.getDefault().getCrudService().update(exerciseTerm);
    }
    
}

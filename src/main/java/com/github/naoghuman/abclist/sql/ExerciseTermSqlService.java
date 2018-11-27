/*
 * Copyright (C) 2018 Naoghuman's dream
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

import com.github.naoghuman.abclist.model.ExerciseTerm;
import com.github.naoghuman.abclist.model.Term;
import java.util.Optional;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
public interface ExerciseTermSqlService {

    long countAllExerciseTermsWithTermId(final long termId);

    void createExerciseTerm(final ExerciseTerm exerciseTerm);

    int deleteAllExerciseTermsWithExerciseId(final long exerciseId);

    ObservableList<ExerciseTerm> findAllExerciseTermsWithExerciseId(final long exerciseId);

    ObservableList<Term> findAllTermsInExerciseTerms(final ObservableList<ExerciseTerm> exerciseTerms);

    ObservableList<Term> findAllTermsInExerciseTermsWithoutParent(final ObservableList<Term> terms);

    Optional<ExerciseTerm> findExerciseTerm(final long exerciseId, final long termId);

    boolean isExerciseTermMarkAsWrong(final long exerciseId, final long termId);

    void updateExerciseTerm(final ExerciseTerm exerciseTerm);
    
}

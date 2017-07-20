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
import com.github.naoghuman.abclist.configuration.ITermConfiguration;
import com.github.naoghuman.abclist.model.Term;
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
final class TermSqlService implements IDefaultConfiguration, ITermConfiguration {
    
    private static final Optional<TermSqlService> INSTANCE = Optional.of(new TermSqlService());

    public static final TermSqlService getDefault() {
        return INSTANCE.get();
    }
    
    private TermSqlService() {
        
    }
    
    void create(Term term) {
        if (Objects.equals(term.getId(), DEFAULT_ID)) {
            term.setId(System.currentTimeMillis());
            DatabaseFacade.getDefault().getCrudService().create(term);
        }
        else {
            this.update(term);
        }
    }
    
    ObservableList<Term> findAllTerms() {
        final ObservableList<Term> allTerms = FXCollections.observableArrayList();
        final List<Term> terms = DatabaseFacade.getDefault().getCrudService()
                .findByNamedQuery(Term.class, NAMED_QUERY__NAME__FIND_ALL);
        
        allTerms.addAll(terms);
        Collections.sort(allTerms);

        return allTerms;
    }
	
    ObservableList<Term> findAllTermsWithTitle(String title) {
        final ObservableList<Term> allTermsWithTitle = FXCollections.observableArrayList();
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(TERM__COLUMN_NAME__TITLE, title);
        
        final List<Term> terms = DatabaseFacade.getDefault().getCrudService()
                .findByNamedQuery(Term.class, NAMED_QUERY__NAME__FIND_ALL_WITH_TITLE, parameters);
        
        allTermsWithTitle.addAll(terms);
        Collections.sort(allTermsWithTitle);

        return allTermsWithTitle;
    }
    
    Term findById(long termId) {
        return DatabaseFacade.getDefault().getCrudService().findById(Term.class, termId);
    }
    
    void update(Term term) {
        DatabaseFacade.getDefault().getCrudService().update(term);
    }
    
}

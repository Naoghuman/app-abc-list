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
import com.github.naoghuman.abclist.configuration.ILinkConfiguration;
import com.github.naoghuman.abclist.model.Link;
import com.github.naoghuman.lib.database.api.DatabaseFacade;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
final class LinkSqlService implements IDefaultConfiguration, ILinkConfiguration {
    
    private static final Optional<LinkSqlService> INSTANCE = Optional.of(new LinkSqlService());

    public static final LinkSqlService getDefault() {
        return INSTANCE.get();
    }
    
    private LinkSqlService() {
        
    }
    
    void create(Link link) {
        if (Objects.equals(link.getId(), DEFAULT_ID)) {
            link.setId(System.currentTimeMillis());
            DatabaseFacade.getDefault().getCrudService().create(link);
        }
        else {
            this.update(link);
        }
    }
    
    ObservableList<Link> findAllLinks() {
        final ObservableList<Link> allLinks = FXCollections.observableArrayList();
        final List<Link> links = DatabaseFacade.getDefault().getCrudService()
                .findByNamedQuery(Link.class, NAMED_QUERY__NAME__FIND_ALL);
        
        allLinks.addAll(links);
        Collections.sort(allLinks);

        return allLinks;
    }
    
    void update(Link link) {
        DatabaseFacade.getDefault().getCrudService().update(link);
    }
    
}

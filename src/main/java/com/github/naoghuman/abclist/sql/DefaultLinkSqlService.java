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
import com.github.naoghuman.abclist.model.LinkMapping;
import com.github.naoghuman.lib.database.core.DatabaseFacade;
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
final class DefaultLinkSqlService implements IDefaultConfiguration, ILinkConfiguration, LinkSqlService {
    
    @Override
    public void createLink(Link link) {
        if (Objects.equals(link.getId(), DEFAULT_ID)) {
            link.setId(System.currentTimeMillis());
            DatabaseFacade.getDefault().getCrudService().create(link);
        }
        else {
            this.updateLink(link);
        }
    }
    
    @Override
    public ObservableList<Link> findAllLinks() {
        final ObservableList<Link> allLinks = FXCollections.observableArrayList();
        final List<Link> links = DatabaseFacade.getDefault().getCrudService()
                .findByNamedQuery(Link.class, NAMED_QUERY__NAME__FIND_ALL);
        
        allLinks.addAll(links);
        Collections.sort(allLinks);

        return allLinks;
    }

    @Override
    public ObservableList<Link> findAllLinks(final ObservableList<LinkMapping> linkMappings) {
        final ObservableList<Link> links = FXCollections.observableArrayList();
        linkMappings.stream()
                .forEach(linkMapping -> {
                    final Optional<Link> optional = Optional.ofNullable(DatabaseFacade.getDefault().getCrudService()
                            .findById(Link.class, linkMapping.getChildId()));
                    if (optional.isPresent()) {
                        links.add(optional.get());
                    }
                });
        return links;
    }
    
    @Override
    public void updateLink(Link link) {
        DatabaseFacade.getDefault().getCrudService().update(link);
    }
    
}

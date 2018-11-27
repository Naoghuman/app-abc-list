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

import com.github.naoghuman.abclist.model.Link;
import com.github.naoghuman.abclist.model.LinkMapping;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
public interface LinkSqlService {

    void createLink(final Link link);

    ObservableList<Link> findAllLinks();

    ObservableList<Link> findAllLinks(final ObservableList<LinkMapping> linkMappings);

    void updateLink(final Link link);
    
}

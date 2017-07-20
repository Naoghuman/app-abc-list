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
import com.github.naoghuman.abclist.configuration.ILinkMappingConfiguration;
import com.github.naoghuman.abclist.model.LinkMapping;
import com.github.naoghuman.abclist.model.LinkMappingType;
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
public final class LinkMappingSqlService implements IDefaultConfiguration, ILinkMappingConfiguration {
    
    private static final Optional<LinkMappingSqlService> INSTANCE = Optional.of(new LinkMappingSqlService());

    public static final LinkMappingSqlService getDefault() {
        return INSTANCE.get();
    }
    
    private LinkMappingSqlService() {
        
    }
    
    void create(final LinkMapping linkMapping) {
        if (Objects.equals(linkMapping.getId(), DEFAULT_ID)) {
            linkMapping.setId(System.currentTimeMillis());
            DatabaseFacade.getDefault().getCrudService().create(linkMapping);
        }
        else {
            this.update(linkMapping);
        }
    }
    
    ObservableList<LinkMapping> findAllLinksInLinkMappingWithoutParent() {
        final ObservableList<LinkMapping> allLinkMappingsWithoutParent = FXCollections.observableArrayList();
        
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(LINK_MAPPING__COLUMN_NAME__PARENT_TYPE, LinkMappingType.NOT_DEFINED);
        parameters.put(LINK_MAPPING__COLUMN_NAME__CHILD_TYPE,  LinkMappingType.LINK);
        
        final List<LinkMapping> linkMappings = DatabaseFacade.getDefault().getCrudService()
                .findByNamedQuery(LinkMapping.class, NAMED_QUERY__NAME__FIND_ALL_WITH_PARENTTYPE_AND_CHILDTYPE, parameters);
        
        allLinkMappingsWithoutParent.addAll(linkMappings);
        Collections.sort(allLinkMappingsWithoutParent);

        return allLinkMappingsWithoutParent;
    }

    ObservableList<LinkMapping> findAllLinksInLinkMappingWithParent(final long parentId, final LinkMappingType parentType) {
        final ObservableList<LinkMapping> allLinkMappingsWithParent = FXCollections.observableArrayList();
        
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(LINK_MAPPING__COLUMN_NAME__PARENT_ID,   parentId);
        parameters.put(LINK_MAPPING__COLUMN_NAME__PARENT_TYPE, parentType);
        
        final List<LinkMapping> linkMappings = DatabaseFacade.getDefault().getCrudService()
                .findByNamedQuery(LinkMapping.class, NAMED_QUERY__NAME__FIND_ALL_WITH_PARENT, parameters);
        
        allLinkMappingsWithParent.addAll(linkMappings);
        Collections.sort(allLinkMappingsWithParent);

        return allLinkMappingsWithParent;
    }
    
    void update(final LinkMapping linkMapping) {
        DatabaseFacade.getDefault().getCrudService().update(linkMapping);
    }
    
}

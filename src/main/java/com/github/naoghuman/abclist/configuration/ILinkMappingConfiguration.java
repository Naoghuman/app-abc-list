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
package com.github.naoghuman.abclist.configuration;

/**
 *
 * @author Naoghuman
 */
public interface ILinkMappingConfiguration {
    
    public static final String ENTITY__TABLE_NAME__LINK_MAPPING = "LinkMapping"; // NOI18N
    
    public static final String NAMED_QUERY__NAME__FIND_ALL_WITH_PARENT = "LinkMapping.findAllWithParent"; // NOI18N
    public static final String NAMED_QUERY__QUERY__FIND_ALL_WITH_PARENT = "SELECT lm FROM LinkMapping lm WHERE lm.parentId == :parentId AND lm.parentType == :parentType"; // NOI18N
    public static final String NAMED_QUERY__NAME__FIND_ALL_WITH_PARENTTYPE_AND_CHILDTYPE = "LinkMapping.findAllWithParentTypeAndChildType"; // NOI18N
    public static final String NAMED_QUERY__QUERY__FIND_ALL_WITH_PARENTTYPE_AND_CHILDTYPE = "SELECT lm FROM LinkMapping lm WHERE lm.parentType == :parentType AND lm.childType == :childType"; // NOI18N
    
    public static final String LINK_MAPPING__COLUMN_NAME__ID = "id"; // NOI18N
    public static final String LINK_MAPPING__COLUMN_NAME__CHILD_ID = "childId"; // NOI18N
    public static final String LINK_MAPPING__COLUMN_NAME__CHILD_TYPE = "childType"; // NOI18N
    public static final String LINK_MAPPING__COLUMN_NAME__PARENT_ID = "parentId"; // NOI18N
    public static final String LINK_MAPPING__COLUMN_NAME__PARENT_TYPE = "parentType"; // NOI18N
    
}

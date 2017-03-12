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
    
    public static final String NAMED_QUERY__NAME__COUNT_ALL_LINK_MAPPINGS_WITH_LINK_ID = "LinkMapping.countAllLinkMappingsWithLinkId"; // NOI18N
    public static final String NAMED_QUERY__QUERY__COUNT_ALL_LINK_MAPPINGS_WITH_LINK_ID = "SELECT COUNT(lm) FROM LinkMapping lm WHERE lm.linkId == :linkId"; // NOI18N
    
    public static final String NAMED_QUERY__NAME__FIND_ALL_LINK_MAPPINGS_WITH_PARENT_ID = "LinkMapping.findAllLinkMappingsWithParentId"; // NOI18N
    public static final String NAMED_QUERY__QUERY__FIND_ALL_LINK_MAPPINGS_WITH_PARENT_ID = "SELECT lm FROM LinkMapping lm WHERE lm.parentId == :parentId"; // NOI18N
    
    public static final String NAMED_QUERY__NAME__FIND_LINK_MAPPING_WITH_PARENT_ID_AND_LINK_ID = "LinkMapping.findLinkMappingWithParentIdAndLinkId"; // NOI18N
    public static final String NAMED_QUERY__QUERY__FIND_LINK_MAPPING_WITH_PARENT_ID_AND_LINK_ID = "SELECT lm FROM LinkMapping lm WHERE lm.parentId == :parentId AND lm.linkId == :linkId"; // NOI18N
    
    public static final String LINK_MAPPING__COLUMN_NAME__ID = "id"; // NOI18N
    public static final String LINK_MAPPING__COLUMN_NAME__LINK_ID = "linkId"; // NOI18N
    public static final String LINK_MAPPING__COLUMN_NAME__PARENT_ID = "parentId"; // NOI18N
    
}

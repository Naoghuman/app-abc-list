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
public interface ILinkConfiguration {
    
    public static final String ENTITY__TABLE_NAME__LINK = "Link"; // NOI18N
    
    public static final String NAMED_QUERY__NAME__FIND_ALL = "Link.findAll"; // NOI18N
    public static final String NAMED_QUERY__QUERY__FIND_ALL = "SELECT l FROM Link l"; // NOI18N
    
    public static final String LINK__COLUMN_NAME__ALIAS = "aliasUrl"; // NOI18N // alias is sql-99 keyword
    public static final String LINK__COLUMN_NAME__ID = "id"; // NOI18N
    public static final String LINK__COLUMN_NAME__IMAGE = "image"; // NOI18N
    public static final String LINK__COLUMN_NAME__DESCRIPTION = "description"; // NOI18N
    public static final String LINK__COLUMN_NAME__FAVORITE = "favorite"; // NOI18N
    public static final String LINK__COLUMN_NAME__GENERATION_TIME = "generationTime"; // NOI18N
    public static final String LINK__COLUMN_NAME__URL = "url"; // NOI18N
    
}

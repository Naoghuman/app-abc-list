/*
 * Copyright (C) 2016 Naoghuman
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
public interface IDefaultConfiguration {
    
    public static final long DEFAULT_ID = -1L;
    public static final long DEFAULT_ID__TERM__SHOW_ALL_EXISTING_LINKS = -2L;
    public static final long DEFAULT_ID__TERM__SHOW_ALL_LINKS_WITHOUT_PARENT = -3L;
    public static final long DEFAULT_ID__TOPIC__SHOW_ALL_EXISTING_LINKS = -4L;
    public static final long DEFAULT_ID__TOPIC__SHOW_ALL_EXISTING_TERMS = -5L;
    public static final long DEFAULT_ID__TOPIC__SHOW_ALL_LINKS_WITHOUT_PARENT = -6L;
    public static final long DEFAULT_ID__TOPIC__SHOW_ALL_TERMS_WITHOUT_PARENT = -7L;
    
//    public static final String SIGN__DOUBLE_POINT = ":"; // NOI18N
    public static final String SIGN__EMPTY = ""; // NOI18N
//    public static final String SIGN__SPACE = " "; // NOI18N
//    public static final String SIGN__STAR = "*"; // NOI18N

}

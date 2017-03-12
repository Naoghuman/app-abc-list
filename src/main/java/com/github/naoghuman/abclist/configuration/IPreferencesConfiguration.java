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
public interface IPreferencesConfiguration {
    
    public static final String PREF__TESTDATA__IS_SELECTED_DELETE_DATABASE = "PREF__TESTDATA__IS_SELECTED_DELETE_DATABASE"; // NOI18N
    public static final Boolean PREF__TESTDATA__IS_SELECTED_DELETE_DATABASE__DEFAULT_VALUE = Boolean.FALSE;
    
    public static final String PREF__TESTDATA__QUANTITY_ENTITIES__EXERCISE = "PREF__TESTDATA__QUANTITY_ENTITIES__EXERCISE"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_ENTITIES__EXERCISE_DEFAULT_VALUE = 100;
    public static final String PREF__TESTDATA__QUANTITY_ENTITIES__EXERCISE_TERM = "PREF__TESTDATA__QUANTITY_ENTITIES__EXERCISE_TERM"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_ENTITIES__EXERCISE_TERM_DEFAULT_VALUE = 100;
    public static final String PREF__TESTDATA__QUANTITY_ENTITIES__LINK = "PREF__TESTDATA__QUANTITY_ENTITIES__LINK"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_ENTITIES__LINK_DEFAULT_VALUE = 100;
    public static final String PREF__TESTDATA__QUANTITY_ENTITIES__LINK_MAPPING = "PREF__TESTDATA__QUANTITY_ENTITIES__LINK_MAPPING"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_ENTITIES__LINK_MAPPING_DEFAULT_VALUE = 100;
    public static final String PREF__TESTDATA__QUANTITY_ENTITIES__TERM = "PREF__TESTDATA__QUANTITY_ENTITIES__TERM"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_ENTITIES__TERM_DEFAULT_VALUE = 100;
    public static final String PREF__TESTDATA__QUANTITY_ENTITIES__TOPIC = "PREF__TESTDATA__QUANTITY_ENTITIES__TOPIC"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_ENTITIES__TOPIC_DEFAULT_VALUE = 100;
    
    public static final String PREF__TESTDATA__QUANTITY_TIMEPERIOD__EXERCISE = "PREF__TESTDATA__QUANTITY_TIMEPERIOD__EXERCISE"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_TIMEPERIOD__EXERCISE_DEFAULT_VALUE = 1;
    public static final String PREF__TESTDATA__QUANTITY_TIMEPERIOD__LINK = "PREF__TESTDATA__QUANTITY_TIMEPERIOD__LINK"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_TIMEPERIOD__LINK_DEFAULT_VALUE = 1;
    public static final String PREF__TESTDATA__QUANTITY_TIMEPERIOD__LINK_MAPPING = "PREF__TESTDATA__QUANTITY_TIMEPERIOD__LINK_MAPPING"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_TIMEPERIOD__LINK_MAPPING_DEFAULT_VALUE = 1;
    public static final String PREF__TESTDATA__QUANTITY_TIMEPERIOD__TERM = "PREF__TESTDATA__QUANTITY_TIMEPERIOD__TERM"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_TIMEPERIOD__TERM_DEFAULT_VALUE = 1;
    public static final String PREF__TESTDATA__QUANTITY_TIMEPERIOD__TOPIC = "PREF__TESTDATA__QUANTITY_TIMEPERIOD__TOPIC"; // NOI18N
    public static final Integer PREF__TESTDATA__QUANTITY_TIMEPERIOD__TOPIC_DEFAULT_VALUE = 1;
    
}

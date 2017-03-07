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

import javafx.util.Duration;

/**
 *
 * @author Naoghuman
 */
public interface IPropertiesConfiguration {
    
    public static final int NO_ENTITY = 0;
    
    public static final String STRING_DEFAULT_REGEX = "%s"; // NOI18N
    
    public static final String INFO__FOUNDED_TERMS = "converter.info.founded.terms"; // NOI18N
    
    public static final Duration LITTLE_DELAY__DURATION_125 = Duration.millis(125.0d);
    
    public static final String KEY__CONVERTER__RESOURCE_BUNDLE = "/com/github/naoghuman/abclist/i18n/converter.properties"; // NOI18N
    public static final String KEY__TESTDATA__RESOURCE_BUNDLE = "/com/github/naoghuman/abclist/i18n/testdataapplication.properties"; // NOI18N
    
    public static final String KEY__TESTDATA_APPLICATION__BORDER_SIGN = "application.testdata.border.sign"; // NOI18N
    public static final String KEY__TESTDATA_APPLICATION__DATABASE = "application.database"; // NOI18N
    public static final String KEY__TESTDATA_APPLICATION__MESSAGE_START = "application.testdata.message.start"; // NOI18N
    public static final String KEY__TESTDATA_APPLICATION__MESSAGE_STOP = "application.testdata.message.stop"; // NOI18N
    public static final String KEY__TESTDATA_APPLICATION__TITLE = "application.testdata.title"; // NOI18N
    public static final String KEY__TESTDATA_APPLICATION__VERSION = "application.testdata.version"; // NOI18N
    
}

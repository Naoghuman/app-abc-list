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
package com.github.naoghuman.abclist.i18n;

import com.github.naoghuman.abclist.configuration.IPropertiesConfiguration;
import com.github.naoghuman.lib.properties.api.PropertiesFacade;

/**
 *
 * @author Naoghuman
 */
public final class Properties implements IPropertiesConfiguration {
    
    public static String getPropertyForConverter(String propertyKey) {
        return PropertiesFacade.getDefault().getProperty(KEY__CONVERTER__RESOURCE_BUNDLE, propertyKey);
    }
    
}

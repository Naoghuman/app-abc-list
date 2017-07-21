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
package com.github.naoghuman.converter;

import com.github.naoghuman.abclist.configuration.IPropertiesConfiguration;
import com.github.naoghuman.abclist.i18n.Properties;

/**
 *
 * @author Naoghuman
 */
public class LinkPresentationConverter implements IPresentationConverter, IPropertiesConfiguration {
    
    public static String getI18nMsgFoundedEntities(int countFoundedEntities) {
        final String text = Properties.getPropertyForConverter(INFO__FOUNDED_LINKS);
        final String textFoundedEntities = text.replaceFirst(STRING_DEFAULT_REGEX, String.valueOf(countFoundedEntities));
        
        return textFoundedEntities;
    }
    
    @Override
    public void convert(Object entity) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet."); // TODO
    }

    @Override
    public String getPresentation() {
        throw new UnsupportedOperationException("Not supported yet."); // TODO
    }

    @Override
    public String getTooltip() {
        throw new UnsupportedOperationException("Not supported yet.");  // TODO
    }
    
}

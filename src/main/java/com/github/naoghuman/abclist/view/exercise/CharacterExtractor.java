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
package com.github.naoghuman.abclist.view.exercise;

import com.github.naoghuman.abclist.view.application.converter.DateConverter;
import java.util.HashMap;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 *
 * @author Naoghuman
 */
public final class CharacterExtractor {
    
    private static final char UNDERLINE = '_'; // NOI18N
    
    private static final Optional<CharacterExtractor> instance = Optional.of(new CharacterExtractor());

    public static final CharacterExtractor getDefault() {
        return instance.get();
    }
    
    private final ObservableMap<Character, Character> toMapCharacters = FXCollections.observableHashMap();
    
    private CharacterExtractor() {
        this.initialize();
    }
    
    private void initialize() {
        toMapCharacters.put('ä', 'a'); // NOI18N
        toMapCharacters.put('ö', 'o'); // NOI18N
        toMapCharacters.put('ü', 'u'); // NOI18N
        toMapCharacters.put('ß', 's'); // NOI18N
    }
   
    public char computeFirstChar(String term) throws UnsupportedOperationException {
        if (term.isEmpty()) {
            throw new UnsupportedOperationException("term can't be empty"); // NOI18N
        }
        
        final int size = term.length();
        term = term.toLowerCase();
        char firstChar = UNDERLINE;
        for (int index = 0; index < size; index++) {
            char c = term.charAt(index);
            if (c >= 97 && c <= 122) { // a - z 
                firstChar = c;
                break;
            }
            
            if (toMapCharacters.containsKey(c)) {
                firstChar = toMapCharacters.get(c);
                break;
            }
        }
        
        if (firstChar == UNDERLINE) {
            throw new UnsupportedOperationException("Can't found valid [firstChar]! Must be a [a-z] in the given [Term]"); // NOI18N
        }
        
        return firstChar;
    }
    
}

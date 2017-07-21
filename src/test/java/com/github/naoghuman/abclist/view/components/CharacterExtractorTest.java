/*
 * Copyright (C) 2017 PRo
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
package com.github.naoghuman.abclist.view.components;

import com.github.naoghuman.abclist.view.components.signflowpane.CharacterExtractor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author PRo
 */
public class CharacterExtractorTest {
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetDefault() {
        assertNotNull(CharacterExtractor.getDefault());
    }

    @Test
    public void testComputeFirstChar() {
        // valid
        char c = CharacterExtractor.getDefault().computeFirstChar("a");
        assertEquals('a', c);
        
        c = CharacterExtractor.getDefault().computeFirstChar("z");
        assertEquals('z', c);
        
        // Umlaute
        c = CharacterExtractor.getDefault().computeFirstChar("ä");
        assertEquals('a', c);
        
        c = CharacterExtractor.getDefault().computeFirstChar("ö");
        assertEquals('o', c);
        
        c = CharacterExtractor.getDefault().computeFirstChar("ß");
        assertEquals('s', c);
        
        c = CharacterExtractor.getDefault().computeFirstChar("ü");
        assertEquals('u', c);
        
        c = CharacterExtractor.getDefault().computeFirstChar("Ä");
        assertEquals('a', c);
        
        c = CharacterExtractor.getDefault().computeFirstChar("Ö");
        assertEquals('o', c);
        
        c = CharacterExtractor.getDefault().computeFirstChar("Ü");
        assertEquals('u', c);
        
        // ignore
        c = CharacterExtractor.getDefault().computeFirstChar("1a");
        assertEquals('a', c);
        
        c = CharacterExtractor.getDefault().computeFirstChar("'cd");
        assertEquals('c', c);
        
        c = CharacterExtractor.getDefault().computeFirstChar(" d");
        assertEquals('d', c);
    }
    
}

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
package com.github.naoghuman.abclist.model;

import com.github.naoghuman.abclist.configuration.IDefaultConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Naoghuman
 */
public class LinkTest {
    
    public LinkTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetId() {
        Link link = new Link();
        assertEquals(IDefaultConfiguration.DEFAULT_ID, link.getId());
    }

    @Test
    public void testSetId() {
        Link link = new Link();
        link.setId(123l);
        assertEquals(123l, link.getId());
    }

    @Test
    public void testIdProperty() {
        Link link = new Link();
        assertEquals(IDefaultConfiguration.DEFAULT_ID, link.idProperty().get());
        
        link.setId(123l);
        assertEquals(123l, link.idProperty().get());
    }

    @Test
    public void testSetGenerationTime() {
        Link link = new Link();
        link.setGenerationTime(1234l);
        assertEquals(1234l, link.getGenerationTime());
    }

    @Test
    public void testGenerationTimeProperty() {
        Link link = new Link();
        link.setGenerationTime(1234l);
        assertEquals(1234l, link.generationTimeProperty().get());
    }

    @Test
    public void testGetAlias() {
        Link link = new Link();
        assertEquals(IDefaultConfiguration.SIGN__EMPTY, link.getAlias());
    }

    @Test
    public void testSetAlias() {
        Link link = new Link();
        link.setAlias("hello"); // NOI18N
        
        assertEquals("hello", link.getAlias()); // NOI18N
    }

    @Test
    public void testAliasProperty() {
        Link link = new Link();
        link.setAlias("hello"); // NOI18N
        
        assertEquals("hello", link.aliasProperty().get()); // NOI18N
    }

    @Test
    public void testGetDescription() {
        Link link = new Link();
        assertEquals(IDefaultConfiguration.SIGN__EMPTY, link.getDescription());
    }

    @Test
    public void testSetDescription() {
        Link link = new Link();
        link.setDescription("what"); // NOI18N
        
        assertEquals("what", link.getDescription()); // NOI18N
    }

    @Test
    public void testDescriptionProperty() {
        Link link = new Link();
        link.setDescription("what"); // NOI18N
        
        assertEquals("what", link.descriptionProperty().get()); // NOI18N
    }

    @Test
    public void testGetUrl() {
        Link link = new Link();
        assertEquals(IDefaultConfiguration.SIGN__EMPTY, link.getUrl());
    }

    @Test
    public void testSetUrl() {
        Link link = new Link();
        link.setUrl("www.javafx.com"); // NOI18N
        
        assertEquals("www.javafx.com", link.getUrl()); // NOI18N
    }

    @Test
    public void testUrlProperty() {
        Link link = new Link();
        link.setUrl("www.javafx.com"); // NOI18N
        
        assertEquals("www.javafx.com", link.urlProperty().get()); // NOI18N
    }

    @Test
    public void testGetImage() {
        Link link = new Link();
        assertEquals(IDefaultConfiguration.SIGN__EMPTY, link.getImage());
    }

    @Test
    public void testSetImage() {
        Link link = new Link();
        link.setImage("image.png"); // NOI18N
        
        assertEquals("image.png", link.getImage()); // NOI18N
    }

    @Test
    public void testImageProperty() {
        Link link = new Link();
        link.setImage("image.png"); // NOI18N
        
        assertEquals("image.png", link.imageProperty().get()); // NOI18N
    }

    @Test
    public void testGetFavorite() {
        Link link = new Link();
        assertEquals(Boolean.FALSE, link.getFavorite());
    }

    @Test
    public void testSetFavorite() {
        Link link = new Link();
        link.setFavorite(Boolean.TRUE);
        
        assertEquals(Boolean.TRUE, link.getFavorite());
    }

    @Test
    public void testFavoriteProperty() {
        Link link = new Link();
        link.setFavorite(Boolean.TRUE);
        
        assertEquals(Boolean.TRUE, link.favoriteProperty().get());
    }

    @Test
    public void testIsMarkAsChanged() {
        Link link = new Link();
        assertEquals(Boolean.FALSE, link.isMarkAsChanged());
    }

    @Test
    public void testMarkAsChangedProperty() {
        Link link = new Link();
        link.setMarkAsChanged(Boolean.TRUE);
        
        assertEquals(Boolean.TRUE, link.isMarkAsChanged());
    }

    @Test
    public void testSetMarkAsChanged() {
        Link link = new Link();
        link.setMarkAsChanged(Boolean.TRUE);
        
        assertEquals(Boolean.TRUE, link.markAsChangedProperty().get());
    }

    @Test
    public void testCompareTo() {
        List<Link> links = new ArrayList<>();
        links.add(new Link(1l));
        links.add(new Link(3l));
        links.add(new Link(2l));
        
        Collections.sort(links);
        
        
        assertEquals(1l, links.get(0).getId());
        assertEquals(2l, links.get(1).getId());
        assertEquals(3l, links.get(2).getId());
    }
    
}

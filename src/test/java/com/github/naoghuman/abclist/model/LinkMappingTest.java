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
 * @author PRo
 */
public class LinkMappingTest {
    
    public LinkMappingTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetId() {
        LinkMapping linkMapping = new LinkMapping();
        assertEquals(IDefaultConfiguration.DEFAULT_ID, linkMapping.getId());
    }

    @Test
    public void testSetId() {
        LinkMapping linkMapping = new LinkMapping();
        linkMapping.setId(123l);
        assertEquals(123l, linkMapping.getId());
    }

    @Test
    public void testIdProperty() {
        LinkMapping linkMapping = new LinkMapping();
        assertEquals(IDefaultConfiguration.DEFAULT_ID, linkMapping.idProperty().get());
        
        linkMapping.setId(123l);
        assertEquals(123l, linkMapping.idProperty().get());
    }

    @Test
    public void testGetParentId() {
        LinkMapping linkMapping = new LinkMapping();
        assertEquals(IDefaultConfiguration.DEFAULT_ID, linkMapping.getParentId());
    }

    @Test
    public void testSetParentId() {
        LinkMapping linkMapping = new LinkMapping();
        linkMapping.setParentId(123l);
        assertEquals(123l, linkMapping.getParentId());
    }

    @Test
    public void testParentIdProperty() {
        LinkMapping linkMapping = new LinkMapping();
        assertEquals(IDefaultConfiguration.DEFAULT_ID, linkMapping.parentIdProperty().get());
        
        linkMapping.setParentId(123l);
        assertEquals(123l, linkMapping.parentIdProperty().get());
    }

    @Test
    public void testGetParentType() {
        LinkMapping linkMapping = new LinkMapping();
        assertEquals(LinkMappingType.NOT_DEFINED, linkMapping.getParentType());
    }

    @Test
    public void testSetParentType() {
        LinkMapping linkMapping = new LinkMapping();
        linkMapping.setParentType(LinkMappingType.LINK);
        assertEquals(LinkMappingType.LINK, linkMapping.getParentType());
    }

    @Test
    public void testParentTypeProperty() {
        LinkMapping linkMapping = new LinkMapping();
        assertEquals(LinkMappingType.NOT_DEFINED, linkMapping.parentTypeProperty().get());
        
        linkMapping.setParentType(LinkMappingType.LINK);
        assertEquals(LinkMappingType.LINK, linkMapping.parentTypeProperty().get());
    }

    @Test
    public void testGetChildId() {
        LinkMapping linkMapping = new LinkMapping();
        assertEquals(IDefaultConfiguration.DEFAULT_ID, linkMapping.getChildId());
    }

    @Test
    public void testSetChildId() {
        LinkMapping linkMapping = new LinkMapping();
        linkMapping.setChildId(123l);
        assertEquals(123l, linkMapping.getChildId());
    }

    @Test
    public void testChildIdProperty() {
        LinkMapping linkMapping = new LinkMapping();
        assertEquals(IDefaultConfiguration.DEFAULT_ID, linkMapping.childIdProperty().get());
        
        linkMapping.setChildId(123l);
        assertEquals(123l, linkMapping.childIdProperty().get());
    }

    @Test
    public void testGetChildType() {
        LinkMapping linkMapping = new LinkMapping();
        assertEquals(LinkMappingType.NOT_DEFINED, linkMapping.getChildType());
    }

    @Test
    public void testSetChildType() {
        LinkMapping linkMapping = new LinkMapping();
        linkMapping.setChildType(LinkMappingType.LINK);
        assertEquals(LinkMappingType.LINK, linkMapping.getChildType());
    }

    @Test
    public void testChildTypeProperty() {
        LinkMapping linkMapping = new LinkMapping();
        assertEquals(LinkMappingType.NOT_DEFINED, linkMapping.childTypeProperty().get());
        
        linkMapping.setChildType(LinkMappingType.LINK);
        assertEquals(LinkMappingType.LINK, linkMapping.childTypeProperty().get());
    }

    @Test
    public void testCompareTo() {
        List<LinkMapping> linkMappings = new ArrayList<>();
        linkMappings.add(new LinkMapping(1l));
        linkMappings.add(new LinkMapping(3l));
        linkMappings.add(new LinkMapping(2l));
        
        Collections.sort(linkMappings);
        
        assertEquals(1l, linkMappings.get(0).getId());
        assertEquals(2l, linkMappings.get(1).getId());
        assertEquals(3l, linkMappings.get(2).getId());
    }

    @Test
    public void testEquals() {
        LinkMapping linkMapping1 = new LinkMapping();
        linkMapping1.setId(1l);
        linkMapping1.setParentId(2l);
        linkMapping1.setParentType(LinkMappingType.EXERCISE);
        linkMapping1.setChildId(3l);
        linkMapping1.setChildType(LinkMappingType.LINK);
        
        LinkMapping linkMapping2 = new LinkMapping();
        linkMapping2.setId(1l);
        linkMapping2.setParentId(2l);
        linkMapping2.setParentType(LinkMappingType.EXERCISE);
        linkMapping2.setChildId(3l);
        linkMapping2.setChildType(LinkMappingType.LINK);
        
        assertEquals(linkMapping1, linkMapping2);
    }
    
}

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
package com.github.naoghuman.testdata.abclist.entity;

import java.util.List;
import javafx.collections.FXCollections;

/**
 *
 * @author Naoghuman
 */
public class EntityHelper {
    
    private static final List<Integer> QUANTITY_ENTITIES = FXCollections.observableArrayList();
    private static final List<Integer> QUANTITY_TIMEPERIOD = FXCollections.observableArrayList();
    
    private static EntityHelper instance = null;
    
    public static EntityHelper getDefault() {
        if (instance == null) {
            instance = new EntityHelper();
        }
        
        return instance;
    }
    
    private EntityHelper() { 
        this.initialize();
    }
    
    private void initialize() {
        this.initializeQuantityEntities();
        this.initializeQuantityTimePeriod();
    }

    private void initializeQuantityEntities() {
        QUANTITY_ENTITIES.add(100);
        QUANTITY_ENTITIES.add(250);
        QUANTITY_ENTITIES.add(500);
        QUANTITY_ENTITIES.add(1000);
        QUANTITY_ENTITIES.add(2500);
        QUANTITY_ENTITIES.add(5000);
        QUANTITY_ENTITIES.add(10000);
        QUANTITY_ENTITIES.add(12500);
        QUANTITY_ENTITIES.add(15000);
        QUANTITY_ENTITIES.add(20000);
        QUANTITY_ENTITIES.add(25000);
        QUANTITY_ENTITIES.add(30000);
        QUANTITY_ENTITIES.add(35000);
        QUANTITY_ENTITIES.add(40000);
        QUANTITY_ENTITIES.add(50000);
        QUANTITY_ENTITIES.add(75000);
        QUANTITY_ENTITIES.add(100000);
    }

    private void initializeQuantityTimePeriod() {
        QUANTITY_TIMEPERIOD.add(1);
        QUANTITY_TIMEPERIOD.add(2);
        QUANTITY_TIMEPERIOD.add(3);
        QUANTITY_TIMEPERIOD.add(4);
        QUANTITY_TIMEPERIOD.add(5);
        QUANTITY_TIMEPERIOD.add(10);
        QUANTITY_TIMEPERIOD.add(15);
        QUANTITY_TIMEPERIOD.add(20);
        QUANTITY_TIMEPERIOD.add(25);
        QUANTITY_TIMEPERIOD.add(50);
        QUANTITY_TIMEPERIOD.add(75);
    }
    
    public List<Integer> getQuantityEntities() {
        return QUANTITY_ENTITIES;
    }
    
    public List<Integer> getQuantityTimePeriods() {
        return QUANTITY_TIMEPERIOD;
    }
    
}

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
package com.github.naoghuman.testdata.abclist;

import java.util.Optional;
import javafx.scene.Parent;

/**
 *
 * @author Naoghuman
 */
public final class TestdataFacade {
    
    private static final Optional<TestdataFacade> INSTANCE = Optional.of(new TestdataFacade());

    public static final TestdataFacade getDefault() {
        return INSTANCE.get();
    }
    
    private TestdataPresenter testdataPresenter = null;
    private TestdataView view = null;
    
    private TestdataFacade() {
        this.initialize();
    }
    
    private void initialize() {
        view = new TestdataView();
        testdataPresenter = view.getRealPresenter();
    }
    
    public void shutdown() throws InterruptedException {
        testdataPresenter.shutdown();
    }
    
    public Parent getView() {
        return view.getView();
    }
    
}

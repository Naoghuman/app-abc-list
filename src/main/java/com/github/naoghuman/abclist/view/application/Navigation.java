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
package com.github.naoghuman.abclist.view.application;

import com.github.naoghuman.abclist.view.application.ENavigationType;

/**
 *
 * @author Naoghuman
 */
public final class Navigation {

    private final ENavigationType navigationType;

    private final long entityId;

    public Navigation(ENavigationType navigationType, long entityId) {
        this.navigationType = navigationType;
        this.entityId = entityId;
    }

    public long getEntityId() {
        return entityId;
    }

    public ENavigationType getNavigationType() {
        return navigationType;
    }

}

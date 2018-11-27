/*
 * Copyright (C) 2018 Naoghuman's dream
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
package com.github.naoghuman.abclist.sql;

import com.github.naoghuman.lib.logger.core.LoggerFacade;
import org.apache.commons.lang3.time.StopWatch;

/**
 *
 * @since  0.5.0-PRERELEASE
 * @author Naoghuman
 */
class SqlPerformance {
    
    public static SqlPerformance create() {
        return new SqlPerformance();
    }
    
    private final StopWatch stopWatch;
    
    private SqlPerformance() {
        stopWatch = new StopWatch();
    }
    
    /**
     * 
     * @since  0.5.0-PRERELEASE
     * @author Naoghuman
     */
    public void start() {
        stopWatch.start();
    }
    
    /**
     * 
     * @param  entities
     * @param  method 
     * @since  0.5.0-PRERELEASE
     * @author Naoghuman
     */
    public void stop(final int entities, final String method) {
        stopWatch.split();
        
        final StringBuilder sb = new StringBuilder();
        sb.append("  + Need "); // NOI18N
        sb.append(stopWatch.toSplitString());
        sb.append(" for ["); // NOI18N
        sb.append(entities);
        sb.append("] entities in method: "); // NOI18N
        sb.append(method);
        
        LoggerFacade.getDefault().debug(this.getClass(), sb.toString());
        
        stopWatch.stop();
        stopWatch.reset();
    }
    
}

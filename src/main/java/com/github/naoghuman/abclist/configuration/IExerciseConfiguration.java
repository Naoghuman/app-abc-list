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
package com.github.naoghuman.abclist.configuration;

/**
 *
 * @author Naoghuman
 */
public interface IExerciseConfiguration {
    
    public static final String ENTITY__TABLE_NAME__EXERCISE = "Exercise"; // NOI18N
    
    public static final String NAMED_QUERY__NAME__FIND_ALL_WITH_TOPIC_ID = "Exercise.findAllWithTopicId"; // NOI18N
    public static final String NAMED_QUERY__QUERY__FIND_ALL_WITH_TOPIC_ID = "SELECT e FROM Exercise e WHERE e.topicId == :topicId"; // NOI18N
    
    public static final String EXERCISE__COLUMN_NAME__CONSOLIDATED = "consolidated"; // NOI18N
    public static final String EXERCISE__COLUMN_NAME__CHOOSEN_TIME = "choosenTime"; // NOI18N
    public static final String EXERCISE__COLUMN_NAME__ID = "id"; // NOI18N
    public static final String EXERCISE__COLUMN_NAME__FINISHED_TIME = "finishedTime"; // NOI18N
    public static final String EXERCISE__COLUMN_NAME__GENERATION_TIME = "generationTime"; // NOI18N
    public static final String EXERCISE__COLUMN_NAME__TOPIC_ID = "topicId"; // NOI18N
    public static final String EXERCISE__COLUMN_NAME__READY = "ready"; // NOI18N
    
}

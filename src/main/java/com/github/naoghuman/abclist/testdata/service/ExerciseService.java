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
package com.github.naoghuman.abclist.testdata.service;

import com.github.naoghuman.abclist.model.Exercise;
import com.github.naoghuman.abclist.model.ModelProvider;
import com.github.naoghuman.abclist.model.Topic;
import com.github.naoghuman.abclist.sql.SqlProvider;
import com.github.naoghuman.abclist.testdata.TestdataPresenter;
import com.github.naoghuman.abclist.view.application.converter.IDateConverter;
import com.github.naoghuman.abclist.testdata.TestdataGenerator;
import com.github.naoghuman.abclist.testdata.testdataexercise.TestdataExercisePresenter;
import com.github.naoghuman.abclist.view.application.converter.DateConverter;
import com.github.naoghuman.abclist.view.exercise.ETime;
import com.github.naoghuman.lib.database.api.DatabaseFacade;
import com.github.naoghuman.lib.database.api.ICrudService;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import org.apache.commons.lang3.time.StopWatch;

/**
 *
 * @author Naoghuman
 */
public class ExerciseService extends Service<Void> {
    
    private final long now = System.currentTimeMillis();
    
    private final DoubleProperty entityProperty = new SimpleDoubleProperty(0.0d);
    
    private int saveMaxEntities = 0;
    private int timePeriod = 0;
    private long convertedTimePeriod = 0L;
    
    private TestdataExercisePresenter presenter = null;
    private String entityName = null;
    private String onStartMessage = null;
    
    public ExerciseService(String entityName) {
        this.entityName = entityName;
    }

    public void bind(TestdataExercisePresenter presenter) {
        this.presenter = presenter;
        
        saveMaxEntities = presenter.getSaveMaxEntities();
        timePeriod = presenter.getTimePeriod();
        
        String startTime = DateConverter.getDefault().convertLongToDateTime(now, IDateConverter.PATTERN__DATE);
        int year = Integer.parseInt(startTime.substring(6)) - timePeriod;
        startTime = startTime.substring(0, 6) + year;
        
        final long convertedStartTime = DateConverter.getDefault().convertDateTimeToLong(startTime, IDateConverter.PATTERN__DATE);
        convertedTimePeriod = now - convertedStartTime;
        
        entityProperty.unbind();
        entityProperty.setValue(0);
        entityProperty.bind(super.progressProperty());
        
        this.presenter.getProgressBarPercentInformation().textProperty().bind(
                Bindings.createStringBinding(() -> {
                    int process = (int) (entityProperty.getValue() * 100.0d);
                    if (process <= 0) {
                        process = 0;
                    } else {
                        ++process;
                    }

                    return process + "%"; // NOI18N
                },
                entityProperty));
        
        this.presenter.progressPropertyFromEntityDream().unbind();
        this.presenter.progressPropertyFromEntityDream().bind(super.progressProperty());
    }
    
    private long createGenerationTime() {
        final long generationTime = now - DateConverter.getDefault().getLongInPeriodFromNowTo(convertedTimePeriod);
        
        return generationTime;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            {
                updateProgress(0, saveMaxEntities);
            }
            
            @Override
            protected Void call() throws Exception {
                LoggerFacade.getDefault().deactivate(Boolean.TRUE);
                
                final StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                
                final ObservableList<Topic> topics = SqlProvider.getDefault().findAllTopics();
                final int removeMaxTopics = (int) (Double.parseDouble(String.valueOf(topics.size())) * 0.001d);
                for (int counter = removeMaxTopics; counter > 0; counter--) {
                    topics.remove(TestdataGenerator.RANDOM.nextInt(topics.size()));
                }
                final int sizeTopics = topics.size();
                
                final ICrudService crudService = DatabaseFacade.getDefault().getCrudService(entityName);
                final ETime[] times = ETime.values();
                final int sizeTimes = times.length;
                long id = -1_000_000_000L + DatabaseFacade.getDefault().getCrudService().count(entityName);
                for (int index = 0; index < saveMaxEntities; index++) {
                    final Exercise exercise = ModelProvider.getDefault().getExercise();
                    exercise.setChoosenTime((times[TestdataGenerator.RANDOM.nextInt(sizeTimes)]).toString());
                    exercise.setConsolidated(false); // TODO later ?
                    exercise.setFinishedTime(ExerciseService.this.createGenerationTime());
                    exercise.setGenerationTime(ExerciseService.this.createGenerationTime());
                    if (exercise.getGenerationTime() > exercise.getFinishedTime()) {
                        final long generationTime = exercise.getGenerationTime();
                        final long finishedTime = exercise.getFinishedTime();
                        exercise.setGenerationTime(finishedTime);
                        exercise.setFinishedTime(generationTime);
                    }
                    exercise.setId(id++);
                    exercise.setReady(TestdataGenerator.RANDOM.nextDouble() > 0.001d); // TODO if ready then add terms
                    
                    final long topicId = topics.get(TestdataGenerator.RANDOM.nextInt(sizeTopics)).getId();
                    exercise.setTopicId(topicId);
                    
                    crudService.create(exercise);
                    updateProgress(index, saveMaxEntities);
                }
                
                LoggerFacade.getDefault().deactivate(Boolean.FALSE);
                stopWatch.split();
                LoggerFacade.getDefault().debug(this.getClass(), "  + " + stopWatch.toSplitString() + " for " + saveMaxEntities + " Exercises."); // NOI18N
		stopWatch.stop();
                
                return null;
            }
        };
    }

    public void setOnStart(String onStartMessage) {
        this.onStartMessage = onStartMessage;
    }
    
    public void setOnSuccededAfterService(TestdataPresenter testdataPresenter, String onSucceededMessage) {
        super.setOnSucceeded((WorkerStateEvent t) -> {
            LoggerFacade.getDefault().debug(this.getClass(), onSucceededMessage);
            
            presenter.setProgressBarInformation(onSucceededMessage);
            
            if (!presenter.getProgressBarPercentInformation().getText().equals("100%")) { // NOI18N
                presenter.getProgressBarPercentInformation().textProperty().unbind();
                presenter.getProgressBarPercentInformation().setText("100%"); // NOI18N
            }
            
            if (testdataPresenter != null) {
                testdataPresenter.cleanUpAfterServices();
            }
        });
    }

    @Override
    public void start() {
        final SequentialTransition sequentialTransition = new SequentialTransition();
        
        final PauseTransition ptProgressBarInformation = new PauseTransition();
        ptProgressBarInformation.setDuration(Duration.millis(250.0d));
        ptProgressBarInformation.setOnFinished((ActionEvent event) -> {
            presenter.setProgressBarInformation(onStartMessage);
        });
        sequentialTransition.getChildren().add(ptProgressBarInformation);
        
        final PauseTransition ptStart = new PauseTransition();
        ptStart.setDuration(Duration.millis(1000.0d));
        ptStart.setOnFinished((ActionEvent event) -> {
            super.start();
        });
        sequentialTransition.getChildren().add(ptStart);
        
        sequentialTransition.playFromStart();
    }
    
}

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
package com.github.naoghuman.testdata.abclist.service;

import com.github.naoghuman.abclist.model.ModelProvider;
import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.testdata.abclist.TestdataPresenter;
import com.github.naoghuman.testdata.abclist.TestdataGenerator;
import com.github.naoghuman.testdata.abclist.view.testdataterm.TestdataTermPresenter;
import com.github.naoghuman.converter.DateConverter;
import com.github.naoghuman.lib.database.core.DatabaseFacade;
import com.github.naoghuman.lib.database.core.CrudService;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
public class TermService extends Service<Void> {
    
    private final long now = System.currentTimeMillis();
    
    private final DoubleProperty entityProperty = new SimpleDoubleProperty(0.0d);
    
    private int saveMaxEntities = 0;
    private int timePeriod = 0;
    private long convertedTimePeriod = 0L;
    
    private TestdataTermPresenter presenter = null;
    private String entityName = null;
    private String onStartMessage = null;
    
    public TermService(String entityName) {
        this.entityName = entityName;
    }

    public void bind(TestdataTermPresenter presenter) {
        this.presenter = presenter;
        
        saveMaxEntities = presenter.getSaveMaxEntities();
        timePeriod = presenter.getTimePeriod();
        
        String startTime = DateConverter.getDefault().convertLongToDateTime(now, DateConverter.PATTERN__DATE);
        int year = Integer.parseInt(startTime.substring(6)) - timePeriod;
        startTime = startTime.substring(0, 6) + year;
        
        final long convertedStartTime = DateConverter.getDefault().convertDateTimeToLong(startTime, DateConverter.PATTERN__DATE);
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
                
                final CrudService crudService = DatabaseFacade.getDefault().getCrudService(entityName);
                long id = -1_000_000_000L + DatabaseFacade.getDefault().getCrudService().count(entityName);
                for (int index = 0; index < saveMaxEntities; index++) {
                    final Term term = ModelProvider.getDefault().getTerm(TestdataGenerator.getDefault().getUniqueTitles(index));
                    term.setId(id++);
                    term.setDescription(TestdataGenerator.getDefault().getDescription());
                    term.setGenerationTime(TermService.this.createGenerationTime());
                    
                    crudService.create(term);
                    updateProgress(index, saveMaxEntities);
                }
                
                LoggerFacade.getDefault().deactivate(Boolean.FALSE);
                stopWatch.split();
                LoggerFacade.getDefault().debug(this.getClass(), "  + " + stopWatch.toSplitString() + " for " + saveMaxEntities + " Terms."); // NOI18N
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

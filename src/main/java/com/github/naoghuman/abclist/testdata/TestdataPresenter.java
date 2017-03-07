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
package com.github.naoghuman.abclist.testdata;

import com.airhacks.afterburner.views.FXMLView;
import com.github.naoghuman.abclist.configuration.IPreferencesConfiguration;
import com.github.naoghuman.abclist.configuration.IPropertiesConfiguration;
import static com.github.naoghuman.abclist.configuration.IPropertiesConfiguration.KEY__TESTDATA_APPLICATION__DATABASE;
import com.github.naoghuman.abclist.i18n.Properties;
import com.github.naoghuman.abclist.model.Exercise;
import com.github.naoghuman.abclist.model.ExerciseTerm;
import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.abclist.model.Topic;
import com.github.naoghuman.abclist.testdata.testdatatopic.TestdataTopicPresenter;
import com.github.naoghuman.abclist.testdata.testdatatopic.TestdataTopicView;
import com.github.naoghuman.abclist.testdata.listview.CheckBoxListCell;
import com.github.naoghuman.abclist.testdata.listview.CheckBoxListCellModel;
import com.github.naoghuman.abclist.testdata.service.ExerciseService;
import com.github.naoghuman.abclist.testdata.service.ExerciseTermService;
import com.github.naoghuman.abclist.testdata.service.TopicService;
import com.github.naoghuman.abclist.testdata.service.SequentialThreadFactory;
import com.github.naoghuman.abclist.testdata.service.TermService;
import com.github.naoghuman.abclist.testdata.testdataexercise.TestdataExercisePresenter;
import com.github.naoghuman.abclist.testdata.testdataexercise.TestdataExerciseView;
import com.github.naoghuman.abclist.testdata.testdataexerciseterm.TestdataExerciseTermPresenter;
import com.github.naoghuman.abclist.testdata.testdataexerciseterm.TestdataExerciseTermView;
import com.github.naoghuman.abclist.testdata.testdataterm.TestdataTermPresenter;
import com.github.naoghuman.abclist.testdata.testdataterm.TestdataTermView;
import com.github.naoghuman.lib.database.api.DatabaseFacade;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.preferences.api.PreferencesFacade;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public class TestdataPresenter implements Initializable, IPreferencesConfiguration, IPropertiesConfiguration {
    
    private static final Map<String, FXMLView> ENTITIES = FXCollections.observableMap(new LinkedHashMap<String, FXMLView>());
    
    private final BooleanProperty disableProperty = new SimpleBooleanProperty(Boolean.FALSE);
    
    @FXML private Button bCreateTestdata;
    @FXML private AnchorPane apDialogLayer;
    @FXML private CheckBox cbDeleteDatabase;
    @FXML private CheckBox cbSelectAll;
    @FXML private ListView lvEntities;
    @FXML private ScrollPane spEntities;
    @FXML private TabPane tpEntities;
    @FXML private TabPane tpTestdata;
    @FXML private VBox vbEntities;
    
    private boolean triggerOnActionSelectAll = Boolean.TRUE;
    
    private ExecutorService sequentialExecutorService;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize TestDataPresenter");
    
        assert (bCreateTestdata != null)  : "fx:id=\"bCreateTestdata\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (apDialogLayer != null)    : "fx:id=\"apDialogLayer\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (cbDeleteDatabase != null) : "fx:id=\"cbDeleteDatabase\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (cbSelectAll != null)      : "fx:id=\"cbSelectAll\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (lvEntities != null)       : "fx:id=\"lvEntities\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (spEntities != null)       : "fx:id=\"spEntities\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (tpEntities != null)       : "fx:id=\"tpEntities\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (tpTestdata != null)       : "fx:id=\"tpTestdata\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
        assert (vbEntities != null)       : "fx:id=\"vbEntities\" was not injected: check your FXML file 'TestdataPresenter.fxml'."; // NOI18N
    
        this.initializeDeleteDatabase();
        this.initializeDesktopSplitPane();
        this.initializeDialogLayer();
        this.initializeEntities();
        this.initializeListView();
        this.initializeProcesses();
        
        this.onActionRefresh();
    }
    
    private void initializeDeleteDatabase() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize delete Database"); // NOI18N
    
        final Boolean isSelectedDeleteDatabase = PreferencesFacade.getDefault().getBoolean(
                PREF__TESTDATA__IS_SELECTED_DELETE_DATABASE,
                PREF__TESTDATA__IS_SELECTED_DELETE_DATABASE__DEFAULT_VALUE);
        cbDeleteDatabase.setSelected(isSelectedDeleteDatabase);
    }
    
    private void initializeDesktopSplitPane() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize desktop SplitPane"); // NOI18N
    
        SplitPane.setResizableWithParent(tpEntities, Boolean.FALSE);
    }
    
    private void initializeDialogLayer() {
        apDialogLayer.setVisible(Boolean.FALSE);
        apDialogLayer.setManaged(Boolean.FALSE);
    }
    
    private void initializeEntities() {
        LoggerFacade.getDefault().info(this.getClass(), "Register entities"); // NOI18N
        
        final TestdataTopicView topicView = new TestdataTopicView();
        topicView.getView().setId(Topic.class.getSimpleName());
        topicView.getRealPresenter().bind(disableProperty);
        ENTITIES.put(Topic.class.getSimpleName(), topicView);
        
        final TestdataExerciseView exerciseView = new TestdataExerciseView();
        exerciseView.getView().setId(Exercise.class.getSimpleName());
        exerciseView.getRealPresenter().bind(disableProperty);
        ENTITIES.put(Exercise.class.getSimpleName(), exerciseView);
        
        final TestdataTermView termView = new TestdataTermView();
        termView.getView().setId(Term.class.getSimpleName());
        termView.getRealPresenter().bind(disableProperty);
        ENTITIES.put(Term.class.getSimpleName(), termView);
        
        final TestdataExerciseTermView exerciseTermView = new TestdataExerciseTermView();
        exerciseTermView.getView().setId(ExerciseTerm.class.getSimpleName());
        exerciseTermView.getRealPresenter().bind(disableProperty);
        ENTITIES.put(ExerciseTerm.class.getSimpleName(), exerciseTermView);
    }
    
    private void initializeListView() {
        LoggerFacade.getDefault().debug(this.getClass(), "Initialize ListView in TestDataPresenter"); // NOI18N

        lvEntities.setCellFactory(CheckBoxListCell.forListView(CheckBoxListCellModel::selectedProperty, disableProperty));
    }
    
    private void initializeProcesses() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize Processes"); // NOI18N
        
        sequentialExecutorService = Executors.newFixedThreadPool(1, new SequentialThreadFactory());
    }
    
    public void cleanUpAfterServices() {
        LoggerFacade.getDefault().debug(this.getClass(), "Clean up after testdata generation"); // NOI18N

        DatabaseFacade.getDefault().shutdown();
        
        disableProperty.setValue(Boolean.FALSE);
        
        cbDeleteDatabase.disableProperty().unbind();
        cbSelectAll.disableProperty().unbind();
        bCreateTestdata.disableProperty().unbind();
        
        LoggerFacade.getDefault().debug(this.getClass(), "##### Ready with Testdata generation..."); // NOI18N
    }

    private void disableComponents() {
        LoggerFacade.getDefault().info(this.getClass(), "Disable the components"); // NOI18N
    
        cbDeleteDatabase.disableProperty().bind(disableProperty);
        cbSelectAll.disableProperty().bind(disableProperty);
        bCreateTestdata.disableProperty().bind(disableProperty);
        
        // CheckBoxes in ListView are binded through the initialization in ListView
        // XyPresenter (ComboBoxes) are binded through the initialization in Entities
        
        disableProperty.setValue(Boolean.TRUE);
    }
    
    private CheckBoxListCellModel getCheckBoxListCellModel(final String key) {
        LoggerFacade.getDefault().info(this.getClass(), "getCheckBoxListCellModel(...): " + key); // NOI18N
        
        final CheckBoxListCellModel model = new CheckBoxListCellModel();
        model.setName(key);
        model.setId(key);
        
        // Add or remove entity configuration
        model.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            // Selected
            if (newValue) {
                vbEntities.getChildren().add(ENTITIES.get(key).getView());
                return;
            }
            
            // Don't selected
            for (Node child : vbEntities.getChildren()) {
                if (!(child instanceof Parent)) {
                    continue;
                }

                final Parent entityToRemove = ENTITIES.get(key).getView();
                final Parent entity = (Parent) child;
                if (entity.getId().equals(entityToRemove.getId())) {
                    vbEntities.getChildren().remove(entityToRemove);
                    return;
                }
            }
        });
        
        // Button 'Create' testdata
        model.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            // Selected
            if (newValue) {
                bCreateTestdata.setDisable(Boolean.FALSE);
                return;
            }
            
            // Don't selected
            boolean disable = Boolean.TRUE;
            for (Object item : lvEntities.getItems()) {
                if (!(item instanceof CheckBoxListCellModel)) {
                    continue;
                }

                final CheckBoxListCellModel checkBoxListCellModel = (CheckBoxListCellModel) item;
                if (checkBoxListCellModel.isSelected()) {
                    disable = Boolean.FALSE;
                    break;
                }
            }

            bCreateTestdata.setDisable(disable);
        });
        
        // Check 'Select all' button
        model.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            final boolean isSelectAll = cbSelectAll.isSelected();
            
            // Selected
            if (newValue) {
                if (isSelectAll) {
                    return;
                }
                
                boolean allEntitiesCheckBoxesAreSelected = Boolean.TRUE;
                for (Object item : lvEntities.getItems()) {
                    if (!(item instanceof CheckBoxListCellModel)) {
                        continue;
                    }

                    final CheckBoxListCellModel checkBoxListCellModel = (CheckBoxListCellModel) item;
                    if (!checkBoxListCellModel.isSelected()) {
                        allEntitiesCheckBoxesAreSelected = Boolean.FALSE;
                        break;
                    }
                }
                
                if (allEntitiesCheckBoxesAreSelected) {
                    triggerOnActionSelectAll = Boolean.FALSE;
                    cbSelectAll.setSelected(Boolean.TRUE);
                    triggerOnActionSelectAll = Boolean.TRUE;
                }
                
                return;
            }
            
            // Don't selected
            if (!isSelectAll) {
                return;
            }
             
            boolean allEntitiesCheckBoxesAreSelected = Boolean.TRUE;
            for (Object item : lvEntities.getItems()) {
                if (!(item instanceof CheckBoxListCellModel)) {
                    continue;
                }

                final CheckBoxListCellModel checkBoxListCellModel = (CheckBoxListCellModel) item;
                if (!checkBoxListCellModel.isSelected()) {
                    allEntitiesCheckBoxesAreSelected = Boolean.FALSE;
                    break;
                }
            }

            if (!allEntitiesCheckBoxesAreSelected) {
                triggerOnActionSelectAll = Boolean.FALSE;
                cbSelectAll.setSelected(Boolean.FALSE);
                triggerOnActionSelectAll = Boolean.TRUE;
            }
        });
        
        return model;
    }
    
    public void onActionCreateTestdata() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action create Testdata"); // NOI18N

        final SequentialTransition sequentialTransition = new SequentialTransition();
        final PauseTransition ptDeactivateComponents = new PauseTransition();
        ptDeactivateComponents.setDuration(Duration.ZERO);
        ptDeactivateComponents.setOnFinished((ActionEvent event) -> {
            this.disableComponents();
        });
        sequentialTransition.getChildren().add(ptDeactivateComponents);
        
        if (cbDeleteDatabase.isSelected()) {
            final PauseTransition ptDropDatabase = new PauseTransition();
            ptDropDatabase.setDuration(Duration.millis(50.0d));
            ptDropDatabase.setOnFinished((ActionEvent event) -> {
                LoggerFacade.getDefault().debug(this.getClass(), "Drop database"); // NOI18N
                DatabaseFacade.getDefault().drop(Properties.getPropertyForTestdataApplication(KEY__TESTDATA_APPLICATION__DATABASE));
            });
            sequentialTransition.getChildren().add(ptDropDatabase);
        }
        
        final PauseTransition ptRegisterDatabase = new PauseTransition();
        ptRegisterDatabase.setDuration(Duration.millis(150.0d));
        ptRegisterDatabase.setOnFinished((ActionEvent event) -> {
            LoggerFacade.getDefault().debug(this.getClass(), "Register database"); // NOI18N
            DatabaseFacade.getDefault().register(Properties.getPropertyForTestdataApplication(KEY__TESTDATA_APPLICATION__DATABASE));
        });
        sequentialTransition.getChildren().add(ptRegisterDatabase);
        
        final PauseTransition ptCreateTestdata = new PauseTransition();
        ptCreateTestdata.setDuration(Duration.millis(150.0d));
        ptCreateTestdata.setOnFinished((ActionEvent event) -> {
            this.startTestdataGeneration();
        });
        sequentialTransition.getChildren().add(ptCreateTestdata);
        
        sequentialTransition.playFromStart();
    }
    
    public void onActionDeleteDatabase() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action delete Database"); // NOI18N
        
        PreferencesFacade.getDefault().putBoolean(PREF__TESTDATA__IS_SELECTED_DELETE_DATABASE, cbDeleteDatabase.isSelected());
    }
    
    private void onActionRefresh() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action Refresh"); // NOI18N
       
        Platform.runLater(() -> {
            this.onActionRefreshListView();
            this.onActionRefreshTabTestdata();
        });
    }
    
    private void onActionRefreshListView() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action refresh ListView"); // NOI18N
       
        lvEntities.getItems().clear();

        final List<CheckBoxListCellModel> models = FXCollections.observableArrayList();
        ENTITIES.keySet().stream()
                .forEach((key) -> {
                    models.add(this.getCheckBoxListCellModel(key));
                });
        
        lvEntities.getItems().addAll(models);
    }
    
    private void onActionRefreshTabTestdata() {
        LoggerFacade.getDefault().info(this.getClass(), "On action refresh tab TestData"); // NOI18N
        
        vbEntities.getChildren().clear();
    }
    
    public void onActionSelectAll(ActionEvent ae) {
        if (!triggerOnActionSelectAll) {
            return;
        }
        
        LoggerFacade.getDefault().info(this.getClass(), "On action select all"); // NOI18N
        
        if (!(ae.getSource() instanceof CheckBox)) {
            return;
        }
        
        final CheckBox checkBox = (CheckBox) ae.getSource();
        final Boolean isSelected = checkBox.isSelected();
        lvEntities.getItems().stream()
                .filter((item) -> (item instanceof CheckBoxListCellModel))
                .forEach((item) -> {
                    final CheckBoxListCellModel model = (CheckBoxListCellModel) item;
                    model.setSelected(isSelected);
                });
    }
    
    public void shutdown() throws InterruptedException {
        sequentialExecutorService.shutdown();
        sequentialExecutorService.awaitTermination(2, TimeUnit.SECONDS);
    }
    
    public void startTestdataGeneration() {
        LoggerFacade.getDefault().debug(this.getClass(), "##### Start with Testdata generation..."); // NOI18N
        
        final List<String> activeEntities = FXCollections.observableArrayList();
        lvEntities.getItems().stream()
                .filter((item) -> (item instanceof CheckBoxListCellModel))
                .forEach((item) -> {
                    final CheckBoxListCellModel model = (CheckBoxListCellModel) item;
                    if (model.isSelected()) {
                        activeEntities.add(model.getId());
                    }
                });
        
        /**
         * The last service need to do:
         *  - activate all components again for the next round,
         *  - shutdown the database
         *  - unbind components
         */
        final String lastActiveService = activeEntities.get(activeEntities.size() - 1);
        activeEntities.stream()
                .forEach((entityName) -> {
                    this.configureServiceForEntityTopic(entityName, lastActiveService);
                    this.configureServiceForEntityExercise(entityName, lastActiveService);
                    this.configureServiceForEntityTerm(entityName, lastActiveService);
                    this.configureServiceForEntityExerciseTerm(entityName, lastActiveService);
                });
    }

    private void configureServiceForEntityExercise(String entityName, String lastActiveService) {
        if (!entityName.equals(Exercise.class.getSimpleName())) {
            return;
        }
        
        final ExerciseService service = new ExerciseService(Exercise.class.getName());
        final TestdataExercisePresenter presenter = (TestdataExercisePresenter) ENTITIES.get(
                Exercise.class.getSimpleName()).getPresenter();
        service.bind(presenter);
        service.setExecutor(sequentialExecutorService);
        service.setOnStart("Start with testdata generation from entity Exercise..."); // NOI18N
        service.setOnSuccededAfterService(
                this.getTestdataPresenter(entityName, lastActiveService),
                "Ready with testdata generation from entity Exercise..."); // NOI18N
        
        service.start();
    }

    private void configureServiceForEntityExerciseTerm(String entityName, String lastActiveService) {
        if (!entityName.equals(ExerciseTerm.class.getSimpleName())) {
            return;
        }
        
        final ExerciseTermService service = new ExerciseTermService(ExerciseTerm.class.getName());
        final TestdataExerciseTermPresenter presenter = (TestdataExerciseTermPresenter) ENTITIES.get(
                ExerciseTerm.class.getSimpleName()).getPresenter();
        service.bind(presenter);
        service.setExecutor(sequentialExecutorService);
        service.setOnStart("Start with testdata generation from entity ExerciseTerm..."); // NOI18N
        service.setOnSuccededAfterService(
                this.getTestdataPresenter(entityName, lastActiveService),
                "Ready with testdata generation from entity ExerciseTerm..."); // NOI18N
        
        service.start();
    }

    private void configureServiceForEntityTerm(String entityName, String lastActiveService) {
        if (!entityName.equals(Term.class.getSimpleName())) {
            return;
        }
        
        final TermService service = new TermService(Term.class.getName());
        final TestdataTermPresenter presenter = (TestdataTermPresenter) ENTITIES.get(Term.class.getSimpleName()).getPresenter();
        service.bind(presenter);
        service.setExecutor(sequentialExecutorService);
        service.setOnStart("Start with testdata generation from entity Term..."); // NOI18N
        service.setOnSuccededAfterService(
                this.getTestdataPresenter(entityName, lastActiveService),
                "Ready with testdata generation from entity Term..."); // NOI18N
        
        service.start();
    }

    private void configureServiceForEntityTopic(String entityName, String lastActiveService) {
        if (!entityName.equals(Topic.class.getSimpleName())) {
            return;
        }
        
        final TopicService service = new TopicService(Topic.class.getName());
        final TestdataTopicPresenter presenter = (TestdataTopicPresenter) ENTITIES.get(Topic.class.getSimpleName()).getPresenter();
        service.bind(presenter);
        service.setExecutor(sequentialExecutorService);
        service.setOnStart("Start with testdata generation from entity Topic..."); // NOI18N
        service.setOnSuccededAfterService(
                this.getTestdataPresenter(entityName, lastActiveService),
                "Ready with testdata generation from entity Topic..."); // NOI18N
        
        service.start();
    }
    
    private TestdataPresenter getTestdataPresenter(String entityName, String lastActiveService) {
        return entityName.equals(lastActiveService) ? this : null;
    }
    
}

/*
 * Copyright (C) 2016 Naoghuman
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

import com.github.naoghuman.abclist.configuration.IActionConfiguration;
import static com.github.naoghuman.abclist.configuration.IActionConfiguration.ACTION__APPLICATION__OPEN_TERM;
import com.github.naoghuman.abclist.configuration.IApplicationConfiguration;
import static com.github.naoghuman.abclist.configuration.IApplicationConfiguration.KEY__APPLICATION__RESOURCE_BUNDLE;
import com.github.naoghuman.abclist.configuration.IDefaultConfiguration;
import com.github.naoghuman.abclist.json.Project;
import com.github.naoghuman.abclist.json.SimpleJsonReader;
import com.github.naoghuman.abclist.view.exercise.ExercisePresenter;
import com.github.naoghuman.abclist.view.exercise.ExerciseView;
import com.github.naoghuman.abclist.model.Exercise;
import com.github.naoghuman.abclist.model.ModelProvider;
import com.github.naoghuman.abclist.model.NavigationEntity;
import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.abclist.model.Topic;
import com.github.naoghuman.abclist.sql.SqlProvider;
import com.github.naoghuman.abclist.view.application.converter.ExerciseNavigationConverter;
import com.github.naoghuman.abclist.view.application.navigation.ENavigationType;
import com.github.naoghuman.abclist.view.term.TermPresenter;
import com.github.naoghuman.abclist.view.term.TermView;
import com.github.naoghuman.abclist.view.topic.TopicPresenter;
import com.github.naoghuman.abclist.view.topic.TopicView;
import com.github.naoghuman.abclist.view.welcome.WelcomeView;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.IRegisterActions;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.properties.api.PropertiesFacade;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Callback;

/**
 *
 * @author Naoghuman
 */
public class ApplicationPresenter implements Initializable, IActionConfiguration, 
        IApplicationConfiguration, IDefaultConfiguration, IRegisterActions 
{
    @FXML private Button bNavigationCreateNewExercise;
    @FXML private Button bNavigationCreateNewTerm;
    @FXML private ComboBox<Topic> cbNavigationTopics;
    @FXML private Label lInfoFoundedElements;
    @FXML private Label lInfoFoundedTerms;
    @FXML private Label lInfoFoundedTopics;
    @FXML private ListView<NavigationEntity> lvNavigationElements;
    @FXML private ListView<Term> lvNavigationTerms;
    @FXML private ListView<Topic> lvNavigationTopics;
    @FXML private SplitPane spApplication;
    @FXML private TabPane tpNavigation;
    @FXML private VBox vbWorkingArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ApplicationPresenter"); // NOI18N
        
//        assert (apView != null) : "fx:id=\"apView\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        
        // XXX test
        final SimpleJsonReader reader = new SimpleJsonReader();
        final Project project = reader.read();
        LoggerFacade.getDefault().info(this.getClass(), "release.json: " + project.toString()); // NOI18N

        this.initializeNavigationButtons();
        this.initializeNavigationTabTerms();
        this.initializeNavigationTabTopics();
        this.initializeWelcomeView();

        this.registerActions();
            
        // Update gui
        final ObservableList<Topic> topics = SqlProvider.getDefault().findAllTopics();
        this.onActionRefreshNavigationTabTopics(topics);
//        NavigationProvider.getDefault().onActionRefreshNavigationTabTerms(topics);
    }
    
    private void initializeNavigationButtons() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Navigation] buttons"); // NOI18N

        // Buttons
        bNavigationCreateNewExercise.disableProperty().bind(lvNavigationTopics.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
        
        bNavigationCreateNewTerm.managedProperty().bind(tpNavigation.getSelectionModel().selectedIndexProperty().isEqualTo(TAB_INDEX__TERMS));
        bNavigationCreateNewTerm.visibleProperty().bind(tpNavigation.getSelectionModel().selectedIndexProperty().isEqualTo(TAB_INDEX__TERMS));
    }
    
    private void initializeNavigationTabTerms() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Navigation] tab [Term]s"); // NOI18N

        /*
        TODO
         - configure ComboBox cbExistingTopics
         - configure ListView lvAssociatedTerms
        */
    }
    
    private void initializeNavigationTabTopics() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Navigation] tab [Topic]s"); // NOI18N
        
        // Info label for Topics
        lInfoFoundedTopics.setText(this.getProperty(INFO__FOUNDED_TOPICS).replaceFirst(INFO__DEFAULT_REGEX, INFO__NO_ENTITIES_FOUND));

        // ListView lvNavigationTopics
        final Callback callbackTerms = (Callback<ListView<Topic>, ListCell<Topic>>) (ListView<Topic> listView) -> new ListCell<Topic>() {
            @Override
            protected void updateItem(Topic topic, boolean empty) {
                super.updateItem(topic, empty);
                
                this.setGraphic(null);
                
                if (topic == null || empty) {
                    this.setText(null);
                } else {
                    this.setText(topic.getTitle());
                }
            }
        };
        
        lvNavigationTopics.setCellFactory(callbackTerms);
        lvNavigationTopics.setOnMouseClicked(event -> {
            // Open the Topic
            if (
                    event.getClickCount() == 2
                    && !lvNavigationTopics.getSelectionModel().isEmpty()
            ) {
                final Topic topic = lvNavigationTopics.getSelectionModel().getSelectedItem();
                this.onActionOpenTopic(topic);
            }
            
            // Show all TopicChilds
            if (
                    event.getClickCount() == 1
                    && !lvNavigationTopics.getSelectionModel().isEmpty()
            ) {
                final Topic topic = lvNavigationTopics.getSelectionModel().getSelectedItem();
                this.onActionShowAllExercisesWithTopicId(topic);
            }
        });
        lvNavigationTopics.setOnKeyPressed(event -> {
            final KeyCode keyCode = event.getCode();
            
            // Open Topic
            if (keyCode.equals(KeyCode.TAB)) {
                final Topic topic = lvNavigationTopics.getSelectionModel().getSelectedItem();
                this.onActionOpenTopic(topic);
            }
            
            // Show all TopicChilds
            if (
                    keyCode.equals(KeyCode.ENTER)
                    || keyCode.equals(KeyCode.SPACE)
            ) {
                final Topic topic = lvNavigationTopics.getSelectionModel().getSelectedItem();
                this.onActionShowAllExercisesWithTopicId(topic);
            }
        });
        
        // Info label for TopicElements
        lInfoFoundedElements.setText(this.getProperty(INFO__FOUNDED_TOPIC_ELEMENTS).replaceFirst(INFO__DEFAULT_REGEX, INFO__NO_ENTITIES_FOUND));
        
        // ListView lvNavigationElements
        final Callback callbackNavigationEntitys = (Callback<ListView<NavigationEntity>, ListCell<NavigationEntity>>) (ListView<NavigationEntity> listView) -> new ListCell<NavigationEntity>() {
            @Override
            protected void updateItem(NavigationEntity navigationEntity, boolean empty) {
                super.updateItem(navigationEntity, empty);
                
                // Check if ...
                if (navigationEntity == null) {
                    this.setGraphic(null);
                    this.setText(null);

                    return;
                }
                
                this.setGraphic(null);
                this.setText(!empty ? navigationEntity.getEntityConverter().getRepresentation() : null);
            }
        };
        // TODO rename NavigationEntity to NavigationElement ? or NavigationTopicChild
        lvNavigationElements.setCellFactory(callbackNavigationEntitys);
        lvNavigationElements.setOnMouseClicked(event -> {
            if (
                    event.getClickCount() == 2
                    && !lvNavigationElements.getSelectionModel().isEmpty()
            ) {
                final TransferData transferData = new TransferData();
                transferData.setActionId(ACTION__APPLICATION__OPEN_EXERCISE);
                
                final NavigationEntity topic = lvNavigationElements.getSelectionModel().getSelectedItem();
                final long exercseId = topic.getNavigation().getEntityId();
                transferData.setLong(exercseId);
                
                ActionFacade.getDefault().handle(transferData);
            }
        });
    }
    
    private void initializeWelcomeView() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [WelcomeView]"); // NOI18N
        
        final WelcomeView welcomeView = new WelcomeView();
        final Parent parent = welcomeView.getView();
        VBox.setVgrow(parent, Priority.ALWAYS);
        vbWorkingArea.getChildren().add(parent);
    }
    
    private String getProperty(String propertyKey) {
        return PropertiesFacade.getDefault().getProperty(KEY__APPLICATION__RESOURCE_BUNDLE, propertyKey);
    }
    
    public void onActionCreateNewExercise() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action create new [Exercise]"); // NOI18N

        // Check if empty
        if (lvNavigationTopics.getSelectionModel().isEmpty()) {
            LoggerFacade.getDefault().warn(this.getClass(), "No [Topic] is selected! Can't create a new [Exercise]"); // NOI18N
            return;
        }
        
        // Create a new Exercise
        final Topic topic = lvNavigationTopics.getSelectionModel().getSelectedItem();
        final Exercise exercise = ModelProvider.getDefault().getExercise(IDefaultConfiguration.DEFAULT_ID, topic.getId());
        SqlProvider.getDefault().createExercise(exercise);
        
        // Open it
        this.onActionShowExerciseInWorkingArea(exercise);
        
        // Refresh gui
        this.onActionShowAllExercisesWithTopicId(topic);
    }
    
    public void onActionCreateNewTerm() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action create new [Term]"); // NOI18N
        
        // TODO replace it with AnchorPane
        final TextInputDialog dialog = new TextInputDialog(); // NOI18N
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setHeaderText("Create Term"); // NOI18N
        dialog.setResizable(false);
        dialog.setTitle("Simple Term Wizard"); // NOI18N
        
        final Optional<String> result = dialog.showAndWait();
        if (
                result.isPresent()
                && !result.get().isEmpty()
        ) {
            // Check if the [Term] always exists
            final ObservableList<Term> terms = SqlProvider.getDefault().findAllTerms();
            final String title = result.get();
            for (Term term : terms) {
                if (term.getTitle().equals(title)) {
                    return;
                }
            }
            
            // Create a new [Term]
            final Term term = ModelProvider.getDefault().getTerm(title);
            SqlProvider.getDefault().createTerm(term);
            
            // Update gui
//            NavigationProvider.getDefault().onActionSelectPreviousSelectedIndex();
        }
    }
    
    public void onActionCreateNewTopic() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action create new [Topic]"); // NOI18N
        
        // TODO replace it with AnchorPane (transparent dialog), show warning when title exists
        final TextInputDialog dialog = new TextInputDialog(); // NOI18N
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setHeaderText("Create Topic"); // NOI18N
        dialog.setResizable(false);
        dialog.setTitle("Simple Topic Wizard"); // NOI18N
        
        final Optional<String> result = dialog.showAndWait();
        if (
                result.isPresent()
                && !result.get().isEmpty()
        ) {
            // Check if the [Topic] always exists
            final ObservableList<Topic> topics = SqlProvider.getDefault().findAllTopics();
            final String title = result.get();
            for (Topic topic : topics) {
                if (topic.getTitle().equals(title)) {
                    return;
                }
            }
            
            // Create a new [Topic]
            final Topic topic = ModelProvider.getDefault().getTopic(title);
            SqlProvider.getDefault().createTopic(topic);
            
            // Update gui
            topics.clear();
            topics.addAll(SqlProvider.getDefault().findAllTopics());
            this.onActionRefreshNavigationTabTopics(topics);
        }
    }
    
    private void onActionShowExerciseInWorkingArea(Exercise exercise) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action show [Exercise] in [WorkingArea]"); // NOI18N
        
        final ExerciseView exerciseView = new ExerciseView();
        final ExercisePresenter exercisePresenter = exerciseView.getRealPresenter();
        exercisePresenter.configure(exercise);
        
        // TODO bind NavigationEntiy.readyProperty() to exercise.readyProperty()
        // if exercise is remove then the binding should also removed.
        
        final Parent parent = exerciseView.getView();
        VBox.setVgrow(parent, Priority.ALWAYS);
        
        vbWorkingArea.getChildren().clear();
        vbWorkingArea.getChildren().add(parent);
    }

    private void onActionOpenExerciseWithId(long exerciseId) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action open [Exercise] with [Id]"); // NOI18N
 
        // Load [Exercise] from the [Database]
        final Optional<Exercise> optional = SqlProvider.getDefault().findById(Exercise.class, exerciseId);
        if (optional.isPresent()) {
            this.onActionShowExerciseInWorkingArea(optional.get());
        }
    }
    
    private void onActionOpenTerm(Term term) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action show [Term]"); // NOI18N

        vbWorkingArea.getChildren().clear();

        final TermView termView = new TermView();
        final TermPresenter termPresenter = termView.getRealPresenter();
        termPresenter.configure(term);
        
        final Parent parent = termView.getView();
        VBox.setVgrow(parent, Priority.ALWAYS);
        vbWorkingArea.getChildren().add(parent);
    }
    
    private void onActionOpenTermWithId(long entityId) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action show [Term] with [Id]"); // NOI18N
        
        // Was the [Term] previously open?
//        int index = 0;
//        for (Navigation navigation : navigationViews) {
//            final Object object = navigation.getView();
//            if (object instanceof TermView) {
//                final TermView termView = (TermView) object;
//                final TermPresenter termPresenter = termView.getRealPresenter();
//                if (Objects.equals(termPresenter.getId(), term.getId())) {
//                    indexShownNavigationView = index;
//                    LoggerFacade.getDefault().debug(this.getClass(), "Show [TermView (index=" + indexShownNavigationView + ")]"); // NOI18N
//        
//                    final Parent parent = termView.getView();
//                    VBox.setVgrow(parent, Priority.ALWAYS);
//                    vbWorkingArea.getChildren().add(parent);
//                    return;
//                }
//            }
//            
//            ++index;
//        }
 
        // Load [Term] from the [Database]
        final Optional<Term> optional = SqlProvider.getDefault().findById(Term.class, entityId);
        if (optional.isPresent()) {
            this.onActionOpenTerm(optional.get());
        }
    }
    
    private void onActionOpenTopic(Topic topic) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action open [Topic]"); // NOI18N
        
        vbWorkingArea.getChildren().clear();

        final TopicView topicView = new TopicView();
        final TopicPresenter topicPresenter = topicView.getRealPresenter();
        topicPresenter.configure(topic);
        
        final Parent parent = topicView.getView();
        VBox.setVgrow(parent, Priority.ALWAYS);
        vbWorkingArea.getChildren().add(parent);
    }
    
    public void onActionRefreshNavigationTabTopics(ObservableList<Topic> topics) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action refres [Navigation] tab [Topics]"); // NOI18N

        lvNavigationTopics.getItems().clear();
        lvNavigationTopics.getItems().addAll(topics);
        
        lInfoFoundedTopics.setText(this.getProperty(INFO__FOUNDED_TOPICS).replaceFirst(INFO__DEFAULT_REGEX, String.valueOf(topics.size())));
    }

    private void onActionShowAllExercisesWithTopicId(Topic topic) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action show all [Exercise]s with [Topic.Id]"); // NOI18N

        final ObservableList<Exercise> exercises = SqlProvider.getDefault().findAllExercisesWithTopicId(topic.getId());
        topic.setExercises(exercises.size());
        
        final List<NavigationEntity> navigationEntities = exercises.stream()
                .map((exercise) -> ModelProvider.getDefault().getNavigationEntity(ENavigationType.EXERCISE, exercise.getId(), new ExerciseNavigationConverter(exercise)))
                .collect(Collectors.toList());

        lvNavigationElements.getItems().clear();
        lvNavigationElements.getItems().addAll(navigationEntities);
        
        lInfoFoundedElements.setText(this.getProperty(INFO__FOUNDED_TOPIC_ELEMENTS).replaceFirst(INFO__DEFAULT_REGEX, String.valueOf(navigationEntities.size())));
    }
    
    public void onActionShowTermsFromSelectedTopic() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action show [Terms]s from selected [Topic]"); // NOI18N

//        NavigationProvider.getDefault().onActionShowTermsFromSelectedTopic();
    }
    
    @Override
    public void registerActions() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register actions in [ApplicationPresenter]"); // NOI18N
        
        this.registerOnActionOpenExercise();
        this.registerOnActionOpenTerm();
    }
    
    private void registerOnActionOpenExercise() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register on action open [Exercise]"); // NOI18N
        
        ActionFacade.getDefault().register(
                ACTION__APPLICATION__OPEN_EXERCISE,
                (ActionEvent event) -> {
                    final TransferData transferData = (TransferData) event.getSource();
                    final long exercseId = transferData.getLong();
                    this.onActionOpenExerciseWithId(exercseId);
                });
    }
    
    private void registerOnActionOpenTerm() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register on action open [Term]"); // NOI18N
        
        ActionFacade.getDefault().register(
                ACTION__APPLICATION__OPEN_TERM,
                (ActionEvent event) -> {
                    final TransferData transferData = (TransferData) event.getSource();
                    final long entityId = transferData.getLong();
                    this.onActionOpenTermWithId(entityId);
                    // TODO select tab terms, select index from the topic in the combobox
                });
    }
    
}

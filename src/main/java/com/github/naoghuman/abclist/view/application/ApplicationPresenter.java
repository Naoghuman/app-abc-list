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

import com.github.naoghuman.abclist.view.application.navigation.Navigation;
import com.github.naoghuman.abclist.configuration.IActionConfiguration;
import com.github.naoghuman.abclist.configuration.IApplicationConfiguration;
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
import com.github.naoghuman.abclist.view.application.navigation.ENavigationType;
import com.github.naoghuman.abclist.view.application.navigation.NavigationProvider;
import com.github.naoghuman.abclist.view.term.TermPresenter;
import com.github.naoghuman.abclist.view.term.TermView;
import com.github.naoghuman.abclist.view.topic.TopicPresenter;
import com.github.naoghuman.abclist.view.topic.TopicView;
import com.github.naoghuman.abclist.view.welcome.WelcomeView;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.IRegisterActions;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 *
 * @author Naoghuman
 */
public class ApplicationPresenter implements Initializable, IActionConfiguration, 
        IApplicationConfiguration, IDefaultConfiguration, IRegisterActions 
{
    
    @FXML private Button bNavigationCreateNewTopic;
    @FXML private Button bNavigationCreateNewTerm;
    @FXML private Button bNavigationToHome;
    @FXML private Button bNavigationToNext;
    @FXML private Button bNavigationToPrevious;
    @FXML private Button bNavigationShowAll;
    @FXML private ComboBox<Topic> cbNavigationTopics;
    @FXML private Label lInfoFoundedTerms;
    @FXML private ListView<Term> lvNavigationTerms;
    @FXML private SplitPane spApplication;
    @FXML private TabPane tpNavigation;
    @FXML private TreeView<NavigationEntity> tvNavigationTopics;
    @FXML private VBox vbWorkingArea;
    
    private final ObservableList<Navigation> navigationViews = FXCollections.observableArrayList();
    
    private int indexShownNavigationView = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ApplicationPresenter"); // NOI18N
        
//        assert (apView != null) : "fx:id=\"apView\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        
        // XXX test
        final SimpleJsonReader reader = new SimpleJsonReader();
        final Project project = reader.read();
        LoggerFacade.getDefault().info(this.getClass(), "release.json: " + project.toString()); // NOI18N

        this.initializeNavigation();
        this.initializeWelcomeView();
        
        NavigationProvider.getDefault().initialize(tvNavigationTopics, cbNavigationTopics, lInfoFoundedTerms, lvNavigationTerms);

        this.registerActions();
            
        // Update gui
        final ObservableList<Topic> topics = SqlProvider.getDefault().findAllTopics();
        NavigationProvider.getDefault().onActionRefreshNavigationTabTopics(topics);
        NavigationProvider.getDefault().onActionRefreshNavigationTabTerms(topics);
    }
    
    private void initializeNavigation() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Navigation]"); // NOI18N

        // Buttons
        bNavigationCreateNewTopic.managedProperty().bind(tpNavigation.getSelectionModel().selectedIndexProperty().isEqualTo(TAB_INDEX__TOPICS));
        bNavigationCreateNewTopic.visibleProperty().bind(tpNavigation.getSelectionModel().selectedIndexProperty().isEqualTo(TAB_INDEX__TOPICS));
        
        bNavigationCreateNewTerm.managedProperty().bind(tpNavigation.getSelectionModel().selectedIndexProperty().isEqualTo(TAB_INDEX__TERMS));
        bNavigationCreateNewTerm.visibleProperty().bind(tpNavigation.getSelectionModel().selectedIndexProperty().isEqualTo(TAB_INDEX__TERMS));
    }
    
    private void initializeWelcomeView() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [WelcomeView]"); // NOI18N
        
        final WelcomeView welcomeView = new WelcomeView();
        
        final Navigation navigation = new Navigation(ENavigationType.WELCOME, DEFAULT_ID);
        navigationViews.add(navigation);
        indexShownNavigationView = 0;
        LoggerFacade.getDefault().debug(this.getClass(), "Add [WelcomeView (index=" + indexShownNavigationView + ")]"); // NOI18N
        
        final Parent parent = welcomeView.getView();
        VBox.setVgrow(parent, Priority.ALWAYS);
        vbWorkingArea.getChildren().add(parent);
    }
    
    private void onActionCreateNewExerciseWithTopicId(long entityId) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action create new [Exercise] with [Topic.Id]"); // NOI18N
        
        // Load [Topic] from the [Database]
        final Optional<Topic> optional = SqlProvider.getDefault().findById(Topic.class, entityId);
        if (!optional.isPresent()) {
            LoggerFacade.getDefault().warn(this.getClass(), "Can't find a [Topic] with [id=" + entityId + "] in [Database]"); // NOI18N
            return;
        }
        final Topic topic = optional.get();
        
        // Create a new Exercise
        final Exercise exercise = ModelProvider.getDefault().getExercise(IDefaultConfiguration.DEFAULT_ID, topic.getId());
        SqlProvider.getDefault().createExercise(exercise);
        
        // Open the new exercise
        this.onActionOpenExercise(exercise);
            
        // Update the gui
        final ObservableList<Topic> topics = SqlProvider.getDefault().findAllTopics();
        NavigationProvider.getDefault().onActionRefreshNavigationTabTopics(topics);
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
            NavigationProvider.getDefault().onActionSelectPreviousSelectedIndex();
        }
    }
    
    public void onActionCreateNewTopic() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action create new [Topic]"); // NOI18N
        
        // TODO replace it with AnchorPane, show warning when title exists
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
            NavigationProvider.getDefault().onActionRefreshNavigationTabTopics(topics);
        }
    }
    
    /*
    TODO Navigation handling
    
    @FXML private Button bNavigationToHome;
    @FXML private Button bNavigationToNext;
    @FXML private Button bNavigationToPrevious;
    @FXML private Button bNavigationShowAll;
    
    bNavigationToHome
     - If [indexShownNavigationView] != 0
        -> activate button bNavigationToHome
        - else disable the button.
    
    bNavigationToNext
     - If [navigationViews.size()] > 0 && [indexShownNavigationView] < [navigationViews.size() - 1] 
        -> activate button bNavigationToNext
        - else disable the button.
    
    bNavigationToPrevious
     - If [navigationViews.size()] > 0 && [indexShownNavigationView] > 0
        -> activate button bNavigationToPrevious
        - else disable the button.
    
    bNavigationShowAll
     - If the size from [navigationViews] > 1
        -> activate button bNavigationShowAll
    */
    
    public void onActionNavigationToHome() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action [Navigation] to [Home]"); // NOI18N
        
        /*
        TODO
         - show the [WelcomeView] (index == 0)
         - set [indexShownNavigationView] to 0
        */
    }
    
    public void onActionNavigationToNext() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action [Navigation] to [Next]"); // NOI18N
        /*
        TODO
         - show the [XyView] ([indexShownNavigationView] + 1)
            - if ([indexShownNavigationView] + 1) >= [navigationViews.size()] throw error
         - set [indexShownNavigationView] to ([indexShownNavigationView] + 1)
        */
    }
    
    public void onActionNavigationToPrevious() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action [Navigation] to [Previous]"); // NOI18N
        /*
        TODO
         - show the [XyView] ([indexShownNavigationView] - 1)
            - if ([indexShownNavigationView] - 1) < [0] throw error
         - set [indexShownNavigationView] to ([indexShownNavigationView] - 1)
        */
    }
    
    public void onActionNavigationShowAll() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action [Navigation] show all"); // NOI18N
        
        /*
        TODO
         - show little popup with all [NameFromView] in the order they was added.
            - use [navigationViews]
         - User click on one open the [XyView]
         - set [indexShownNavigationView] to user click index
        */
    }
    
    private void onActionOpenExercise(Exercise exercise) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action open [Exercise]"); // NOI18N
        
        vbWorkingArea.getChildren().clear();
        
        final ExerciseView exerciseView = new ExerciseView();
        final ExercisePresenter exercisePresenter = exerciseView.getRealPresenter();
        exercisePresenter.configure(exercise);
        
        final Navigation navigation = new Navigation(ENavigationType.EXERCISE, exercise.getId());
        navigationViews.add(navigation);
        indexShownNavigationView = navigationViews.size() - 1;
        LoggerFacade.getDefault().debug(this.getClass(), "Add [ExerciseView (index=" + indexShownNavigationView + ")]"); // NOI18N
        
        final Parent parent = exerciseView.getView();
        VBox.setVgrow(parent, Priority.ALWAYS);
        vbWorkingArea.getChildren().add(parent);
    }

    private void onActionOpenExerciseWithId(long entityId) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action open [Exercise] with [Id]"); // NOI18N
 
        // Load [Exercise] from the [Database]
        final Optional<Exercise> optional = SqlProvider.getDefault().findById(Exercise.class, entityId);
        if (optional.isPresent()) {
            this.onActionOpenExercise(optional.get());
        }
    }
    
    private void onActionOpenTerm(Term term) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action show [Term]"); // NOI18N

        vbWorkingArea.getChildren().clear();

        final TermView termView = new TermView();
        final TermPresenter termPresenter = termView.getRealPresenter();
        termPresenter.configure(term);
        
        final Navigation navigation = new Navigation(ENavigationType.TERM, term.getId());
        navigationViews.add(navigation);
        indexShownNavigationView = navigationViews.size() - 1;
        LoggerFacade.getDefault().debug(this.getClass(), "Add [TermView (index=" + indexShownNavigationView + ")]"); // NOI18N
        
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
        
        final Navigation navigation = new Navigation(ENavigationType.TOPIC, topic.getId());
        navigationViews.add(navigation);
        indexShownNavigationView = navigationViews.size() - 1;
        LoggerFacade.getDefault().debug(this.getClass(), "Add [TopicView (index=" + indexShownNavigationView + ")]"); // NOI18N
        
        final Parent parent = topicView.getView();
        VBox.setVgrow(parent, Priority.ALWAYS);
        vbWorkingArea.getChildren().add(parent);
    }
    
    private void onActionOpenTopicWithId(long entityId) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action open [Topic] with [Id]"); // NOI18N
        
        // Load [Topic] from the [Database]
        final Optional<Topic> optional = SqlProvider.getDefault().findById(Topic.class, entityId);
        if (optional.isPresent()) {
            this.onActionOpenTopic(optional.get());
        }
    }
    
    public void onActionShowTermsFromSelectedTopic() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action show [Terms]s from selected [Topic]"); // NOI18N

        NavigationProvider.getDefault().onActionShowTermsFromSelectedTopic();
    }
    
    @Override
    public void registerActions() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register actions in [ApplicationPresenter]"); // NOI18N
        
        this.registerOnActionCreateNewExercise();
        
        this.registerOnActionOpenExercise();
        this.registerOnActionOpenTerm();
        this.registerOnActionOpenTopic();
    }
    
    private void registerOnActionCreateNewExercise() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register on action create new [Exercise]"); // NOI18N
        
        ActionFacade.getDefault().register(
                ACTION__APPLICATION__CREATE_NEW_EXERCISE,
                (ActionEvent event) -> {
                    final TransferData transferData = (TransferData) event.getSource();
                    final long entityId = transferData.getLong();
                    this.onActionCreateNewExerciseWithTopicId(entityId);
                });
    }
    
    private void registerOnActionOpenExercise() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register on action open [Exercise]"); // NOI18N
        
        ActionFacade.getDefault().register(
                ACTION__APPLICATION__OPEN_EXERCISE,
                (ActionEvent event) -> {
                    final TransferData transferData = (TransferData) event.getSource();
                    final long entityId = transferData.getLong();
                    this.onActionOpenExerciseWithId(entityId);
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
    
    private void registerOnActionOpenTopic() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register on action open [Topic]"); // NOI18N
        
        ActionFacade.getDefault().register(
                ACTION__APPLICATION__OPEN_TOPIC,
                (ActionEvent event) -> {
                    final TransferData transferData = (TransferData) event.getSource();
                    final long entityId = transferData.getLong();
                    this.onActionOpenTopicWithId(entityId);
                });
    }
    
}

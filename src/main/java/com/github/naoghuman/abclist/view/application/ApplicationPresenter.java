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
import com.github.naoghuman.abclist.configuration.IDefaultConfiguration;
import com.github.naoghuman.abclist.configuration.IPropertiesConfiguration;
import com.github.naoghuman.abclist.dialog.DialogProvider;
import com.github.naoghuman.abclist.json.Project;
import com.github.naoghuman.abclist.json.SimpleJsonReader;
import com.github.naoghuman.abclist.view.exercise.ExercisePresenter;
import com.github.naoghuman.abclist.view.exercise.ExerciseView;
import com.github.naoghuman.abclist.model.Exercise;
import com.github.naoghuman.abclist.model.Link;
import com.github.naoghuman.abclist.model.LinkMappingType;
import com.github.naoghuman.abclist.model.ModelProvider;
import com.github.naoghuman.abclist.model.NavigationEntity;
import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.abclist.model.Topic;
import com.github.naoghuman.abclist.sql.SqlProvider;
import com.github.naoghuman.converter.ExercisePresentationConverter;
import com.github.naoghuman.converter.LinkPresentationConverter;
import com.github.naoghuman.converter.TermPresentationConverter;
import com.github.naoghuman.converter.TopicPresentationConverter;
import com.github.naoghuman.abclist.view.link.LinkPresenter;
import com.github.naoghuman.abclist.view.link.LinkView;
import com.github.naoghuman.abclist.view.term.TermPresenter;
import com.github.naoghuman.abclist.view.term.TermView;
import com.github.naoghuman.abclist.view.topic.TopicPresenter;
import com.github.naoghuman.abclist.view.topic.TopicView;
import com.github.naoghuman.abclist.view.welcome.WelcomeView;
import com.github.naoghuman.lib.action.core.ActionHandlerFacade;
import com.github.naoghuman.lib.action.core.RegisterActions;
import com.github.naoghuman.lib.action.core.TransferData;
import com.github.naoghuman.lib.action.core.TransferDataBuilder;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
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
        IDefaultConfiguration, IPropertiesConfiguration, RegisterActions 
{
    @FXML private Button bNavigationCreateNewExercise;
    @FXML private Button bNavigationCreateNewTerm;
    @FXML private ComboBox<Topic> cbFindTermsInTopics;
    @FXML private ComboBox<Term> cbFindLinksInTerms;
    @FXML private ComboBox<Topic> cbFindLinksInTopics;
    @FXML private Label lInfoFoundedNavigationEntities;
    @FXML private Label lInfoFoundedLinks;
    @FXML private Label lInfoFoundedTerms;
    @FXML private Label lInfoFoundedTopics;
    @FXML private ListView<NavigationEntity> lvFoundedNavigationEntities;
    @FXML private ListView<Link> lvFoundedLinks;
    @FXML private ListView<Term> lvFoundedTerms;
    @FXML private ListView<Topic> lvFoundedTopics;
    @FXML private RadioButton rbFindInTerms;
    @FXML private SplitPane spApplication;
    @FXML private TabPane tpNavigation;
    @FXML private VBox vbWorkingArea;
    
    private final ToggleGroup toggleGroup = new ToggleGroup();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ApplicationPresenter"); // NOI18N
        
//        assert (apView != null) : "fx:id=\"apView\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        
        // XXX test
        final SimpleJsonReader reader = new SimpleJsonReader();
        final Project project = reader.read();
        LoggerFacade.getDefault().info(this.getClass(), "release.json: " + project.toString()); // NOI18N

        this.initializeNavigationButtons();
        this.initializeNavigationTabPane();
        this.initializeNavigationTabLinks();
        this.initializeNavigationTabTerms();
        this.initializeNavigationTabTopics();
        this.initializeWelcomeView();

        this.register();
            
        // Update gui
        this.onActionRefreshNavigationTabTopics();
    }
    
    private void initializeNavigationButtons() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Navigation] buttons"); // NOI18N

        // Buttons
        bNavigationCreateNewExercise.disableProperty().bind(lvFoundedTopics.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
        
        bNavigationCreateNewTerm.managedProperty().bind(tpNavigation.getSelectionModel().selectedIndexProperty().isEqualTo(TAB_INDEX__TERMS));
        bNavigationCreateNewTerm.visibleProperty().bind(tpNavigation.getSelectionModel().selectedIndexProperty().isEqualTo(TAB_INDEX__TERMS));
    }
    
    private void initializeNavigationTabPane() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Navigation] [TabPane]"); // NOI18N

        tpNavigation.getSelectionModel().selectedIndexProperty()
                .addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    if (newValue.intValue() == TAB_INDEX__TERMS) {
                        this.onActionRefreshNavigationTabTerms();
                    }
                    
                    if (newValue.intValue() == TAB_INDEX__LINKS) {
                        this.onActionRefreshNavigationTabLinks();
                    }
                });
    }
    
    private void initializeNavigationTabLinks() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Navigation] tab [Links]"); // NOI18N

        // ComboBox cbFindLinksInTerms
        final Callback callbackTerms = (Callback<ListView<Term>, ListCell<Term>>) (ListView<Term> listView) -> new ListCell<Term>() {
            @Override
            protected void updateItem(Term term, boolean empty) {
                super.updateItem(term, empty);

                this.setGraphic(null);

                if (term == null || empty) {
                    this.setText(null);
                } else {
                    this.setText(term.getTitle());
                }
            }
        };
        
        cbFindLinksInTerms.setButtonCell((ListCell) callbackTerms.call(null));
        cbFindLinksInTerms.setCellFactory(callbackTerms);
        cbFindLinksInTerms.setVisible(Boolean.FALSE);
        cbFindLinksInTerms.setManaged(Boolean.FALSE);

        // ComboBox cbFindLinksInTopics
        final Callback callbackTopics = (Callback<ListView<Topic>, ListCell<Topic>>) (ListView<Topic> listView) -> new ListCell<Topic>() {
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
        
        cbFindLinksInTopics.setButtonCell((ListCell) callbackTopics.call(null));
        cbFindLinksInTopics.setCellFactory(callbackTopics);
        
        // ListView lvNavigationElements
        lInfoFoundedLinks.setText(LinkPresentationConverter.getI18nMsgFoundedEntities(NO_ENTITY));

        final Callback callbackLinks = (Callback<ListView<Link>, ListCell<Link>>) (ListView<Link> listView) -> new ListCell<Link>() {
            @Override
            protected void updateItem(Link link, boolean empty) {
                super.updateItem(link, empty);
                
                this.setGraphic(null);
                
                if (link == null || empty) {
                    this.setText(null);
                } else {
                    this.setText(link.getAlias());
                }
            }
        };
        lvFoundedLinks.setCellFactory(callbackLinks);
        lvFoundedLinks.setOnMouseClicked(event -> {
            // Open the Link
            if (
                    event.getClickCount() == 2
                    && !lvFoundedLinks.getSelectionModel().isEmpty()
            ) {
                final Link link = lvFoundedLinks.getSelectionModel().getSelectedItem();
                this.onActionOpenLink(link);
            }
        });
        
        rbFindInTerms.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            cbFindLinksInTerms.getSelectionModel().clearSelection();
            cbFindLinksInTerms.setVisible(newValue);
            cbFindLinksInTerms.setManaged(newValue);
            
            cbFindLinksInTopics.getSelectionModel().clearSelection();
            cbFindLinksInTopics.setVisible(!newValue);
            cbFindLinksInTopics.setManaged(!newValue);
            
            lvFoundedLinks.getItems().clear();
        });
    }
    
    private void initializeNavigationTabTerms() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Navigation] tab [Terms]"); // NOI18N

        // ComboBox cbFindTermsInTopics
        final Callback callbackTopics = (Callback<ListView<Topic>, ListCell<Topic>>) (ListView<Topic> listView) -> new ListCell<Topic>() {
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
        
        cbFindTermsInTopics.setButtonCell((ListCell) callbackTopics.call(null));
        cbFindTermsInTopics.setCellFactory(callbackTopics);
        
        // ListView lvFoundedTerms
        lInfoFoundedTerms.setText(TermPresentationConverter.getI18nMsgFoundedEntities(IPropertiesConfiguration.NO_ENTITY));
        
        final Callback callbackTerms = (Callback<ListView<Term>, ListCell<Term>>) (ListView<Term> listView) -> new ListCell<Term>() {
            @Override
            protected void updateItem(Term term, boolean empty) {
                super.updateItem(term, empty);
                
                this.setGraphic(null);
                
                if (term == null || empty) {
                    this.setText(null);
                } else {
                    this.setText(term.getTitle());
                }
            }
        };
        lvFoundedTerms.setCellFactory(callbackTerms);
        lvFoundedTerms.setOnMouseClicked(event -> {
            // Open the Term
            if (
                    event.getClickCount() == 2
                    && !lvFoundedTerms.getSelectionModel().isEmpty()
            ) {
                final Term term = lvFoundedTerms.getSelectionModel().getSelectedItem();
                this.onActionOpenTerm(term);
            }
        });
    }
    
    private void initializeNavigationTabTopics() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Navigation] tab [Topics]"); // NOI18N
        
        // Info label for Topics
        lInfoFoundedTopics.setText(TopicPresentationConverter.getI18nMsgFoundedEntities(INFO__FOUNDED_TOPICS, NO_ENTITY));

        // ListView lvFoundedTopics
        final Callback callbackTopics = (Callback<ListView<Topic>, ListCell<Topic>>) (ListView<Topic> listView) -> new ListCell<Topic>() {
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
        lvFoundedTopics.setCellFactory(callbackTopics);
        lvFoundedTopics.setOnMouseClicked(event -> {
            // Open the Topic
            if (
                    event.getClickCount() == 2
                    && !lvFoundedTopics.getSelectionModel().isEmpty()
            ) {
                final Topic topic = lvFoundedTopics.getSelectionModel().getSelectedItem();
                this.onActionOpenTopic(topic);
            }
            
            // Show all TopicChilds
            if (
                    event.getClickCount() == 1
                    && !lvFoundedTopics.getSelectionModel().isEmpty()
            ) {
                final Topic topic = lvFoundedTopics.getSelectionModel().getSelectedItem();
                this.onActionShowAllExercisesWithTopicId(topic);
            }
        });
        lvFoundedTopics.setOnKeyPressed(event -> {
            final KeyCode keyCode = event.getCode();
            
            // Open Topic
            if (keyCode.equals(KeyCode.TAB)) {
                final Topic topic = lvFoundedTopics.getSelectionModel().getSelectedItem();
                this.onActionOpenTopic(topic);
            }
            
            // Show all TopicChilds
            if (
                    keyCode.equals(KeyCode.ENTER)
                    || keyCode.equals(KeyCode.SPACE)
            ) {
                final Topic topic = lvFoundedTopics.getSelectionModel().getSelectedItem();
                this.onActionShowAllExercisesWithTopicId(topic);
            }
        });
        
        // Info label for NavigationEntity
        lInfoFoundedNavigationEntities.setText(TopicPresentationConverter.getI18nMsgFoundedEntities(INFO__FOUNDED_TOPIC_ELEMENTS, NO_ENTITY));
        
        // ListView lvFoundedNavigationEntities
        final Callback callbackNavigationEntitys = (Callback<ListView<NavigationEntity>, ListCell<NavigationEntity>>) (ListView<NavigationEntity> listView) -> new ListCell<NavigationEntity>() {
            @Override
            protected void updateItem(NavigationEntity navigationEntity, boolean empty) {
                super.updateItem(navigationEntity, empty);
                
                this.setGraphic(null);
                
                if (navigationEntity == null) {
                    this.setText(null);
                } else {
                    this.setText(!empty ? navigationEntity.getEntityConverter().getPresentation() : null);
                }
            }
        };
        
        lvFoundedNavigationEntities.setCellFactory(callbackNavigationEntitys);
        lvFoundedNavigationEntities.setOnMouseClicked(event -> {
            if (
                    event.getClickCount() == 2
                    && !lvFoundedNavigationEntities.getSelectionModel().isEmpty()
            ) {
                
                final NavigationEntity navigationEntity = lvFoundedNavigationEntities.getSelectionModel().getSelectedItem();
                final long exercseId = navigationEntity.getNavigation().getEntityId();
                
                final TransferData transferData = TransferDataBuilder.create()
                        .actionId(ACTION__APPLICATION__OPEN_EXERCISE)
                        .longValue(exercseId)
                        .build();
                
                ActionHandlerFacade.getDefault().handle(transferData);
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
    
    public void onActionCreateNewExercise() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action create new [Exercise]"); // NOI18N

        // Check if empty
        if (lvFoundedTopics.getSelectionModel().isEmpty()) {
            LoggerFacade.getDefault().warn(this.getClass(), "No [Topic] is selected! Can't create a new [Exercise]"); // NOI18N
            return;
        }
        
        // Create a new Exercise
        final Topic topic = lvFoundedTopics.getSelectionModel().getSelectedItem();
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
        }
    }
    
    public void onActionCreateNewTopic() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action create new [Topic]"); // NOI18N
        
        DialogProvider.getDefault().showTopicWizard();
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

    private void onActionOpenExerciseWithId(final long exerciseId) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action open [Exercise] with [Id]"); // NOI18N
 
        // Load [Exercise] from the [Database]
        final Optional<Exercise> optional = SqlProvider.getDefault().findById(Exercise.class, exerciseId);
        if (optional.isPresent()) {
            this.onActionShowExerciseInWorkingArea(optional.get());
        }
    }
    
    private void onActionOpenLink(final Link link) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action show [Link]"); // NOI18N

        final LinkView view = new LinkView();
        final LinkPresenter presenter = view.getRealPresenter();
        presenter.configure(link);
        
        final Parent parent = view.getView();
        VBox.setVgrow(parent, Priority.ALWAYS);
        
        vbWorkingArea.getChildren().clear();
        vbWorkingArea.getChildren().add(parent);
    }
    
    private void onActionOpenTerm(final Term term) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action show [Term]"); // NOI18N

        final TermView view = new TermView();
        final TermPresenter presenter = view.getRealPresenter();
        presenter.configure(term);
        
        final Parent parent = view.getView();
        VBox.setVgrow(parent, Priority.ALWAYS);
        
        vbWorkingArea.getChildren().clear();
        vbWorkingArea.getChildren().add(parent);
    }
    
    private void onActionOpenTermWithId(final long entityId) {
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
    
    private void onActionOpenTopic(final Topic topic) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action open [Topic]"); // NOI18N

        final TopicView view = new TopicView();
        final TopicPresenter presenter = view.getRealPresenter();
        presenter.configure(topic);
        
        final Parent parent = view.getView();
        VBox.setVgrow(parent, Priority.ALWAYS);
        
        vbWorkingArea.getChildren().clear();
        vbWorkingArea.getChildren().add(parent);
    }

    private void onActionRefreshNavigationTabLinks() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action refresh [Navigation] tab [Links]"); // NOI18N
        
        // Reset gui
        lInfoFoundedLinks.setText(LinkPresentationConverter.getI18nMsgFoundedEntities(NO_ENTITY));
        lvFoundedLinks.getItems().clear();

        // Reload the [ComboBox] cbFindLinksInTerms
        cbFindLinksInTerms.getSelectionModel().clearSelection();
        cbFindLinksInTerms.getItems().clear();
        
        final ObservableList<Term> terms = SqlProvider.getDefault().findAllTerms();
        final Term termShowAllExistingLinks = ModelProvider.getDefault().getTerm(
                DEFAULT_ID__TERM__SHOW_ALL_EXISTING_LINKS,
                "=== Show all existing Links ==="); // NOI18N
        terms.add(0, termShowAllExistingLinks);
        
        final Term termShowAllLinksWithoutParent = ModelProvider.getDefault().getTerm(
                DEFAULT_ID__TERM__SHOW_ALL_LINKS_WITHOUT_PARENT,
                "=== Show all Links without Parent ==="); // NOI18N
        terms.add(1, termShowAllLinksWithoutParent);
        
        cbFindLinksInTerms.getItems().addAll(terms);

        // Reload the [ComboBox] cbFindLinksInTopics
        cbFindLinksInTopics.getSelectionModel().clearSelection();
        cbFindLinksInTopics.getItems().clear();
        
        final ObservableList<Topic> topics = SqlProvider.getDefault().findAllTopics();
        final Topic topicShowAllExistingLinks = ModelProvider.getDefault().getTopic(
                DEFAULT_ID__TOPIC__SHOW_ALL_EXISTING_LINKS,
                "=== Show all existing Links ==="); // NOI18N
        topics.add(0, topicShowAllExistingLinks);
        
        final Topic topicShowAllLinksWithoutParent = ModelProvider.getDefault().getTopic(
                DEFAULT_ID__TOPIC__SHOW_ALL_LINKS_WITHOUT_PARENT,
                "=== TOPIC Show all Links without Parent ==="); // NOI18N
        topics.add(1, topicShowAllLinksWithoutParent);
        
        cbFindLinksInTopics.getItems().addAll(topics);
    }
    
    private void onActionRefreshNavigationTabTerms() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action refresh [Navigation] tab [Terms]"); // NOI18N
        
        // Reset gui
        lInfoFoundedTerms.setText(TermPresentationConverter.getI18nMsgFoundedEntities(IPropertiesConfiguration.NO_ENTITY));
        lvFoundedTerms.getItems().clear();
        
        // Reload the [ComboBox]
        cbFindTermsInTopics.getSelectionModel().clearSelection();
        cbFindTermsInTopics.getItems().clear();
        
        final ObservableList<Topic> topics = SqlProvider.getDefault().findAllTopics();
        final Topic topicShowAllExistingTerms = ModelProvider.getDefault().getTopic(
                DEFAULT_ID__TOPIC__SHOW_ALL_EXISTING_TERMS,
                "=== Show all existing Terms ==="); // NOI18N
        topics.add(0, topicShowAllExistingTerms);
        
        final Topic topicShowAllTermsWithoutParent = ModelProvider.getDefault().getTopic(
                DEFAULT_ID__TOPIC__SHOW_ALL_TERMS_WITHOUT_PARENT,
                "=== Show all Terms without Parent ==="); // NOI18N
        topics.add(1, topicShowAllTermsWithoutParent);
        
        cbFindTermsInTopics.getItems().addAll(topics);
    }
    
    private void onActionRefreshNavigationTabTopics() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action refresh [Navigation] tab [Topics]"); // NOI18N

        // Reload ListView
        lvFoundedTopics.getItems().clear();
        
        final ObservableList<Topic> topics = SqlProvider.getDefault().findAllTopics();
        lvFoundedTopics.getItems().addAll(topics);
        
        // Show info
        lInfoFoundedTopics.setText(TopicPresentationConverter.getI18nMsgFoundedEntities(INFO__FOUNDED_TOPICS, topics.size()));
    }

    private void onActionShowAllExercisesWithTopicId(Topic topic) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action show all [Exercise]s with [Topic.Id]"); // NOI18N

        final ObservableList<Exercise> exercises = SqlProvider.getDefault().findAllExercisesWithTopicId(topic.getId());
        topic.setExercises(exercises.size());
        
        final List<NavigationEntity> navigationEntities = exercises.stream()
                .map((exercise) -> ModelProvider.getDefault().getNavigationEntity(ENavigationType.EXERCISE, exercise.getId(), new ExercisePresentationConverter(exercise)))
                .collect(Collectors.toList());

        lvFoundedNavigationEntities.getItems().clear();
        lvFoundedNavigationEntities.getItems().addAll(navigationEntities);
        
        lInfoFoundedNavigationEntities.setText(TopicPresentationConverter.getI18nMsgFoundedEntities(INFO__FOUNDED_TOPIC_ELEMENTS, navigationEntities.size()));
    }
    
    public void onActionShowAllLinksFromSelectedTerm() {
        // Is any [Term] in the [ComboBox] selected?
        if (cbFindLinksInTerms.getSelectionModel().isEmpty()) {
            lInfoFoundedLinks.setText(LinkPresentationConverter.getI18nMsgFoundedEntities(NO_ENTITY));
            return;
        }
        
        LoggerFacade.getDefault().debug(this.getClass(), "On action show all [Link]s from selected [Term]"); // NOI18N

        // Which [Link]s should be loaded
        final Term term = cbFindLinksInTerms.getSelectionModel().getSelectedItem();
        final long parentId = term.getId();
        
        // Load [Term]
        final ObservableList<Link> links = FXCollections.observableArrayList();
        if (Objects.equals(parentId, DEFAULT_ID__TERM__SHOW_ALL_EXISTING_LINKS)) {
            links.addAll(SqlProvider.getDefault().findAllLinks());
        }
        else if (Objects.equals(parentId, DEFAULT_ID__TERM__SHOW_ALL_LINKS_WITHOUT_PARENT)) {
            links.addAll(SqlProvider.getDefault().findAllLinksInLinkMappingWithoutParent());
        }
        else {
            links.addAll(SqlProvider.getDefault().findAllLinksInLinkMappingWithParent(parentId, LinkMappingType.TOPIC));
        }
        
        // Show them in gui
        lInfoFoundedLinks.setText(LinkPresentationConverter.getI18nMsgFoundedEntities(links.size()));
         
        lvFoundedLinks.getItems().clear();
        lvFoundedLinks.getItems().addAll(links);
    }
    
    public void onActionShowAllLinksFromSelectedTopic() {
        // Is any [Topic] in the [ComboBox] selected?
        if (cbFindLinksInTopics.getSelectionModel().isEmpty()) {
            lInfoFoundedLinks.setText(LinkPresentationConverter.getI18nMsgFoundedEntities(NO_ENTITY));
            return;
        }
        
        LoggerFacade.getDefault().debug(this.getClass(), "On action show all [Link]s from selected [Topic]"); // NOI18N

        // Which [Link]s should be loaded
        final Topic topic = cbFindLinksInTopics.getSelectionModel().getSelectedItem();
        final long parentId = topic.getId();
        
        // Load [Term]
        final ObservableList<Link> links = FXCollections.observableArrayList();
        if (Objects.equals(parentId, DEFAULT_ID__TOPIC__SHOW_ALL_EXISTING_LINKS)) {
            links.addAll(SqlProvider.getDefault().findAllLinks());
        }
        else if (Objects.equals(parentId, DEFAULT_ID__TOPIC__SHOW_ALL_LINKS_WITHOUT_PARENT)) {
            links.addAll(SqlProvider.getDefault().findAllLinksInLinkMappingWithoutParent());
        }
        else {
            links.addAll(SqlProvider.getDefault().findAllLinksInLinkMappingWithParent(parentId, LinkMappingType.TOPIC));
        }
        
        // Show them in gui
        lInfoFoundedLinks.setText(LinkPresentationConverter.getI18nMsgFoundedEntities(links.size()));
         
        lvFoundedLinks.getItems().clear();
        lvFoundedLinks.getItems().addAll(links);
    }
    
    public void onActionShowAllTermsFromSelectedTopic() {
        // Is any [Topic] in the [ComboBox] selected?
        if (cbFindTermsInTopics.getSelectionModel().isEmpty()) {
            lInfoFoundedTerms.setText(TermPresentationConverter.getI18nMsgFoundedEntities(IPropertiesConfiguration.NO_ENTITY));
            return;
        }
        
        LoggerFacade.getDefault().debug(this.getClass(), "On action show all [Term]s from selected [Topic]"); // NOI18N

        // Which [Term]s should be loaded
        final Topic topic = cbFindTermsInTopics.getSelectionModel().getSelectedItem();
        final long topicId = topic.getId();
        
        // Load [Term]
        final ObservableList<Term> terms = FXCollections.observableArrayList();
        if (Objects.equals(topicId, DEFAULT_ID__TOPIC__SHOW_ALL_EXISTING_TERMS)) {
            terms.addAll(SqlProvider.getDefault().findAllTerms());
        }
        else if (Objects.equals(topicId, DEFAULT_ID__TOPIC__SHOW_ALL_TERMS_WITHOUT_PARENT)) {
            terms.addAll(SqlProvider.getDefault().findAllTermsInExerciseTermsWithoutParent());
        }
        else {
            terms.addAll(SqlProvider.getDefault().findAllTermsWithTopicId(topicId));
        }
        
        // Show them in gui
        lInfoFoundedTerms.setText(TermPresentationConverter.getI18nMsgFoundedEntities(terms.size()));
         
        lvFoundedTerms.getItems().clear();
        lvFoundedTerms.getItems().addAll(terms);
    }
    
    @Override
    public void register() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register in [ApplicationPresenter]"); // NOI18N
        
        this.registerOnActionOpenExercise();
        this.registerOnActionOpenTerm();
        this.registerOnActionRefreshNavigationTabTopics();
    }
    
    private void registerOnActionOpenExercise() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register on action open [Exercise]"); // NOI18N
        
        ActionHandlerFacade.getDefault().register(
                ACTION__APPLICATION__OPEN_EXERCISE,
                (ActionEvent event) -> {
                    final TransferData transferData = (TransferData) event.getSource();
                    final Optional<Long> exercseId = transferData.getLong();
                    if (exercseId.isPresent()) {
                        this.onActionOpenExerciseWithId(exercseId.get());
                    }
                });
    }
    
    private void registerOnActionOpenTerm() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register on action open [Term]"); // NOI18N
        
        ActionHandlerFacade.getDefault().register(
                ACTION__APPLICATION__OPEN_TERM,
                (ActionEvent event) -> {
                    final TransferData transferData = (TransferData) event.getSource();
                    final Optional<Long> entityId = transferData.getLong();
                    if (entityId.isPresent()) {
                        this.onActionOpenTermWithId(entityId.get());
                    }
                    // TODO select tab terms, select index from the topic in the combobox
                });
    }
    
    private void registerOnActionRefreshNavigationTabTopics() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register on action refresh [Navigation] tab [Topic]s"); // NOI18N
        
        ActionHandlerFacade.getDefault().register(
                ACTION__APPLICATION__REFRESH_NAVIGATION_TAB_TOPICS,
                (ActionEvent event) -> {
                    this.onActionRefreshNavigationTabTopics();
                });
    }
    
}

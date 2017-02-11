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
package com.github.naoghuman.abclist.view.application.navigation;

import com.github.naoghuman.abclist.configuration.IActionConfiguration;
import com.github.naoghuman.abclist.configuration.IDefaultConfiguration;
import com.github.naoghuman.abclist.model.Exercise;
import com.github.naoghuman.abclist.model.ModelProvider;
import com.github.naoghuman.abclist.model.NavigationEntity;
import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.abclist.model.Topic;
import com.github.naoghuman.abclist.sql.SqlProvider;
import com.github.naoghuman.abclist.view.application.converter.ExerciseNavigationConverter;
import com.github.naoghuman.abclist.view.application.converter.TopicNavigationConverter;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.IRegisterActions;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 *
 * @author Naoghuman
 */
public class NavigationProvider implements IActionConfiguration, IDefaultConfiguration, IRegisterActions {
    
    private static final Optional<NavigationProvider> instance = Optional.of(new NavigationProvider());

    public static final NavigationProvider getDefault() {
        return instance.get();
    }
    
    private NavigationProvider() {
        
    }
    
    private final TreeItem<NavigationEntity> rootItemNavigationTabTopics = new TreeItem<> ();
    
    private ComboBox<Topic> cbNavigationTopics;
    private Label lInfoFoundedTerms;
    private ListView<Term> lvNavigationTerms;
    private TreeView<NavigationEntity> tvNavigationTabTopics;
    
    public void initialize(
            TreeView<NavigationEntity> tvNavigationTabTopics, ComboBox<Topic> cbNavigationTopics,
            Label lInfoFoundedTerms, ListView<Term> lvNavigationTerms
    ) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [NavigationProvider]"); // NOI18N
        
        this.tvNavigationTabTopics = tvNavigationTabTopics;
        this.cbNavigationTopics = cbNavigationTopics;
        this.lInfoFoundedTerms = lInfoFoundedTerms;
        this.lvNavigationTerms = lvNavigationTerms;
        
        this.initializeNavigationTabTerms();
        this.initializeNavigationTabTopics();
        
        this.registerActions();
    }
    
    private void initializeNavigationTabTerms() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Navigation] tab [Term]s"); // NOI18N

        // ComboBox
        final Tooltip tooltip = new Tooltip("Show all Terms from the selected Topic"); // NOI18N
        cbNavigationTopics.setTooltip(tooltip);
        cbNavigationTopics.setDisable(true);
        
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

        cbNavigationTopics.setButtonCell((ListCell) callbackTopics.call(null));
        cbNavigationTopics.setCellFactory(callbackTopics);
        
        // ListView
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
        
        lvNavigationTerms.setCellFactory(callbackTerms);
        lvNavigationTerms.setOnMouseClicked(event -> {
            if (
                    event.getClickCount() == 2
                    && !lvNavigationTerms.getSelectionModel().isEmpty()
            ) {
                final TransferData transferData = new TransferData();
                transferData.setActionId(ACTION__APPLICATION__OPEN_TERM);
                
                final Term term = lvNavigationTerms.getSelectionModel().getSelectedItem();
                final long entityId = term.getId();
                transferData.setLong(entityId);
                
                ActionFacade.getDefault().handle(transferData);
            }
        });
    }
    
    private void initializeNavigationTabTopics() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Navigation] tab [Topic]s"); // NOI18N
        
        tvNavigationTabTopics.setCellFactory((TreeView<NavigationEntity> p) -> new NavigationListTreeCell());
    }
    
    private String getInfoFoundedTerms(int foundedTerms) {
        final StringBuilder sb = new StringBuilder();
        sb.append("Found "); // NOI18N
        sb.append(foundedTerms);
        sb.append(" Terms"); // NOI18N
        
        return sb.toString();
    }
    
//    public void onAcitonExpandAllOpen
    
//    public void onActionExpandTopic(Topic topic) {
//        LoggerFacade.getDefault().debug(this.getClass(), "On action expand [Topic]"); // NOI18N
//    
//        final Optional<TreeItem<NavigationEntity>> optionalTreeItem = rootItemNavigationTabTopics.getChildren().stream()
//                .filter(treeItem -> ((NavigationEntity) treeItem.getValue()).getNavigation().getNavigationType().equals(ENavigationType.TOPIC))
//                .filter(treeItem -> Objects.equals(((NavigationEntity) treeItem.getValue()).getNavigation().getEntityId(), topic.getId()))
//                .findFirst();
//        if (optionalTreeItem.isPresent()) {
//            optionalTreeItem.get().setExpanded(true);
//        }
//    }
    
    public void onActionRefreshNavigationTabTerms(ObservableList<Topic> topics) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action refresh [Navigation] tab [Term]s"); // NOI18N

        // Reload the [ComboBox]
        // TODO need selected object / have a look if always exists
//        final boolean isAnyIndexSelected = !cbNavigationTopics.getSelectionModel().isEmpty();
//        final int selectedIndex = cbNavigationTopics.getSelectionModel().getSelectedIndex();
        cbNavigationTopics.getItems().clear();
        
        final Topic topicShowAllExistingTerms = ModelProvider.getDefault().getTopic(
                DEFAULT_ID__TOPIC__SHOW_ALL_EXISTING_TERMS,
                "=== Show all existing Terms ==="); // NOI18N
        topics.add(0, topicShowAllExistingTerms);
        
        final Topic topicShowAllTermsWithoutExercise = ModelProvider.getDefault().getTopic(
                DEFAULT_ID__TOPIC__SHOW_ALL_TERMS_WITHOUT_PARENT,
                "=== Show all Terms without Parent ==="); // NOI18N
        topics.add(1, topicShowAllTermsWithoutExercise);
        
        cbNavigationTopics.getItems().addAll(topics);
        cbNavigationTopics.setDisable(topics.size() <= 1);
        
//        if (isAnyIndexSelected) {
//            // How to avoid the [Selection Event]?
//            cbNavigationTopics.getSelectionModel().select(selectedIndex);
//        }
    }

    private void onActionRefreshNavigationTabTerms(Term term) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action refresh [Navigation] tab [Terms]"); // NOI18N
        
        final int selectedIndex = lvNavigationTerms.getSelectionModel().getSelectedIndex();
        lvNavigationTerms.getItems().remove(selectedIndex);
        lvNavigationTerms.getItems().add(selectedIndex, term);
        lvNavigationTerms.getSelectionModel().clearAndSelect(selectedIndex);
    }

    public void onActionRefreshNavigationTabTopics(ObservableList<Topic> topics) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action refresh [Navigation] tab [Topic]s"); // NOI18N
        
        // Catch expanded [TreeItem]s
        final List<NavigationEntity> navigationEntities = new ArrayList<>();
        rootItemNavigationTabTopics.getChildren().stream()
                .filter((treeItem) -> treeItem.isExpanded())
                .forEach((treeItem) -> {
                    navigationEntities.add(treeItem.getValue());
                });
        
        // Refresh the [TreeView]
        rootItemNavigationTabTopics.getChildren().clear();
        
        topics.forEach(topic -> {
            final ObservableList<Exercise> observableListExercises = SqlProvider.getDefault().findAllExercisesWithTopicId(topic.getId());
            topic.setExercises(observableListExercises.size());
            
            final NavigationEntity navigationEntity = ModelProvider.getDefault().getNavigationEntity(ENavigationType.TOPIC, topic.getId(), new TopicNavigationConverter(topic));
            final TreeItem<NavigationEntity> treeItemTopic = new TreeItem<>(navigationEntity);
            observableListExercises.forEach(exercise -> {
                final NavigationEntity navigationEntity2 = ModelProvider.getDefault().getNavigationEntity(ENavigationType.EXERCISE, exercise.getId(), new ExerciseNavigationConverter(exercise));
                final TreeItem<NavigationEntity> treeItemExercise = new TreeItem<>(navigationEntity2);
                treeItemTopic.getChildren().add(treeItemExercise);
            });
            
            rootItemNavigationTabTopics.getChildren().add(treeItemTopic);
        });
        
        tvNavigationTabTopics.setRoot(rootItemNavigationTabTopics);
        
        // Expanded previous [TreeItem]s
        Platform.runLater(() -> {
            navigationEntities.stream().forEach((navigationEntity) -> {
                rootItemNavigationTabTopics.getChildren().stream()
                        .filter((treeItem) -> (Objects.equals(((NavigationEntity) treeItem.getValue()).getNavigation().getEntityId(), navigationEntity.getNavigation().getEntityId())))
                        .forEach((treeItem) -> {
                            treeItem.setExpanded(true);
                        });
            });
        });
    }
    
    public void onActionSelectPreviousSelectedIndex() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action select previous [SelectedIndex]"); // NOI18N
        
        final int selectedIndex = cbNavigationTopics.getSelectionModel().getSelectedIndex();
        cbNavigationTopics.getSelectionModel().clearSelection();
        cbNavigationTopics.getSelectionModel().select(selectedIndex);
    }
    
    public void onActionShowTermsFromSelectedTopic() {
        // Is any [Topic] in the [ComboBox] selected?
        if (cbNavigationTopics.getSelectionModel().isEmpty()) {
            lInfoFoundedTerms.setText(this.getInfoFoundedTerms(0));
            return;
        }
        
        LoggerFacade.getDefault().debug(this.getClass(), "On action show [Terms]s from selected [Topic]"); // NOI18N

        // Catch which [Term] should be loaded
        final ObservableList<Term> terms = FXCollections.observableArrayList();
        final Topic topic = cbNavigationTopics.getSelectionModel().getSelectedItem();
        
        final long topicId = topic.getId();
        if (Objects.equals(topicId, DEFAULT_ID__TOPIC__SHOW_ALL_EXISTING_TERMS)) {
            terms.addAll(SqlProvider.getDefault().findAllTerms());
        }
        else if (Objects.equals(topicId, DEFAULT_ID__TOPIC__SHOW_ALL_TERMS_WITHOUT_PARENT)) {
            terms.addAll(SqlProvider.getDefault().findAllTermsInExerciseTermWithoutParent());
        }
        else {
            terms.addAll(SqlProvider.getDefault().findAllTermsWithTopicId(topicId));
        }
        
        // Show them in the gui
        lInfoFoundedTerms.setText(this.getInfoFoundedTerms(terms.size()));
        lvNavigationTerms.getItems().clear();
        lvNavigationTerms.getItems().addAll(terms);
    }

    @Override
    public void registerActions() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register actions in [NavigationProvider]"); // NOI18N
        
        this.registerOnActionRefreshNavigationTabTermsWithSelection();
        this.registerOnActionRefreshNavigationTabTopics();
    }

    private void registerOnActionRefreshNavigationTabTermsWithSelection() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register on action refresh [Navigation] tab [Terms] with selection"); // NOI18N
        
        ActionFacade.getDefault().register(
                ACTION__APPLICATION__REFRESH_NAVIGATION_TAB_TERMS_WITH_SELECTION,
                (ActionEvent event) -> {
                    final TransferData transferData = (TransferData) event.getSource();
                    final Term term = (Term) transferData.getObject();
                    this.onActionRefreshNavigationTabTerms(term);
                });
    }
    
    private void registerOnActionRefreshNavigationTabTopics() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register on action refresh [Navigation] tab [Topics]"); // NOI18N
        
        ActionFacade.getDefault().register(
                ACTION__APPLICATION__REFRESH_NAVIGATION_TAB_TOPICS,
                (ActionEvent event) -> {
                    final ObservableList<Topic> topics = SqlProvider.getDefault().findAllTopics();
                    this.onActionRefreshNavigationTabTopics(topics);//topic);
                });
    }
    
}

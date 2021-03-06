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
package com.github.naoghuman.abclist.view.exercise;

import com.github.naoghuman.abclist.view.components.signflowpane.CharacterExtractor;
import com.github.naoghuman.abclist.view.components.signflowpane.ESign;
import com.github.naoghuman.abclist.view.components.signflowpane.SignFlowPane;
import com.github.naoghuman.abclist.configuration.IActionConfiguration;
import com.github.naoghuman.abclist.configuration.IExerciseConfiguration;
import com.github.naoghuman.abclist.model.Exercise;
import com.github.naoghuman.abclist.model.ExerciseTerm;
import com.github.naoghuman.abclist.model.ModelProvider;
import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.abclist.sql.SqlProvider;
import com.github.naoghuman.lib.action.core.ActionHandlerFacade;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * 
 * @author Naoghuman
 */
public class ExercisePresenter implements Initializable, IActionConfiguration, IExerciseConfiguration {
    
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("mm:ss"); // NOI18N
    
    private final ObservableMap<Character, SignFlowPane> signFlowPanes = FXCollections.observableHashMap();
    private final PauseTransition ptExerciseTimer = new PauseTransition();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // NOI18N
    
    @FXML private Button bPauseExercise;
    @FXML private Button bStartExercise;
    @FXML private Button bStopExercise;
    @FXML private ComboBox<ETime> cbTimeChooser;
    @FXML private Label lCounterTerms;
    @FXML private Label lCounterTime;
    @FXML private Label lGenerationTime;
    @FXML private TextField tfUserInput;
    @FXML private VBox vbSignFlowPanesEven;
    @FXML private VBox vbSignFlowPanesUneven;
    
    private int counterTerms = 0;
    private int exerciseTime = 0;
    
    private Exercise exercise;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ExercisePresenter"); // NOI18N
        
        this.initializeBindings();
        this.initializeComboBoxTimeChooser();
        this.initializeExerciseTimer();
        this.initializeFlowPaneTerms();
        this.initializeTextFieldUserInput();
        
        this.onActionPrepareExerciseFor(EState.PREPARE_STATE_FOR__INITIALIZE);
    }
    
    private void initializeBindings() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Bindings]"); // NOI18N
        
        bPauseExercise.disableProperty().bind(StateFacade.getDefault().disablePauseButtonProperty());
        bStartExercise.disableProperty().bind(StateFacade.getDefault().disableStartButtonProperty());
        bStopExercise.disableProperty().bind(StateFacade.getDefault().disableStopButtonProperty());
        cbTimeChooser.disableProperty().bind(StateFacade.getDefault().disableTimeChooserComboBoxProperty());
        tfUserInput.disableProperty().bind(StateFacade.getDefault().disableUserInputTextFieldProperty());
    }
    
    private void initializeComboBoxTimeChooser() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [ComboBox] [TimeChooser]"); // NOI18N
        
        cbTimeChooser.setCellFactory((ListView<ETime> listview) -> new ListCell<ETime>() {
            @Override
            public void updateItem(ETime time, boolean empty) {
                super.updateItem(time, empty);
                this.setGraphic(null);
                this.setText(!empty ? time.toString() : null);
            }
        });
        
        final ObservableList<ETime> observableListTimes = FXCollections.observableArrayList();
        observableListTimes.addAll(ETime.values());
        cbTimeChooser.getItems().addAll(observableListTimes);
        cbTimeChooser.getSelectionModel().selectFirst();
    }
    
    private void initializeExerciseTimer() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [Exercise] timer"); // NOI18N
        
        ptExerciseTimer.setAutoReverse(false);
        ptExerciseTimer.setDelay(Duration.millis(125.0d));
        ptExerciseTimer.setDuration(Duration.seconds(1.0d));
        ptExerciseTimer.setOnFinished(value -> {
            --exerciseTime;
            this.onActionShowTime(exerciseTime);
            
            if (exerciseTime > 0) {
                ptExerciseTimer.playFromStart();
            }
            else {
                this.onActionExerciseIsReady();
            }
        });
    }
    
    private void initializeFlowPaneTerms() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [FlowPane] [Term]s"); // NOI18N
        
        int index = 0;
        for (ESign sign : ESign.values()) {
            final SignFlowPane signFlowPane = new SignFlowPane();
            signFlowPane.configure(sign);
            signFlowPanes.put(sign.getSign(), signFlowPane);
            
            if (index % 2 == 0) {
                System.out.println("-> even: " + sign.name()); // XXX remove
                vbSignFlowPanesEven.getChildren().add(signFlowPane);
            }
            else {
                vbSignFlowPanesUneven.getChildren().add(signFlowPane);
                System.out.println("     -> uneven: " + sign.name()); // XXX remove
            }
            ++index;
        }
        
        counterTerms = 0;
        lCounterTerms.setText("Terms: " + counterTerms); // NOI18N
    }
    
    private void initializeTextFieldUserInput() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize [TextField] [UserInput]"); // NOI18N
        
        tfUserInput.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                this.onActionUserPressEnter();
            }
        });
    }
    
    public void configure(Exercise exercise) {
        LoggerFacade.getDefault().debug(this.getClass(), "Configure"); // NOI18N
        
        this.exercise = exercise;
        
        cbTimeChooser.getSelectionModel().select(ETime.getTime(exercise.getChoosenTime()));
        
        lGenerationTime.setText(simpleDateFormat.format(new Date(exercise.getGenerationTime())));
        
        if (exercise.isReady()) {
            this.onActionPrepareExerciseFor(EState.PREPARE_STATE_FOR__IS_READY);
            this.onActionLoadAllTerms();
            this.onActionCountTerms();
        }
    }
    
    public long getId() {
        return exercise.getId();
    }
    
//    private FlowPane getFlowPane(char firstChar) {
//        FlowPane flowPane = new FlowPane();
//        for (FlowPane flowPane2 : flowPaneTerms) {
//            if (flowPane2.getId().toLowerCase().charAt(0) == firstChar) {
//                flowPane = flowPane2;
//            }
//        }
//        
//        return flowPane;
//    }
    
//    private Label getLabel(Term term) {
//        // Check in db if isMarkAsWrong
//        final boolean isMarkAsWrong = SqlProvider.getDefault().isExerciseTermMarkAsWrong(exercise.getId(), term.getId());
//        
//        // Create the label
//        final Label label = new Label(term.getTitle());
//        label.setUserData(term); // TODO tweak it - own component
//        label.setStyle(
//                "-fx-background-color:"
//                + (isMarkAsWrong ? "ORANGERED;" : "LIGHTGREEN;")); // NOI18N
//        
//        final Font font = label.getFont();
//        label.setFont(Font.font(
//                font.getName(),
//                (isMarkAsWrong ? FontPosture.ITALIC : FontPosture.REGULAR),
//                font.getSize()));
//        label.setOnMouseClicked(event -> {
//            if (event.getClickCount() == 2) {
//                final TransferData transferData = new TransferData();
//                transferData.setActionId(ACTION__APPLICATION__OPEN_TERM);
//                transferData.setObject(term);
//                
//                ActionFacade.getDefault().handle(transferData);
//            }
//        });
//        
//        // Create [ContextMenu]
//        final ContextMenu cm = new ContextMenu();
//        final Optional<ExerciseTerm> optional = SqlProvider.getDefault().findExerciseTerm(exercise.getId(), term.getId());
//        if (isMarkAsWrong) {
//            final MenuItem mi2 = new MenuItem("Mark as right"); // NOI18N
//            mi2.setOnAction((ActionEvent event) -> {
//                if (optional.isPresent()) {
//                    final ExerciseTerm exerciseTerm = optional.get();
//                    exerciseTerm.setMarkAsWrong(false);
//                    SqlProvider.getDefault().updateExerciseTerm(exerciseTerm);
//                    
//                    // Refresh flowpane
//                    // TODO Reload only the relevant [FlowPane]
//                    this.onActionResetFlowPanes();
//                    this.onActionLoadAllTerms();
//                    this.onActionCountTerms();
//                }
//            });
//            cm.getItems().add(mi2);
//        }
//        else {
//            final MenuItem mi = new MenuItem("Mark as wrong"); // NOI18N
//            mi.setOnAction((ActionEvent event) -> {
//                if (optional.isPresent()) {
//                    final ExerciseTerm exerciseTerm = optional.get();
//                    exerciseTerm.setMarkAsWrong(true);
//                    SqlProvider.getDefault().updateExerciseTerm(exerciseTerm);
//                    
//                    // Refresh flowpane
//                    // TODO Reload only the relevant [FlowPane]
//                    this.onActionResetFlowPanes();
//                    this.onActionLoadAllTerms();
//                    this.onActionCountTerms();
//                }
//            });
//            cm.getItems().add(mi);
//        }
//
//        label.setContextMenu(cm);
//        
//        return label;
//    }
    
    private void onActionAddTerm(Term term) {
        final char firstChar = CharacterExtractor.getDefault().computeFirstChar(term.getTitle());
        final SignFlowPane signFlowPane = signFlowPanes.get(firstChar);
        signFlowPane.onActionAddTerm(term);
    }
    
    private void onActionCountTerms() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action count [Term]s"); // NOI18N
        
        counterTerms = 0;
        signFlowPanes.forEach((character, signFlowPane) -> {
            counterTerms += signFlowPane.size();
        });
        
        lCounterTerms.setText("Terms: " + counterTerms);
    }
    
    private void onActionExerciseIsReady() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action [Exercise] is ready"); // NOI18N

        // Save new state
        exercise.setReady(true);
        SqlProvider.getDefault().updateExercise(exercise);
        
        // Reflect the new state in the gui
        this.onActionPrepareExerciseFor(EState.PREPARE_STATE_FOR__IS_READY);
        ActionHandlerFacade.getDefault().handle(ACTION__APPLICATION__REFRESH_NAVIGATION_TAB_TOPICS);
    }
    
    private void onActionLoadAllTerms() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action load all [Term]s"); // NOI18N

        // Compute all [Term] from this [Exercise]
        final ObservableList<ExerciseTerm> exerciseTerms = SqlProvider.getDefault().findAllExerciseTermsWithExerciseId(exercise.getId());
        final ObservableList<Term> terms = SqlProvider.getDefault().findAllTermsInExerciseTerms(exerciseTerms);
        terms.stream()
                .forEach(term -> {
                    this.onActionAddTerm(term);
                });
    }
    
    private void onActionResetFlowPanes() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action reset [FlowPane]s"); // NOI18N
        
        signFlowPanes.forEach((character, signFlowPane) -> {
            signFlowPane.clear();
        });
    }

    private void onActionPrepareExerciseFor(EState state) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action prepare [Exercise] for: " + state.name()); // NOI18N
        
        // Swith to the new state
        StateFacade.getDefault().setDisablePauseButton(state.isDisablePauseButton());
        StateFacade.getDefault().setDisableStartButton(state.isDisableStartButton());
        StateFacade.getDefault().setDisableStopButton(state.isDisableStopButton());
        StateFacade.getDefault().setDisableTimeChooserComboBox(state.isDisableTimeChooserComboBox());
        StateFacade.getDefault().setDisableUserInputTextField(state.isDisableUserInputTextField());
        
        // Do extra stuff
        switch (state) {
            case PREPARE_STATE_FOR__INITIALIZE:
            case PREPARE_STATE_FOR__IS_READY: {
                lCounterTime.setText("00:00"); // NOI18N
                tfUserInput.setText(null);
                exerciseTime = 0;
                break;
            }
            case PREPARE_STATE_FOR__IS_STARTED: {
                final ETime time = cbTimeChooser.getSelectionModel().getSelectedItem();
                lCounterTime.setText(time.toString());
                exerciseTime = time.getSeconds();
                
                Platform.runLater(() -> {
                    tfUserInput.requestFocus();
                });
                break;
            }
            case PREPARE_STATE_FOR__SHOULD_PAUSE: {
                /* do nothing extra */
                break;
            }
            case PREPARE_STATE_FOR__START_AGAIN: {
                Platform.runLater(() -> {
                    tfUserInput.requestFocus();
                });
                break;
            }
        }
    }
    
    private void onActionShowTime(int _exerciseTime) {
//        LoggerFacade.getDefault().debug(this.getClass(), "On action show Time: " + _exerciseTime); // NOI18N
        
        final String formattedTime = SIMPLE_DATE_FORMAT.format(_exerciseTime * 1000);
        lCounterTime.setText(formattedTime);
    }
    
    public void onActionUserChooseTime() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action [User] choose time"); // NOI18N
        
        exercise.setChoosenTime(cbTimeChooser.getSelectionModel().getSelectedItem().toString());
        
        SqlProvider.getDefault().updateExercise(exercise);
    }
    
    public void onActionUserPauseExercise() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action [User] pause [Exercise]"); // NOI18N
        
        if (ptExerciseTimer.getStatus().equals(Animation.Status.RUNNING)) {
            this.onActionPrepareExerciseFor(EState.PREPARE_STATE_FOR__SHOULD_PAUSE);
            ptExerciseTimer.pause();
        }
    }
    
    public void onActionUserPressEnter() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action [User] press [Enter]"); // NOI18N
        
        // Catch [UserInput]
        final String userInput = tfUserInput.getText().trim();
        if (userInput.isEmpty()) {
            LoggerFacade.getDefault().warn(this.getClass(), "Empty User input - not a valid [Term]"); // NOI18N
            return;
        }
        
        this.onActionUserTypedTerm(userInput);
    }
    
    public void onActionUserStartExercise() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action [User] start [Exercise]"); // NOI18N
        
        if (ptExerciseTimer.getStatus().equals(Animation.Status.PAUSED)) {
            this.onActionPrepareExerciseFor(EState.PREPARE_STATE_FOR__START_AGAIN);
            ptExerciseTimer.play();
        }
        else {
            this.onActionPrepareExerciseFor(EState.PREPARE_STATE_FOR__IS_STARTED);
            ptExerciseTimer.playFromStart();
        }
    }
    
    public void onActionUserStopExercise() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action [User] stop [Exercise]"); // NOI18N

        // Stop timer
        if (ptExerciseTimer.getStatus().equals(Animation.Status.RUNNING)) {
            ptExerciseTimer.stop();
        }
        
        this.onActionExerciseIsReady();
    }
    
    private void onActionUserTypedTerm(String userInput) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action [User] typed [Term]"); // NOI18N
        
        // Check if the [Term] with the [title] in the [Database] exists
        final Term term = ModelProvider.getDefault().getTerm(userInput);
        final ObservableList<Term> observableListTerms = SqlProvider.getDefault().findAllTermsWithTitle(userInput);
        if (observableListTerms.isEmpty()) {
            SqlProvider.getDefault().createTerm(term);
        } else {
            term.copy(observableListTerms.get(0));
        }
        
        // Check if the [Term] is associated with the [Exercise]
        final ObservableList<ExerciseTerm> exerciseTerms = SqlProvider.getDefault().findAllExerciseTermsWithExerciseId(exercise.getId());
        boolean isExerciseTermExists = false;
        for (ExerciseTerm exerciseTerm : exerciseTerms) {
            if (
                    Objects.equals(exerciseTerm.getExerciseId(), exercise.getId())
                    && Objects.equals(exerciseTerm.getTermId(), term.getId())
            ) {
                isExerciseTermExists = true;
                break;
            }
        }
        
        if (!isExerciseTermExists) {
            final ExerciseTerm exerciseTerm = ModelProvider.getDefault().getExerciseTerm(exercise.getId(), term.getId());
            SqlProvider.getDefault().createExerciseTerm(exerciseTerm);
            
            LoggerFacade.getDefault().debug(this.getClass(), "  # " + exerciseTerm.toString());
        }
        
        // Show the [Term] in the [FlowPane]
        this.onActionAddTerm(term);
        this.onActionCountTerms();
        
        // Refresh [TextField]
        Platform.runLater(() -> {
            tfUserInput.setText(null);
            tfUserInput.requestFocus();
        });
    }

}

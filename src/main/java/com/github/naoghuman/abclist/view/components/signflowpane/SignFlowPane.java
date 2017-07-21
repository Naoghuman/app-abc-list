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
package com.github.naoghuman.abclist.view.components.signflowpane;

import com.github.naoghuman.abclist.model.Term;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.util.Comparator;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * TODO move css to file.css
 *  - http://stackoverflow.com/questions/27712213/how-do-i-make-a-simple-solid-border-around-a-flowpane-in-javafx
 *
 * @author Naoghuman
 */
public class SignFlowPane extends HBox {
    
    private static final Comparator<Node> TERM_COMPARATOR = (Node node1, Node node2) -> {
        int compare = 0;
        if (
                node1 instanceof Label
                && node2 instanceof Label
                && node1.getUserData() instanceof Term
                && node2.getUserData() instanceof Term
        ) {
            final Term term1 = (Term) node1.getUserData();
            final Term term2 = (Term) node2.getUserData();
            compare = term1.getTitle().compareTo(term2.getTitle());
        }
        
        return compare;
    };
    private static final Insets DEFAULT_PADDING = new Insets(7.0d);
    
    private FlowPane fpTerms;
    private Label lSign;
    private ScaleTransition scaleTransition;
    
    private ESign sign;
    
    public SignFlowPane() {
        this.initialize();
    }
    
    private void initialize() {
        LoggerFacade.getDefault().trace(this.getClass(), "Initialize SignFlowPane"); // NOI18N
        
        this.initializeHBox();
        this.initializeFlowPane();
        this.initializeLabel();
        this.initializeScaleTransition();
    }
    
    private void initializeHBox() {
        LoggerFacade.getDefault().trace(this.getClass(), "Initialize HBox"); // NOI18N
        
        super.setPadding(DEFAULT_PADDING);
        super.setSpacing(7.0d);
        super.setStyle("-fx-background-color:PALETURQUOISE; -fx-border-color: POWDERBLUE"); // NOI18N
        
        DropShadow dropShadow = new DropShadow(3.0d, 2.0d, 2.0d, Color.rgb(0, 0, 0, 0.6d));
        super.setEffect(dropShadow);
        
        super.setOnMouseEntered((e) -> {
            this.onActionScaleToExpandedMode();
        });
        super.setOnMouseExited((e) -> {
            this.onActionScaleToNormalMode();
        });
    }
    
    private void initializeFlowPane() {
        LoggerFacade.getDefault().trace(this.getClass(), "Initialize FlowPane"); // NOI18N
        
        fpTerms = new FlowPane(7.0d, 7.0d);
        fpTerms.setPadding(DEFAULT_PADDING);
        fpTerms.setStyle("-fx-background-color:POWDERBLUE"); // NOI18N
        HBox.setHgrow(fpTerms, Priority.ALWAYS);
        
        super.getChildren().add(fpTerms);
    }
    
    private void initializeLabel() {
        LoggerFacade.getDefault().trace(this.getClass(), "Initialize Label"); // NOI18N
        
        lSign = new Label();
        lSign.setPadding(DEFAULT_PADDING);
        lSign.setMinWidth(26.0d);
        lSign.setStyle("-fx-background-color:POWDERBLUE"); // NOI18N
        lSign.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                final Term term = new Term();
                term.setTitle("" + System.currentTimeMillis()); // NOI18N
                this.onActionAddTerm(term);
            }
        });
        
        super.getChildren().add(0, lSign);
    }
    
    private void initializeScaleTransition() {
        LoggerFacade.getDefault().trace(this.getClass(), "Initialize ScaleTransition"); // NOI18N
        
        scaleTransition = new ScaleTransition(Duration.millis(64.0d), this);
    }
    
    public void clear() {
        fpTerms.getChildren().clear();
    }
    
    public void configure(ESign sign) {
        LoggerFacade.getDefault().debug(this.getClass(), "configure(ESign)"); // NOI18N
        
        this.configure(sign, FXCollections.observableArrayList());
    }
    
    public void configure(ESign sign, List<Term> terms) {
        LoggerFacade.getDefault().debug(this.getClass(), "configure(ESign, List<Term>)"); // NOI18N
        
        this.sign = sign;
        lSign.setText(this.sign.name());
        
        if (terms.isEmpty()) {
            return;
        }
        
        terms.stream()
                .forEach(term -> {
                    this.onActionAddTerm(term);
                });
    }
    
    public int size() {
        return fpTerms.getChildren().size();
    }
    
    public boolean isSign(char firstChar) {
        return sign.isSign(firstChar);
    }
    
    private boolean isTermAdded(Term term) { // XXX lambda
        boolean isTermAdded = Boolean.FALSE;
        for (Node node : fpTerms.getChildren()) {
            if (node instanceof Label) {
                final Label label = (Label) node;
                if (label.getUserData() instanceof Term) {
                    final Term addedTerm = (Term) label.getUserData();
                    if (addedTerm.getTitle().equals(term.getTitle())) {
                        isTermAdded = true;
                        break;
                    }
                }
            }
        }
        
        return isTermAdded;
    }

    public void onActionAddTerm(Term term) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action add [Term=" + term.getTitle() + "]"); // NOI18N
        
        final boolean isTermAdded = this.isTermAdded(term);
        if (!isTermAdded) {
            final Label label = new Label(term.getTitle());
            label.setUserData(term); // TODO tweak it - own component
            fpTerms.getChildren().add(label);
            
            if (fpTerms.getChildren().size() > 1) {
                FXCollections.sort(fpTerms.getChildren(), TERM_COMPARATOR);
            }
        }
    }

    private void onActionScaleToExpandedMode() {
        LoggerFacade.getDefault().trace(this.getClass(), "On action scale to expanded mode"); // NOI18N
        
        if (
                scaleTransition != null
                && scaleTransition.getStatus().equals(Animation.Status.RUNNING)
        ) {
            scaleTransition.stop();
        }
        
        scaleTransition.setFromX(this.getScaleX());
        scaleTransition.setFromY(this.getScaleY());
        scaleTransition.setToX(1.015d);
        scaleTransition.setToY(1.01d);
        
        scaleTransition.playFromStart();
    }

    private void onActionScaleToNormalMode() {
        LoggerFacade.getDefault().trace(this.getClass(), "On action scale to normal mode"); // NOI18N
        
        if (
                scaleTransition != null
                && scaleTransition.getStatus().equals(Animation.Status.RUNNING)
        ) {
            scaleTransition.stop();
        }

        scaleTransition.setFromX(this.getScaleX());
        scaleTransition.setFromY(this.getScaleY());
        scaleTransition.setToX(1.0d);
        scaleTransition.setToY(1.0d);
        
        scaleTransition.playFromStart();
    }
    
}

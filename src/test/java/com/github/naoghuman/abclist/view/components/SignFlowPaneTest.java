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
package com.github.naoghuman.abclist.view.components;

import com.github.naoghuman.abclist.view.components.signflowpane.ESign;
import com.github.naoghuman.abclist.view.components.signflowpane.SignFlowPane;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Naoghuman
 */
public class SignFlowPaneTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final VBox vb = new VBox(7.0d);
        vb.setPadding(new Insets(20));
        
        final SignFlowPane signFlowPaneA = new SignFlowPane();
        signFlowPaneA.configure(ESign.A);
        vb.getChildren().add(signFlowPaneA);
        
        final SignFlowPane signFlowPaneB = new SignFlowPane();
        signFlowPaneB.configure(ESign.B);
        vb.getChildren().add(signFlowPaneB);
        
        final SignFlowPane signFlowPaneC = new SignFlowPane();
        signFlowPaneC.configure(ESign.C);
        vb.getChildren().add(signFlowPaneC);
        
        Scene myScene = new Scene(vb);

//        String stylesheet = getClass().getResource("test.css").toExternalForm();
        primaryStage.setScene(myScene);
        primaryStage.setWidth(300);
        primaryStage.setHeight(200);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

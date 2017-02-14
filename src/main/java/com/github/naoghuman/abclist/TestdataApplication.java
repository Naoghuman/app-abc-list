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
package com.github.naoghuman.abclist;

import com.airhacks.afterburner.injection.Injector;
import com.github.naoghuman.abclist.configuration.ITestdataConfiguration;
import com.github.naoghuman.abclist.testdata.TestdataFacade;
import com.github.naoghuman.lib.database.api.DatabaseFacade;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.preferences.api.PreferencesFacade;
import com.github.naoghuman.lib.properties.api.PropertiesFacade;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author PRo
 */
public class TestdataApplication extends Application implements ITestdataConfiguration {
    
    @Override
    public void init() throws Exception {
        PropertiesFacade.getDefault().register(DBW__RESOURCE_BUNDLE__TESTDATA);
        
        final char borderSign = this.getProperty(KEY__APPLICATION_TESTDATA__BORDER_SIGN).charAt(0);
        final String message = this.getProperty(KEY__APPLICATION_TESTDATA__MESSAGE_START);
        final String title = this.getProperty(KEY__APPLICATION_TESTDATA__TITLE);
        LoggerFacade.getDefault().message(borderSign, 80, message + title);
        
        final Boolean dropPreferencesFileAtStart = Boolean.FALSE;
        PreferencesFacade.getDefault().init(dropPreferencesFileAtStart);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Scene scene = new Scene(TestdataFacade.getDefault().getView(), 1280.0d, 720.0d);
        primaryStage.setTitle(this.getProperty(KEY__APPLICATION_TESTDATA__TITLE) + this.getProperty(KEY__APPLICATION_TESTDATA__VERSION));
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
           we.consume();
           
           this.onCloseRequest();
        });
        
        primaryStage.show();
    }
    
    private String getProperty(String propertyKey) {
        return PropertiesFacade.getDefault().getProperty(DBW__RESOURCE_BUNDLE__TESTDATA, propertyKey);
    }
    
    private void onCloseRequest() {
        final char borderSign = this.getProperty(KEY__APPLICATION_TESTDATA__BORDER_SIGN).charAt(0);
        final String message = this.getProperty(KEY__APPLICATION_TESTDATA__MESSAGE_STOP);
        final String title = this.getProperty(KEY__APPLICATION_TESTDATA__TITLE);
        LoggerFacade.getDefault().message(borderSign, 80, message + title);
        
        try {
            TestdataFacade.getDefault().shutdown();
        } catch (InterruptedException e) {
        }
        
        Injector.forgetAll();
        DatabaseFacade.getDefault().shutdown();
        
        final PauseTransition pt = new PauseTransition(DBW__LITTLE_DELAY__DURATION_125);
        pt.setOnFinished((ActionEvent event) -> {
            Platform.exit();
        });
        pt.playFromStart();
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

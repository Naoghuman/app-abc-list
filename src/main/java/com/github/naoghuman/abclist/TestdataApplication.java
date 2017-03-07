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
import com.github.naoghuman.abclist.configuration.IPropertiesConfiguration;
import com.github.naoghuman.abclist.i18n.Properties;
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
public class TestdataApplication extends Application implements IPropertiesConfiguration {
    
    @Override
    public void init() throws Exception {
        PropertiesFacade.getDefault().register(KEY__TESTDATA__RESOURCE_BUNDLE);
        
        final char borderSign = Properties.getPropertyForTestdataApplication(KEY__TESTDATA_APPLICATION__BORDER_SIGN).charAt(0);
        final String message = Properties.getPropertyForTestdataApplication(KEY__TESTDATA_APPLICATION__MESSAGE_START);
        final String title = Properties.getPropertyForTestdataApplication(KEY__TESTDATA_APPLICATION__TITLE);
        LoggerFacade.getDefault().message(borderSign, 80, message + title);
        
        final Boolean dropPreferencesFileAtStart = Boolean.FALSE;
        PreferencesFacade.getDefault().init(dropPreferencesFileAtStart);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Scene scene = new Scene(TestdataFacade.getDefault().getView(), 1280.0d, 720.0d);
        primaryStage.setTitle(Properties.getPropertyForTestdataApplication(KEY__TESTDATA_APPLICATION__TITLE)
                + Properties.getPropertyForTestdataApplication(KEY__TESTDATA_APPLICATION__VERSION));
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
           we.consume();
           
           this.onCloseRequest();
        });
        
        primaryStage.show();
    }
    
    private void onCloseRequest() {
        final char borderSign = Properties.getPropertyForTestdataApplication(KEY__TESTDATA_APPLICATION__BORDER_SIGN).charAt(0);
        final String message = Properties.getPropertyForTestdataApplication(KEY__TESTDATA_APPLICATION__MESSAGE_STOP);
        final String title = Properties.getPropertyForTestdataApplication(KEY__TESTDATA_APPLICATION__TITLE);
        LoggerFacade.getDefault().message(borderSign, 80, message + title);
        
        try {
            TestdataFacade.getDefault().shutdown();
        } catch (InterruptedException e) {
        }
        
        Injector.forgetAll();
        DatabaseFacade.getDefault().shutdown();
        
        final PauseTransition pt = new PauseTransition(LITTLE_DELAY__DURATION_125);
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

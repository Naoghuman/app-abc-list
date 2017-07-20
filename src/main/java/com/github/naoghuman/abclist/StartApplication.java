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
package com.github.naoghuman.abclist;


import com.airhacks.afterburner.injection.Injector;
import com.github.naoghuman.abclist.view.application.ApplicationView;
import com.github.naoghuman.abclist.configuration.IPropertiesConfiguration;
import com.github.naoghuman.abclist.i18n.Properties;
import com.github.naoghuman.lib.database.core.DatabaseFacade;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import com.github.naoghuman.lib.preferences.core.PreferencesFacade;
import com.github.naoghuman.lib.properties.core.PropertiesFacade;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import static javafx.application.Application.launch;

/**
 *
 * @author Naoghuman
 */
public class StartApplication extends Application implements IPropertiesConfiguration {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        
        PropertiesFacade.getDefault().register(KEY__APPLICATION__RESOURCE_BUNDLE);
        PropertiesFacade.getDefault().register(KEY__CONVERTER__RESOURCE_BUNDLE);
        
        final char borderSign = Properties.getPropertyForApplication(KEY__APPLICATION__BORDER_SIGN).charAt(0);
        final String message = Properties.getPropertyForApplication(KEY__APPLICATION__MESSAGE_START);
        final String title = Properties.getPropertyForApplication(KEY__APPLICATION__TITLE)
                + Properties.getPropertyForApplication(KEY__APPLICATION__VERSION);
        LoggerFacade.getDefault().message(borderSign, 80, String.format(message, title));
        
        final Boolean dropPreferencesFileAtStart = Boolean.FALSE;
        PreferencesFacade.getDefault().init(dropPreferencesFileAtStart);
        
        DatabaseFacade.getDefault().register(Properties.getPropertyForApplication(KEY__APPLICATION__DATABASE));
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        final ApplicationView applicationView = new ApplicationView();
        final Scene scene = new Scene(applicationView.getView(), 1280, 720);
        primaryStage.setTitle(Properties.getPropertyForApplication(KEY__APPLICATION__TITLE)
                + Properties.getPropertyForApplication(KEY__APPLICATION__VERSION));
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
           we.consume();
           
           this.onCloseRequest();
        });
//        primaryStage.getIcons().add(new Image("http://icons.iconarchive.com/icons/guillendesign/variations-2/256/Favourites-icon.png"));
        
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }
    
    private void onCloseRequest() {
        // afterburner.fx
        Injector.forgetAll();
        
        // Database
        DatabaseFacade.getDefault().shutdown();
        
        // Message
        final char borderSign = Properties.getPropertyForApplication(KEY__APPLICATION__BORDER_SIGN).charAt(0);
        final String message = Properties.getPropertyForApplication(KEY__APPLICATION__MESSAGE_STOP);
        final String title = Properties.getPropertyForApplication(KEY__APPLICATION__TITLE)
                + Properties.getPropertyForApplication(KEY__APPLICATION__VERSION);
        LoggerFacade.getDefault().message(borderSign, 80, String.format(message, title));
        
        // Timer
        final PauseTransition pt = new PauseTransition(LITTLE_DELAY__DURATION_125);
        pt.setOnFinished((ActionEvent event) -> {
            Platform.exit();
        });
        pt.playFromStart();
    }
    
}

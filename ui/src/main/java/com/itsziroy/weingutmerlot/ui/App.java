package com.itsziroy.weingutmerlot.ui;

import com.itsziroy.weingutmerlot.backend.Entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.backend.db.DB;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import jakarta.persistence.PersistenceException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            var javaVersion = SystemInfo.javaVersion();
            var javafxVersion = SystemInfo.javafxVersion();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/Main.fxml"));
            Parent main = loader.load();

            Scene mainScene = new Scene(main);
            // Adds Material UI Theme Manager
            MFXThemeManager.addOn(mainScene, Themes.DEFAULT, Themes.LEGACY);

            stage.setScene(mainScene);
            stage.show();


        } catch (PersistenceException e) {

            var label = new Label("A database Error occured while starting the application: " +e.getMessage());
            var scene = new Scene(new StackPane(label), 640, 480);
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
package com.itsziroy.weingutmerlot.ui;

import com.itsziroy.weingutmerlot.backend.Entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.backend.db.DB;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        DB.initialize();
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var prozess = DB.entityManager.find(Gaerungsprozessschritt.class, 2);



        var label = new Label("Hello, JavaFX " + prozess.getPreviousProzessschritt().getId() + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
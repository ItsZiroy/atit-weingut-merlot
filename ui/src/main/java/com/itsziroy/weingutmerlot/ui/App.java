package com.itsziroy.weingutmerlot.ui;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import jakarta.persistence.PersistenceException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JavaFX App
 */
public class App extends Application {

    BorderPane borderPane = new BorderPane();
    public static Locale locale = new Locale("de");

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        loadView();
        borderPane.setTop(createComboBox());
        stage.setScene(new Scene(borderPane));
        stage.setTitle("Internationalization");
        stage.show();
    }

    private ComboBox<Locale> createComboBox() {
        ComboBox<Locale> comboBox = new ComboBox<>();
        ObservableList<Locale> options = FXCollections.observableArrayList(Locale.GERMAN, Locale.ENGLISH, Locale.FRENCH);
        comboBox.setItems(options);
        comboBox.setConverter(new StringConverter<Locale>() {
            @Override
            public String toString(Locale object) {
                return object.getDisplayLanguage();
            }

            @Override
            public Locale fromString(String string) {
                return null;
            }
        });
        comboBox.setCellFactory(p -> new LanguageListCell());
        comboBox.getSelectionModel().selectFirst();

        comboBox.setOnAction(event -> {
            App.locale=comboBox.getSelectionModel().getSelectedItem();
            loadView();
        });
        return comboBox;
    }

    private void loadView() {
        try {
            FXMLLoader loader = new FXMLLoader();

            // Here, just the resource bundles name is mentioned. You can add support for more languages
            // by adding more properties-files with language-specific endings like
            // "E_13_Internationalization_fr.properties".
            loader.setResources(ResourceBundle.getBundle("App", locale));

            Pane pane = (AnchorPane) loader.load(this.getClass().getResource("/views/Main.fxml").openStream());
            borderPane.setCenter(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    class LanguageListCell extends ListCell<Locale> {
        @Override protected void updateItem(Locale item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getDisplayLanguage());
            }
        }
    }
}

/*
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


}*/

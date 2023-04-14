package com.itsziroy.weingutmerlot.ui;

import jakarta.persistence.PersistenceException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JavaFX App
 */
public class App extends Application {

    // default language is German
    public static Locale locale = new Locale("de");
    private BorderPane borderPane = new BorderPane();


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            loadView();
            borderPane.setTop(createComboBox());
            stage.setScene(new Scene(borderPane));
            stage.setTitle("Internationalization");
            stage.show();

        } catch (PersistenceException e) {
            var label = new Label("A database Error occured while starting the application: " +e.getMessage());
            var scene = new Scene(new StackPane(label), 640, 480);
            stage.setScene(scene);
            stage.show();
        }
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
            App.locale = comboBox.getSelectionModel().getSelectedItem();
            loadView();
        });
        return comboBox;
    }

    private void loadView() {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setResources(ResourceBundle.getBundle("App", locale));

            InputStream inputStream = this.getClass().getResource("/views/Main.fxml").openStream();
            Pane pane = loader.load(inputStream);
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

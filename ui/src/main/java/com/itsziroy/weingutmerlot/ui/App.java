package com.itsziroy.weingutmerlot.ui;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import jakarta.persistence.PersistenceException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JavaFX App
 */
public class App extends Application {

  // default language is German
  public static Locale locale = new Locale("de");

  public static void main(String[] args) {
    launch();
  }

  private static final Scene scene = new Scene(new BorderPane());

  @Override
  public void start(Stage stage) {
    MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
    loadView("/views/Main.fxml");
    stage.setScene(scene);
    stage.show();
  }

  public static void loadView(String view) {
    Parent root;

    try {
      FXMLLoader loader = new FXMLLoader();
      ResourceBundle resourceBundle = ResourceBundle.getBundle("App", App.locale);
      loader.setResources(resourceBundle);
      URL url = App.class.getResource(view);
      loader.setLocation(url);
      root = loader.load();

    } catch (IOException | NullPointerException e) {
      var label = new Label("The view could not be loaded" + e.getMessage());
      var viewExceptionScene = new Scene(new StackPane(label), 640, 480);
      root = viewExceptionScene.getRoot();

    } catch (PersistenceException e) {
      var label = new Label("A database error occurred while starting the application: " + e.getMessage());
      var dbExceptionScene = new Scene(new StackPane(label), 640, 480);
      root = dbExceptionScene.getRoot();

    } catch (Exception e) {
      var label = new Label("An error occurred while starting the app: " + e.getMessage() +
              "See system log for more information.");
      var exceptionScene = new Scene(new StackPane(label), 640, 480);
      root = exceptionScene.getRoot();
    }

    scene.setRoot(root);
  }
}

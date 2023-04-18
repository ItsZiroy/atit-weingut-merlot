package com.itsziroy.weingutmerlot.ui;

import com.itsziroy.weingutmerlot.ui.Enums.View;
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
import org.apache.logging.log4j.LogManager;

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
    loadView(View.MAIN);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Loads the view that is passed as a parameter in the selected language
   * @param view View that shall be loaded
   */
  public static void loadView(View view) {
    Parent root;

    ResourceBundle resourceBundle = null;
    try {
      LogManager.getLogger().info("Loading View " + view.toString());
      FXMLLoader loader = new FXMLLoader();
      resourceBundle = ResourceBundle.getBundle("App", App.locale);
      loader.setResources(resourceBundle);
      URL url = App.class.getResource(view.toString());
      loader.setLocation(url);
      root = loader.load();

    } catch (IOException | NullPointerException e) {
      LogManager.getLogger().error("The view could not be loaded");
      var label = new Label(resourceBundle.getString("viewExceptionScene") + e.getMessage());
      var viewExceptionScene = new Scene(new StackPane(label), 640, 480);
      root = viewExceptionScene.getRoot();

    } catch (PersistenceException e) {
      LogManager.getLogger().error("A database error occurred while starting the application:  ");
      var label = new Label(resourceBundle.getString("dbExceptionScene") + e.getMessage());
      var dbExceptionScene = new Scene(new StackPane(label), 640, 480);
      root = dbExceptionScene.getRoot();

    } catch (Exception e) {
      LogManager.getLogger().error("An error occurred while starting the app");
      var label = new Label(resourceBundle.getString("exceptionScene") + e.getMessage() +
              resourceBundle.getString("seeSystemLog"));
      var exceptionScene = new Scene(new StackPane(label), 640, 480);
      root = exceptionScene.getRoot();
    }

    scene.setRoot(root);
  }
}

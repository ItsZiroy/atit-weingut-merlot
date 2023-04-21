package com.itsziroy.weingutmerlot.ui;

import com.itsziroy.weingutmerlot.ui.Enums.View;
import com.itsziroy.weingutmerlot.ui.controller.ErrorController;
import jakarta.persistence.PersistenceException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.javatuples.Pair;

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

  private static Scene scene;

  public static ResourceBundle resourceBundle;

  @Override
  public void start(Stage stage) {
    scene = new Scene(new Pane());
    stage.setScene(scene);
    setView(View.DASHBOARD);
    stage.show();
  }

  /**
   * Loads the view that is passed as a parameter in the selected language
   * @param view View that shall be loaded
   * @return Pair of the loaded Parent Node and the Loader Object
   */
  public static Pair<Parent, FXMLLoader> loadView(View view) {
    Parent root;
    FXMLLoader loader = new FXMLLoader();

    LogManager.getLogger().info("Loading View " + view);
    try {
      resourceBundle = ResourceBundle.getBundle("App", App.locale);
      loader.setResources(resourceBundle);
      URL url = App.class.getResource(view.toString());
      loader.setLocation(url);
      root = loader.load();
      LogManager.getLogger().info("Successfully loaded View " + view);

    } catch (IOException | IllegalStateException e) {
      LogManager.getLogger().error(view + " could not be loaded");
      App.error(resourceBundle.getString("viewExceptionScene"));
      throw new RuntimeException(e);

    } catch (PersistenceException e) {
      LogManager.getLogger().error("A database error occurred while starting the application:  ");
      App.error(resourceBundle.getString("dbExceptionScene"));
      throw new RuntimeException(e);

    } catch (Exception e) {
      LogManager.getLogger().error("An error occurred while starting the app");
      App.error(resourceBundle.getString("exceptionScene") +" " +
              resourceBundle.getString("seeSystemLog"));
      throw new RuntimeException(e);
    }
    return new Pair<>(root, loader);
  }

  /**
   * Combines {@link #loadView(View)} and {@link #setRoot(Parent)}
   * @param view View
   */
  public static void setView(View view) {
    Parent root = loadView(view).getValue0();
    if(root != null) scene.setRoot(root);
  }

  /**
   * Sets the Root Node of the entire Stage
   * @param root Node to display
   */
  public static void setRoot(Parent root) {
    LogManager.getLogger().info("Startet Setting Root");
    if(root != null) scene.setRoot(root);
    LogManager.getLogger().info("Finished Setting Root");
  }

  /**
   * A helper method for loading a generic error View.
   * See {@link ErrorController} for more information
   * @param message The message to display
   */
  public static void error(String message) {
    Pair<Parent, FXMLLoader> pair = loadView(View.ERROR);
    FXMLLoader loader = pair.getValue1();

    ErrorController controller = loader.getController();
    controller.initializeData(message);
    App.setRoot(pair.getValue0());
  }
}

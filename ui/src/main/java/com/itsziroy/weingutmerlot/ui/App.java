package com.itsziroy.weingutmerlot.ui;

import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.ui.controller.ErrorController;
import com.itsziroy.weingutmerlot.ui.helper.LoadedView;
import jakarta.persistence.PersistenceException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * JavaFX App
 */
public class App extends Application {

    // default language is German
    private static Locale locale = new Locale("de");
    private static ResourceBundle resourceBundle;
    private static Scene scene;

    private final String APP_ICON_PATH = "/images/weingutmerlot_square.png";

    /**
     * A helper method for loading a generic error View.
     * See {@link ErrorController} for more information
     *
     * @param message The message to display
     */
    public static void error(String message) {
        LoadedView loadedView = loadView(View.ERROR);
        FXMLLoader loader = loadedView.loader();

        ErrorController controller = loader.getController();
        controller.initializeData(message);
        App.setRoot(loadedView.parent());
    }

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        App.locale = locale;
    }

    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /**
     * Loads the view that is passed as a parameter in the selected language
     *
     * @param view View that shall be loaded
     * @return Pair of the loaded Parent Node and the Loader Object
     */
    public static LoadedView loadView(View view) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();

        LogManager.getLogger().info("Loading View " + view);
        try {
            loader.setResources(resourceBundle);
            URL url = App.class.getResource(view.toString());
            loader.setLocation(url);
            root = loader.load();
            LogManager.getLogger().info("Successfully loaded View " + view);

        } catch (PersistenceException e) {
            LogManager.getLogger().error("A database error occurred while starting the application:  ", e);
            App.error(resourceBundle.getString("dbExceptionScene"));

        } catch (IOException | IllegalStateException e) {
            LogManager.getLogger().error(view + " could not be loaded", e);
            App.error(resourceBundle.getString("viewExceptionScene"));

        } catch (Exception e) {
            LogManager.getLogger().error("An error occurred while starting the app", e);
            App.error(resourceBundle.getString("exceptionScene") + " " +
                    resourceBundle.getString("seeSystemLog"));
        }
        return new LoadedView(root, loader);
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Sets the Root Node of the entire Stage
     *
     * @param root Node to display
     */
    public static void setRoot(Parent root) {
        LogManager.getLogger().info("Started Setting Root");
        if (root != null) scene.setRoot(root);
        LogManager.getLogger().info("Finished Setting Root");
    }

    /**
     * Combines {@link #loadView(View)} and {@link #setRoot(Parent)}
     *
     * @param view View
     */
    public static void setView(View view) {
        Parent root = loadView(view).parent();
        if (root != null) scene.setRoot(root);
    }

    @Override
    public void start(Stage stage) {
        scene = new Scene(new Pane());
        stage.setScene(scene);

        stage.setTitle("Weingut Merlot");

        try {
            resourceBundle = ResourceBundle.getBundle("App", App.locale);
            DB.setPersistenceUnit("default");
            setView(View.DASHBOARD);

            setAppIcon(stage);

        } catch (PersistenceException e) {
            LogManager.getLogger().error("A database error occurred while starting the application:  ", e);
            App.error(resourceBundle.getString("dbExceptionScene"));
        } catch (MissingResourceException e) {
            LogManager.getLogger().error("Resource bundle could not be loaded. Something is wrong with your files!", e);
        } catch (IOException | AssertionError e) {
            LogManager.getLogger().error("App Icon could not be loaded. Something is wrong with your files!", e);
        }

        stage.show();
    }

    /**
     * Sets the App Icon
     *
     * @param stage Root stage
     * @throws IOException Icon could not be loaded
     * @throws AssertionError Loaded Icon is null
     */
    private void setAppIcon(Stage stage) throws IOException, AssertionError {
        InputStream logoStream = getClass().getResourceAsStream(APP_ICON_PATH);

        assert logoStream != null;

        Image logo = new Image(logoStream);
        stage.getIcons().add(logo);

        // Add image to dock icon
        if (Taskbar.isTaskbarSupported()) {
            var taskbar = Taskbar.getTaskbar();
            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                var dockIcon = defaultToolkit.getImage(getClass().getResource(APP_ICON_PATH));
                taskbar.setIconImage(dockIcon);

            }
        }
    }
}

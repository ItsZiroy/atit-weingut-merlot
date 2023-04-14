package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.ui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ResourceBundle;

public class Main {

  private Parent weinCreateScene;
  private Parent weinOverviewScene;

  @FXML
  private Button weinButton;
  @FXML
  public void initialize() throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setResources(ResourceBundle.getBundle("App", App.locale));
    loader.setLocation(getClass().getResource("/views/create/wein.fxml"));
    weinCreateScene = loader.load();

    loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/views/read/wein.fxml"));
    weinOverviewScene = loader.load();
  }

  public void weinCreateButtonClicked(MouseEvent event) {
    Node node = (Node) event.getSource();
    Scene scene = node.getScene();
    scene.setRoot(weinCreateScene);
  }

  public void weinOverviewButtonClicked(MouseEvent event) {
    Node node = (Node) event.getSource();
    Scene scene = node.getScene();
    scene.setRoot(weinOverviewScene);
  }

}

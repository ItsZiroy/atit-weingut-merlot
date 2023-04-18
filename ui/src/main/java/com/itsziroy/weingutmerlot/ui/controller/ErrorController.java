package com.itsziroy.weingutmerlot.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Controller for FXMl View Error.fxml
 */
public class ErrorController {

  @FXML
  public Text errorMessage;

  public void initializeData(String message) {
      errorMessage.setText(message);
  }
}

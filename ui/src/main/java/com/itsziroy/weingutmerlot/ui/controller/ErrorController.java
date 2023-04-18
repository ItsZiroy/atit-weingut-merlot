package com.itsziroy.weingutmerlot.ui.controller;

import javafx.scene.text.Text;

public class ErrorController {

  public Text errorMessage;

  public void initializeData(String message) {
      errorMessage.setText(message);
  }
}

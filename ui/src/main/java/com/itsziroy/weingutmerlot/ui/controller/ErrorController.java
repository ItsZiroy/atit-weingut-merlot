package com.itsziroy.weingutmerlot.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Controller for FXMl View Error.fxml
 */
public class ErrorController extends Controller {

    @FXML
    private Text errorMessage;

    public void initializeData(String message) {
        errorMessage.setText(message);
    }

    @Override
    public void initialize() { }
}

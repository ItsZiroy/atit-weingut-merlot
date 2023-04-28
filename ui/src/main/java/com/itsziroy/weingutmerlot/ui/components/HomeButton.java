package com.itsziroy.weingutmerlot.ui.components;

import com.itsziroy.weingutmerlot.ui.App;
import com.itsziroy.weingutmerlot.ui.View;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;

public class HomeButton extends MFXButton {
  public HomeButton() {
    super();
    this.setOnAction(this::handleHomeButtonClicked);
    this.setText(App.getResourceBundle().getString("home"));
  }

  private void handleHomeButtonClicked(ActionEvent actionEvent) {
    App.setView(View.DASHBOARD);
  }
}
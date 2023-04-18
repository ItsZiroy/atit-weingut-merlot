package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.ui.App;
import com.itsziroy.weingutmerlot.ui.Enums.View;
import com.itsziroy.weingutmerlot.ui.components.HomeButton;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;

public class UeberpruefungController {

  public MFXTextField alkoholSoll;
  public MFXTextField zuckerSoll;
  public MFXTextField temperaturSoll;
  public Label chargeLabel;
  private Gaerungsprozessschritt gaerungsprozessschritt;
  private Charge charge;


  public void initializeData(Gaerungsprozessschritt gaerungsprozessschritt, Charge charge) {
    this.gaerungsprozessschritt = gaerungsprozessschritt;
    this.charge = charge;

    alkoholSoll.setText(String.valueOf(this.gaerungsprozessschritt.getSollAlkohol()));
    zuckerSoll.setText(String.valueOf(this.gaerungsprozessschritt.getSollZucker()));
    temperaturSoll.setText(String.valueOf(this.gaerungsprozessschritt.getSollTemperatur()));

    chargeLabel.setText("Charge "+ this.charge.getId());

  }
}

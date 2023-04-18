package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.control.Label;

public class UeberpruefungController {

  public MFXTextField alkoholSoll;
  public MFXTextField zuckerSoll;
  public MFXTextField temperaturSoll;
  public Label chargeLabel;
  private Gaerungsprozessschritt gaerungsprozessschritt;
  private Charge charge;

  /**
   * This Method needs to be run before the View can be displayed properly and
   * initializes all the necessary ui components.
   *
   * @param gaerungsprozessschritt Gärungsprozesschritt the Überprüfung should be created for
   * @param charge The charge that the Überpüfung should be created for
   */
  public void initializeData(Gaerungsprozessschritt gaerungsprozessschritt, Charge charge) {
    this.gaerungsprozessschritt = gaerungsprozessschritt;
    this.charge = charge;

    alkoholSoll.setText(String.valueOf(this.gaerungsprozessschritt.getSollAlkohol()));
    zuckerSoll.setText(String.valueOf(this.gaerungsprozessschritt.getSollZucker()));
    temperaturSoll.setText(String.valueOf(this.gaerungsprozessschritt.getSollTemperatur()));

    chargeLabel.setText("Charge "+ this.charge.getId());

  }
}

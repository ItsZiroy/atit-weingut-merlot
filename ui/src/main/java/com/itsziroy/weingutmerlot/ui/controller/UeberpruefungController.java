package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.ui.App;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class UeberpruefungController {

  public MFXTextField alkoholSoll;
  public MFXTextField zuckerSoll;
  public MFXTextField temperaturSoll;
  public MFXTextField alkoholIst;
  public MFXTextField zuckerIst;
  public MFXTextField temperaturIst;
  public Label chargeLabel;
  public Label alkoholLabel;
  public Label zuckerLabel;
  public Label temperaturLabel;
  private Gaerungsprozessschritt gaerungsprozessschritt;
  private Charge charge;

  /**
   * This Method needs to be run before the View can be displayed properly and
   * initializes all the necessary ui components.
   *
   * @param gaerungsprozessschritt Gärungsprozesschritt the Überprüfung should be created for
   * @param charge The Charge that the Überpüfung should be created for
   */
  public void initializeData(Gaerungsprozessschritt gaerungsprozessschritt, Charge charge) {
    this.gaerungsprozessschritt = gaerungsprozessschritt;
    this.charge = charge;

    double sollAlkohol = this.gaerungsprozessschritt.getSollAlkohol();
    int sollZucker = this.gaerungsprozessschritt.getSollZucker();
    double sollTemperatur = this.gaerungsprozessschritt.getSollTemperatur();
    alkoholSoll.setText(sollAlkohol + " Vol-%");
    zuckerSoll.setText(sollZucker + " g/l");
    temperaturSoll.setText(sollTemperatur + " °C");

    chargeLabel.setText(App.resourceBundle.getString("charge") + ": " + this.charge.getId());

  }

  public void differenceAlkohol(ActionEvent actionEvent) {
    double istAlkohol = Double.parseDouble(alkoholIst.getText());
    double sollAlkohol = this.gaerungsprozessschritt.getSollAlkohol();
    alkoholLabel.setText(String.valueOf(istAlkohol - sollAlkohol));
  }

  public void differenceZucker(ActionEvent actionEvent) {
    double istZucker = Double.parseDouble(zuckerIst.getText());
    int sollZucker = this.gaerungsprozessschritt.getSollZucker();
    zuckerLabel.setText(String.valueOf(istZucker - sollZucker));
  }

  public void differenceTemperatur(ActionEvent actionEvent) {
    double istTemperatur = Double.parseDouble(temperaturIst.getText());
    double sollTemperatur = this.gaerungsprozessschritt.getSollTemperatur();
    temperaturLabel.setText(String.valueOf(istTemperatur - sollTemperatur));
  }
}

package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.backend.db.entities.Wein;
import com.itsziroy.weingutmerlot.ui.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class UeberpruefungController {

    @FXML
    private MFXTextField alkoholSoll, zuckerSoll, temperaturSoll;
    @FXML
    private MFXTextField alkoholIst, zuckerIst, temperaturIst;
    @FXML
    private Label chargeLabel, gaerungsprozessschrittLabel, weinLabel, weinartLabel;
    @FXML
    private Label alkoholLabel, zuckerLabel, temperaturLabel;
    @FXML
    private MFXButton saveButton;
    private Gaerungsprozessschritt gaerungsprozessschritt;
    private Charge charge;

    /**
     * This Method needs to be run before the View can be displayed properly and
     * initializes all the necessary ui components.
     *
     * @param gaerungsprozessschritt Gärungsprozesschritt the Überprüfung should be created for
     * @param charge                 The Charge that the Überpüfung should be created for
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

        chargeLabel.setText(App.resourceBundle.getString("charge") + ": " + charge.getId());
        gaerungsprozessschrittLabel.setText(App.resourceBundle.getString("gaerungsprozessschritt") + ": " + gaerungsprozessschritt.getSchritt());

        Wein wein = charge.getWein();
        weinLabel.setText(App.resourceBundle.getString("wein") + ": " + wein.getBeschreibung());
        weinartLabel.setText(App.resourceBundle.getString("weinart") + ": " + wein.getWeinart());

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

    public void persistUeberpruefung(MouseEvent mouseEvent) {

    }
}

package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.HefeManager;
import com.itsziroy.weingutmerlot.backend.db.entities.*;
import com.itsziroy.weingutmerlot.ui.App;
import com.itsziroy.weingutmerlot.ui.Enums.View;
import io.github.palexdev.materialfx.controls.*;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class UeberpruefungController {

    public MFXDatePicker datePicker;
    public MFXComboBox<Hefe> hefeComboBox;
    @FXML
    private MFXToggleButton nachsterSchrittToggleButton;
    @FXML
    private MFXTextField alkoholSoll, zuckerSoll, temperaturSoll,
            anpassungZucker, anpassungTemperatur,
            alkoholIst, zuckerIst, temperaturIst;
    @FXML
    private Label chargeLabel, gaerungsprozessschrittLabel, weinLabel, weinartLabel;
    @FXML
    private Label alkoholLabel, zuckerLabel, temperaturLabel;
    @FXML
    private MFXButton saveButton;
    private Gaerungsprozessschritt gaerungsprozessschritt;
    private Charge charge;

    public void initialize() {
        alkoholIst.textProperty().addListener(this::differenceAlkohol);
        zuckerIst.textProperty().addListener(this::differenceZucker);
        temperaturIst.textProperty().addListener(this::differenceTemperatur);

        initializeHefenComboxbox();
    }

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

    public void differenceAlkohol(Observable observable, String oldValue, String newValue) {
        try {
            double istAlkohol = Double.parseDouble(newValue);
            if(istAlkohol < 0) {
                throw new NumberFormatException();
            }
            double sollAlkohol = this.gaerungsprozessschritt.getSollAlkohol();
            alkoholLabel.setText(String.valueOf(istAlkohol - sollAlkohol));
        } catch (NumberFormatException e) {
            alkoholLabel.setText("");
        }
    }

    public void differenceZucker(Observable observable, String oldValue, String newValue) {
        try {
            int istZucker = Integer.parseInt(newValue);
            if(istZucker < 0) {
                throw new NumberFormatException();
            }
            int sollZucker = this.gaerungsprozessschritt.getSollZucker();
            zuckerLabel.setText(String.valueOf(istZucker - sollZucker));
        } catch (NumberFormatException e) {
            zuckerLabel.setText("");
        }
    }

    public void differenceTemperatur(Observable observable, String oldValue, String newValue) {
        try {
            double istTemperatur = Double.parseDouble(newValue);
            if(istTemperatur < 0) {
                throw new NumberFormatException();
            }
            double sollTemperatur = this.gaerungsprozessschritt.getSollTemperatur();
            temperaturLabel.setText(String.valueOf(istTemperatur - sollTemperatur));
        } catch (NumberFormatException e) {
            temperaturLabel.setText("");
        }
    }

    public void handleTakeOverZucker() {
        try{
            int zuckerDifference = Integer.parseInt(zuckerLabel.getText());
            // When actual value higher than target value, you cannot put sugar out of the wine
            if(zuckerDifference > 0){
                zuckerDifference = 0;
            }
            anpassungZucker.setText(String.valueOf(zuckerDifference * (-1)));
        } catch (NumberFormatException e) {
            anpassungZucker.setText("");
        }
    }

    public void handleTakeOverTemperatur() {
        try{
            double temperaturDifference = Double.parseDouble(temperaturLabel.getText()) * (-1);
            anpassungTemperatur.setText(String.valueOf(temperaturDifference));
        } catch (NumberFormatException e) {
            anpassungTemperatur.setText("");
        }
    }

    public void handleNextStepChange() {
        datePicker.setVisible(!nachsterSchrittToggleButton.isSelected());
    }

    private void initializeHefenComboxbox() {
        List<Hefe> hefen = HefeManager.getAllHefen();
        for (var hefe : hefen) {
            hefeComboBox.getItems().add(hefe);
        }
    }

    public void handleSaveButtonClicked() {
        // TODO
        App.setView(View.DASHBOARD);
    }
}

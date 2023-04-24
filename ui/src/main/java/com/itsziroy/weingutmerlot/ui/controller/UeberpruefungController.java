package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.HefeManager;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.*;
import com.itsziroy.weingutmerlot.ui.App;
import com.itsziroy.weingutmerlot.ui.Enums.View;
import io.github.palexdev.materialfx.controls.*;
import jakarta.persistence.PersistenceException;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class UeberpruefungController {

    public MFXDatePicker datePicker;
    public MFXComboBox<Hefe> hefeComboBox;
    public MFXButton uebernehmenZucker, uebernehmenTemperatur;
    @FXML
    private MFXToggleButton nachsterSchrittToggleButton;
    @FXML
    private MFXTextField alkoholSoll, zuckerSoll, temperaturSoll,
            anpassungZucker, anpassungTemperatur,
            alkoholIst, zuckerIst, temperaturIst,
            mengeHefe;
    @FXML
    private Label chargeLabel, gaerungsprozessschrittLabel, weinLabel, weinartLabel;
    @FXML
    private Label alkoholLabel, zuckerLabel, temperaturLabel;

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
            int difference = istZucker - sollZucker;
            if(difference > 0){
                uebernehmenZucker.setDisable(true);
            } else {
                uebernehmenZucker.setDisable(false);
            }
            zuckerLabel.setText(String.valueOf(difference));
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
        Ueberpruefung ueberpruefung = new Ueberpruefung();

        // set all attributes entered before
        ueberpruefung.setCharge(charge);
        ueberpruefung.setGaerungsprozessschritt(gaerungsprozessschritt);
        ueberpruefung.setDatum(new Date());
        int istZucker = Integer.parseInt(zuckerIst.getText());
        ueberpruefung.setIstZucker(istZucker);
        double istTemperatur = Double.parseDouble(temperaturIst.getText());
        ueberpruefung.setIstTemperatur(istTemperatur);
        double istAlkohol = Double.parseDouble(alkoholIst.getText());
        ueberpruefung.setIstAlkohol(istAlkohol);
        int anpassungZuckerInt = Integer.parseInt(anpassungZucker.getText());
        ueberpruefung.setAnpassungZucker(anpassungZuckerInt);
        double anpassungTemperaturDouble = Double.parseDouble(anpassungTemperatur.getText());
        ueberpruefung.setAnpassungTemperatur(anpassungTemperaturDouble);

        Hefe hefe = hefeComboBox.getSelectedItem();
        int hefe_menge = Integer.parseInt(mengeHefe.getText());

        //Hefen + Menge Setzen fehlt
        //TODO
        /*Set<Hefe> hefen;
        hefen.add(hefe);
        ueberpruefung.setHefen(hefen);*/


        boolean naechsterSchritt = nachsterSchrittToggleButton.isSelected();
        ueberpruefung.setNaechsterSchritt(naechsterSchritt);

        // next date is only relevant if nachsterSchritt is deselected
        if(!naechsterSchritt){
            LocalDate localNextDate = datePicker.getValue();
            Instant instant = Instant.from(localNextDate.atStartOfDay(ZoneId.systemDefault()));
            Date nextDate = Date.from(instant);
            ueberpruefung.setNextDate(nextDate);
        }

        // persist new Überprüfung
        try {
            DB.getEntityManager().getTransaction().begin();
            DB.getEntityManager().persist(ueberpruefung);
            DB.getEntityManager().getTransaction().commit();
        } catch (PersistenceException e) {
            LogManager.getLogger().error("Datenbank-Transaktion fehlgeschlagen");
        }

        // return to Dashboard view
        App.setView(View.DASHBOARD);
    }

    public int checkStringforValidInt(String string){
        // TODO
        int value;
        try{
            value = Integer.parseInt(string);
        } catch(NumberFormatException e) {
            value = -1;
            // show message, no int was entered
        }
        if(value < 0){
            value = -1;
            // show message, no negative values are allowed
        }
        return value;
    }

    public double checkStringforValidDouble(String string){
        // TODO
        double value;
        try{
            value = Double.parseDouble(string);
        } catch(NumberFormatException e) {
            value = -1;
            // show message, no double was entered
        }
        if(value < 0){
            // show message, no negative values are allowed
        }
        return value;
    }
}

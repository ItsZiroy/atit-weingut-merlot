package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.HefeManager;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.*;
import com.itsziroy.weingutmerlot.backend.db.entities.pivot.GaerungsprozessschritteHasHefen;
import com.itsziroy.weingutmerlot.backend.db.entities.pivot.UeberpruefungenHasHefen;
import com.itsziroy.weingutmerlot.ui.App;
import com.itsziroy.weingutmerlot.ui.Enums.View;
import com.itsziroy.weingutmerlot.ui.helper.HefeMenge;
import io.github.palexdev.materialfx.controls.*;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.LogManager;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class UeberpruefungController {

    public MFXDatePicker datePicker;
    public MFXComboBox<Hefe> hefeComboBox;
    @FXML
    public ListView<HefeMenge> anpassungHefeList;
    public ListView<HefeMenge> hefeSollList;

    public MFXButton uebernehmenZucker, uebernehmenTemperatur, hinzufuegenHefeButton;
    @FXML
    private MFXToggleButton nachsterSchrittToggleButton;
    @FXML
    private MFXTextField alkoholSoll, zuckerSoll, temperaturSoll,
            anpassungZucker, anpassungTemperatur, anpasssungHefe,
            alkoholIst, zuckerIst, temperaturIst;
    @FXML
    private Label chargeLabel, gaerungsprozessschrittLabel, weinLabel, weinartLabel;
    @FXML
    private Label alkoholLabel, zuckerLabel, temperaturLabel;
    @FXML
    private MFXButton saveButton;
    private Gaerungsprozessschritt gaerungsprozessschritt;
    private Charge charge;

    private Map<Hefe, Double> hefeMenge = new HashMap<>();

    public void initialize() {
        alkoholIst.textProperty().addListener(this::differenceAlkohol);
        zuckerIst.textProperty().addListener(this::differenceZucker);
        temperaturIst.textProperty().addListener(this::differenceTemperatur);
        hefeComboBox.textProperty().addListener(this::addHefe);
        anpasssungHefe.textProperty().addListener(this::addHefe);

        initializeHefenCombobox();
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

        Set<GaerungsprozessschritteHasHefen> gaerungsprozessschritteHasHefen
                = gaerungsprozessschritt.getHefenPivot();

        System.out.println(gaerungsprozessschritt.getId());
        gaerungsprozessschritteHasHefen.forEach(g -> {
            hefeSollList.getItems().add(new HefeMenge(g.getHefe(), g.getMenge()));
        });

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
            uebernehmenZucker.setDisable(difference > 0);
            zuckerLabel.setText(String.valueOf(difference));
        } catch (NumberFormatException e) {
            uebernehmenZucker.setDisable(true);
            zuckerLabel.setText("");
        }
    }

    public void differenceTemperatur(Observable observable, String oldValue, String newValue) {
        try {
            double istTemperatur = Double.parseDouble(newValue);
            if(istTemperatur < 0) {
                throw new NumberFormatException();
            }
            uebernehmenTemperatur.setDisable(false);
            double sollTemperatur = this.gaerungsprozessschritt.getSollTemperatur();
            temperaturLabel.setText(String.valueOf(istTemperatur - sollTemperatur));
        } catch (NumberFormatException e) {
            uebernehmenTemperatur.setDisable(true);
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

    private void initializeHefenCombobox() {
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
        Date currentDate = new Date();
        ueberpruefung.setDatum(currentDate);
        try{
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

            boolean naechsterSchritt = nachsterSchrittToggleButton.isSelected();
            ueberpruefung.setNaechsterSchritt(naechsterSchritt);

            // next date is only relevant if nachsterSchritt is deselected
            if(!naechsterSchritt){
                LocalDate localNextDate = datePicker.getValue();
                Instant instant = Instant.from(localNextDate.atStartOfDay(ZoneId.systemDefault()));
                Date nextDate = Date.from(instant);
                if(nextDate.before(currentDate)){
                    throw new IllegalArgumentException();
                }
                ueberpruefung.setNextDate(nextDate);
            }

            // persist new Überprüfung
            DB.getEntityManager().getTransaction().begin();
            DB.getEntityManager().persist(ueberpruefung);

            // persist new ueberpruefungHasHefen
            for(HefeMenge h: anpassungHefeList.getItems()) {
                UeberpruefungenHasHefen ueberpruefungenHasHefen = new UeberpruefungenHasHefen();
                ueberpruefungenHasHefen.setAnpassung(h.menge());
                ueberpruefungenHasHefen.setHefe(h.hefe());
                ueberpruefungenHasHefen.setUeberpruefung(ueberpruefung);

                DB.getEntityManager().persist(ueberpruefungenHasHefen);
            }

            // Get the last Gärungsprozessschritt of the current Gärungsprozess
            TypedQuery<Gaerungsprozessschritt> lastStepQuery = DB.getEntityManager().createQuery(
                    "select g from Gaerungsprozessschritt g where g.gaerungsprozess.id = :id " +
                            "order by schritt desc", Gaerungsprozessschritt.class);
            lastStepQuery.setParameter("id", ueberpruefung.getGaerungsprozessschritt().getGaerungsprozess().getId());
            List<Gaerungsprozessschritt> result = lastStepQuery.getResultList();
            Gaerungsprozessschritt lastStep = result.get(0);

            // If current Überprüfung is set to be valid, and we proceed to the next step
            // we need to check if it is the last process step because then the charge
            // is finished.
            if((ueberpruefung.getGaerungsprozessschritt().getSchritt() == lastStep.getSchritt()
                    && ueberpruefung.isNaechsterSchritt())) {
                charge.setIstFertig(true);
                DB.getEntityManager().persist(charge);
            }

            DB.getEntityManager().getTransaction().commit();
            // return to Dashboard view
            App.setView(View.DASHBOARD);
        } catch(NumberFormatException e){
            LogManager.getLogger().error("Umwandlung der Eingabe in eine Zahl fehlgeschlagen");
            App.error(App.resourceBundle.getString("errorUmwandlungInZahl"));
            //throw new RuntimeException(e);
        } catch(IllegalArgumentException e){
            LogManager.getLogger().error("Dieses Datum ist als Argument nicht erlaubt");
            App.error(App.resourceBundle.getString("errorDatum"));
            //throw new RuntimeException(e);
        } catch (PersistenceException e) {
            LogManager.getLogger().error("Datenbank-Transaktion fehlgeschlagen");
            App.error(App.resourceBundle.getString("errorDatenbank"));
            //throw new RuntimeException(e);
        }
    }

    public void handleAddHefe() {
        if(hefeComboBox.getSelectedItem() != null & !anpasssungHefe.getText().isBlank()) {
            try {
                double menge = Double.parseDouble(anpasssungHefe.getText());
                Hefe hefe = hefeComboBox.getSelectedItem();
                hefeMenge.put(hefe, menge);

                hefeComboBox.clearSelection();
                anpasssungHefe.clear();

                reloadHefeSelection();
            } catch (NumberFormatException e) {
                LogManager.getLogger().warn("Invalid Number for Hefe Input.");
            }
        } else {
            LogManager.getLogger().info("Selection or Text is Empty");
        }
    }

    private void reloadHefeSelection() {
        anpassungHefeList.getItems().clear();
        hefeMenge.forEach((hefe, menge) ->
                anpassungHefeList.getItems().add(new HefeMenge(hefe, menge)));
    }

    public void handleHefeListClicked() {
        HefeMenge selected = anpassungHefeList.getSelectionModel().getSelectedItem();
        hefeComboBox.selectItem(selected.hefe());
        anpasssungHefe.setText(String.valueOf(selected.menge()));
    }

    private void addHefe(Observable observable) {
        int anpassungHefeInt;
        try{
            anpassungHefeInt = Integer.parseInt(anpasssungHefe.getText());
            if(anpassungHefeInt < 0){
                throw new RuntimeException();
            }
        } catch(RuntimeException e){
            anpassungHefeInt = -1;
            hinzufuegenHefeButton.setDisable(true);
        }
        if(hefeComboBox.getSelectedItem() == null || anpassungHefeInt == -1){
            hinzufuegenHefeButton.setDisable(true);
        } else {
            hinzufuegenHefeButton.setDisable(false);
        }
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

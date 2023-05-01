package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.UebererpruefungService;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.*;
import com.itsziroy.weingutmerlot.backend.db.entities.pivot.GaerungsprozessschritteHasHefen;
import com.itsziroy.weingutmerlot.backend.db.entities.pivot.UeberpruefungenHasHefen;
import com.itsziroy.weingutmerlot.backend.db.managers.ChargeManager;
import com.itsziroy.weingutmerlot.ui.App;
import com.itsziroy.weingutmerlot.ui.View;
import com.itsziroy.weingutmerlot.ui.helper.HefeMenge;
import io.github.palexdev.materialfx.controls.*;
import jakarta.persistence.PersistenceException;
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

    @FXML
    private Label alkoholLabel, zuckerLabel, temperaturLabel, irreversibleLabel;
    @FXML
    private MFXTextField alkoholSoll, zuckerSoll, temperaturSoll,
            anpassungZucker, anpassungTemperatur, anpasssungHefe,
            alkoholIst, zuckerIst, temperaturIst;
    @FXML
    private ListView<HefeMenge> anpassungHefeList;
    private Charge charge;
    @FXML
    private Label chargeLabel, gaerungsprozessschrittLabel, weinLabel, weinartLabel;
    @FXML
    private MFXDatePicker datePicker;
    private Gaerungsprozessschritt gaerungsprozessschritt;
    @FXML
    private MFXComboBox<Hefe> hefeComboBox;
    @FXML
    private ListView<HefeMenge> hefeSollList;
    @FXML
    private MFXToggleButton nachsterSchrittToggleButton, irreversibelToggleButton;
    @FXML
    private MFXButton uebernehmenZucker, uebernehmenTemperatur, hinzufuegenHefeButton;
    private final Map<Hefe, Double> hefeMenge = new HashMap<>();

    public void handleAddHefe() {
        if (hefeComboBox.getSelectedItem() != null && !anpasssungHefe.getText().isBlank()) {
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

    /**
     * Handler method for "Nächster Gärungsprozessschritt" toggle button changed
     */
    public void handleNextStepChange() {
        // when the next step is enabled, the date picker, toggle Button and label should not be visible
        // when the next step is disabled, these should be visible
        boolean nextStepSelected = nachsterSchrittToggleButton.isSelected();
        datePicker.setVisible(!nextStepSelected);
        irreversibelToggleButton.setVisible(!nextStepSelected);
        irreversibleLabel.setVisible(!nextStepSelected);
        if (nextStepSelected) {
            // if the next step is selected the irreversible toggle button should not be selected and the date should be cleared
            irreversibelToggleButton.setSelected(false);
            datePicker.clear();
        }
    }

    /**
     * Handler method for "Speichern" button clicked
     */
    public void handleSaveButtonClicked() {
        LogManager.getLogger().info("Started saving Ueberpruefung.");
        // if the Gaerungsprozess cannot be continued
        if (irreversibelToggleButton.isSelected()) {
            ChargeManager chargeManager = DB.getChargeManager();
            chargeManager.setIrreversiblyDamaged(charge);
            // return to Dashboard view
            App.setView(View.DASHBOARD);
            return;
        }

        Ueberpruefung ueberpruefung = new Ueberpruefung();

        // set all attributes entered before
        ueberpruefung.setCharge(charge);
        ueberpruefung.setGaerungsprozessschritt(gaerungsprozessschritt);
        Date currentDate = new Date();
        ueberpruefung.setDatum(currentDate);
        try {
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
            if (!naechsterSchritt) {
                LogManager.getLogger().info("Charge will not go over into next Gaerungsprozessschritt.");

                LocalDate localNextDate = datePicker.getValue();
                Instant instant = Instant.from(localNextDate.atStartOfDay(ZoneId.systemDefault()));
                Date nextDate = Date.from(instant);
                if (nextDate.before(currentDate)) {
                    throw new IllegalArgumentException("Next date cannot be before current date");
                }
                ueberpruefung.setNextDate(nextDate);
            }

            List<UeberpruefungenHasHefen> ueberpruefungenHasHefenList = new ArrayList<>();

            for (HefeMenge h : anpassungHefeList.getItems()) {
                UeberpruefungenHasHefen ueberpruefungenHasHefen = new UeberpruefungenHasHefen();
                ueberpruefungenHasHefen.setAnpassung(h.menge());
                ueberpruefungenHasHefen.setHefe(h.hefe());
                ueberpruefungenHasHefenList.add(ueberpruefungenHasHefen);
            }

            // persist Ueberpruefung and UeberpruefungHasHefen to database
            UebererpruefungService uebererpruefungService = DB.getUeberpruefungManager();
            uebererpruefungService.save(ueberpruefung, ueberpruefungenHasHefenList);

            // return to Dashboard view
            App.setView(View.DASHBOARD);

        } catch (NumberFormatException | NullPointerException e) {
            LogManager.getLogger().error("Umwandlung einer Eingabe in eine Zahl fehlgeschlagen", e);
            App.error(App.getResourceBundle().getString("errorUmwandlungInZahl"));
        } catch (IllegalArgumentException e) {
            LogManager.getLogger().error("Validierungsfehler aufgetreten", e);
            App.error(App.getResourceBundle().getString("errorValidierung"));
        } catch (PersistenceException e) {
            LogManager.getLogger().error("Datenbank-Transaktion fehlgeschlagen", e);
            App.error(App.getResourceBundle().getString("errorDatenbank"));
        }
    }

    /**
     * Handler method for "Übernehmen" button clicked for temperature
     */
    public void handleTakeOverTemperatur() {
        try {
            double temperaturDifference = Double.parseDouble(temperaturLabel.getText()) * (-1);
            anpassungTemperatur.setText(String.valueOf(temperaturDifference));
        } catch (NumberFormatException e) {
            anpassungTemperatur.setText("0");
        }
    }

    /**
     * Handler method for "Übernehmen" button clicked for sugar
     */
    public void handleTakeOverZucker() {
        try {
            int zuckerDifference = Integer.parseInt(zuckerLabel.getText());
            // When actual value higher than target value, you cannot put sugar out of the wine
            if (zuckerDifference > 0) {
                zuckerDifference = 0;
            }
            anpassungZucker.setText(String.valueOf(zuckerDifference * (-1)));
        } catch (NumberFormatException e) {
            anpassungZucker.setText("0");
        }
    }

    public void initialize() {
        alkoholIst.textProperty().addListener(this::differenceAlkohol);
        zuckerIst.textProperty().addListener(this::differenceZucker);
        temperaturIst.textProperty().addListener(this::differenceTemperatur);
        hefeComboBox.textProperty().addListener(this::addHefe);
        anpasssungHefe.textProperty().addListener(this::addHefe);

        initializeHefenCombobox();
    }

    /**
     * Handler method for text value change of actual alcohol
     * Calculates the difference between the actual value of alcohol and the target value and displays it
     *
     * @param observable not used
     * @param oldValue   of actual alcohol
     * @param newValue   of actual alcohol
     */
    private void differenceAlkohol(Observable observable, String oldValue, String newValue) {
        try {
            double istAlkohol = Double.parseDouble(newValue);
            if (istAlkohol < 0) {
                throw new NumberFormatException("Alcohol value cannot be negative");
            }
            double sollAlkohol = this.gaerungsprozessschritt.getSollAlkohol();
            alkoholLabel.setText(String.valueOf(istAlkohol - sollAlkohol));
        } catch (NumberFormatException e) {
            alkoholLabel.setText("");
        }
    }

    /**
     * Handler method for text value change of actual sugar
     * Calculates the difference between the actual value of sugar and the target value and displays it
     *
     * @param observable not used
     * @param oldValue   of actual sugar
     * @param newValue   of actual sugar
     */
    private void differenceZucker(Observable observable, String oldValue, String newValue) {
        try {
            int istZucker = Integer.parseInt(newValue);
            if (istZucker < 0) {
                throw new NumberFormatException("Sugar value cannot be negative");
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

    /**
     * Handler method for text value change of actual temperature
     * Calculates the difference between the actual value of temperature and the target value and displays it
     *
     * @param observable not used
     * @param oldValue   of actual temperature
     * @param newValue   of actual temperature
     */
    private void differenceTemperatur(Observable observable, String oldValue, String newValue) {
        try {
            double istTemperatur = Double.parseDouble(newValue);
            if (istTemperatur < 0) {
                throw new NumberFormatException("Temperature value cannot be negative");
            }
            uebernehmenTemperatur.setDisable(false);
            double sollTemperatur = this.gaerungsprozessschritt.getSollTemperatur();
            temperaturLabel.setText(String.valueOf(istTemperatur - sollTemperatur));
        } catch (NumberFormatException e) {
            uebernehmenTemperatur.setDisable(true);
            temperaturLabel.setText("");
        }
    }

    private void addHefe(Observable observable) {
        int anpassungHefeInt;
        try {
            anpassungHefeInt = Integer.parseInt(anpasssungHefe.getText());
            if (anpassungHefeInt < 0) {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            anpassungHefeInt = -1;
            hinzufuegenHefeButton.setDisable(true);
        }

        hinzufuegenHefeButton.setDisable(hefeComboBox.getSelectedItem() == null || anpassungHefeInt == -1);
    }

    private void initializeHefenCombobox() {
        List<Hefe> hefen = DB.getHefeManager().getAll();
        for (var hefe : hefen) {
            hefeComboBox.getItems().add(hefe);
        }
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

        initializeComponents();

    }

    private void initializeComponents() {
        double sollAlkohol = gaerungsprozessschritt.getSollAlkohol();
        int sollZucker = gaerungsprozessschritt.getSollZucker();
        double sollTemperatur = gaerungsprozessschritt.getSollTemperatur();
        alkoholSoll.setText(sollAlkohol + " Vol-%");
        zuckerSoll.setText(sollZucker + " g/l");
        temperaturSoll.setText(sollTemperatur + " °C");

        chargeLabel.setText(App.getResourceBundle().getString("charge") + ": " + charge.getId());
        gaerungsprozessschrittLabel.setText(App.getResourceBundle().getString("gaerungsprozessschritt") + ": " + gaerungsprozessschritt.getSchritt());

        Wein wein = charge.getWein();
        weinLabel.setText(App.getResourceBundle().getString("wein") + ": " + wein.getBeschreibung());
        weinartLabel.setText(App.getResourceBundle().getString("weinart") + ": " + wein.getWeinart());

        Set<GaerungsprozessschritteHasHefen> gaerungsprozessschritteHasHefen
                = gaerungsprozessschritt.getHefenPivot();

        gaerungsprozessschritteHasHefen.forEach(g ->
                hefeSollList.getItems().add(new HefeMenge(g.getHefe(), g.getMenge())));
    }
}

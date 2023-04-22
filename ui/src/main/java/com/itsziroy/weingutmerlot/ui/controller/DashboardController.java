package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.UeberpruefungManager;
import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;
import com.itsziroy.weingutmerlot.backend.helper.UpcomingUeberpruefung;
import com.itsziroy.weingutmerlot.ui.App;
import com.itsziroy.weingutmerlot.ui.Enums.View;
import com.itsziroy.weingutmerlot.ui.components.ChargeButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardController {

    @FXML
    private Label noUeberpruefungLabel;
    @FXML
    private MFXListView<ChargeButton> listView;
    @FXML
    private MFXComboBox<Locale> languageSelection;

    @FXML
    public void initialize() {

        initalizeUpcomingUeberpruefungen();
        initializeLanguageSelection();
    }

    private void initializeLanguageSelection() {
        ObservableList<Locale> options = FXCollections.observableArrayList(Locale.GERMAN, Locale.ENGLISH, Locale.FRENCH);
        languageSelection.setItems(options);
        languageSelection.getSelectionModel().selectItem(App.locale);

        // The Converter transforms the locale so a proper name for the locale is displayed in the format English, German, French,...
        languageSelection.setConverter(new StringConverter<>() {
            @Override
            public String toString(Locale locale) {
                if (locale != null) {
                    return locale.getDisplayLanguage();
                }
                return "";
            }

            @Override
            public Locale fromString(String string) {
                return new Locale.Builder().setLanguage(string).build();
            }
        });

    }

    private void initalizeUpcomingUeberpruefungen() {
        List<UpcomingUeberpruefung> nextUe =
                UeberpruefungManager.getUpcomingUeberpruefungen();

        if (nextUe.isEmpty()) {
            listView.setVisible(false);
            noUeberpruefungLabel.setVisible(true);
        }

        // Sort the list by date
        nextUe.sort(Comparator.comparing(UpcomingUeberpruefung::nextDate));

        nextUe.forEach(p -> {
            Charge charge = p.charge();
            Date date = p.nextDate();
            Ueberpruefung ue = p.lastUeberpruefung();

            ChargeButton button = new ChargeButton(charge, date, ue);

            listView.getItems().add(button);
        });
    }

    public void handleLanguageChange() {
        Locale selection = languageSelection.getSelectionModel().getSelectedItem();

        if (selection != App.locale) {
            App.locale = selection;
            App.setView(View.DASHBOARD);
        }
    }
}

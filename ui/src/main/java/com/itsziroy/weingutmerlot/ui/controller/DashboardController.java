package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.UebererpruefungService;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;
import com.itsziroy.weingutmerlot.backend.helper.UpcomingUeberpruefung;
import com.itsziroy.weingutmerlot.ui.components.ChargeButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DashboardController extends Controller {

    @FXML
    private MFXListView<ChargeButton> listView;
    @FXML
    private Label noUeberpruefungLabel;


    @FXML
    public void initialize() {
        initalizeUpcomingUeberpruefungen();
    }

    private void initalizeUpcomingUeberpruefungen() {

        UebererpruefungService uebererpruefungService = DB.getUeberpruefungManager();
        List<UpcomingUeberpruefung> nextUe =
                uebererpruefungService.getUpcomingUeberpruefungen();

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

}

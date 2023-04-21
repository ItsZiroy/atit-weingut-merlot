package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.UeberpruefungManager;
import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;
import com.itsziroy.weingutmerlot.backend.helper.UpcomingUeberpruefung;
import com.itsziroy.weingutmerlot.ui.components.ChargeButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DashboardController {

  @FXML
  private MFXListView<ChargeButton> listView;

  @FXML
  public void initialize() {

    List<UpcomingUeberpruefung> nextUe =
            UeberpruefungManager.getUpcomingUeberpruefungen();

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

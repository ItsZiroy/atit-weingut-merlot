package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.UeberpruefungManager;
import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;
import com.itsziroy.weingutmerlot.ui.components.ChargeButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;
import org.javatuples.Triplet;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DashboardController {

  @FXML
  private MFXListView<ChargeButton> listView;

  @FXML
  public void initialize() {

    List<Triplet<Charge, Date, Ueberpruefung>> nextUe =
            UeberpruefungManager.getUpcomingUeberpruefungen();

    // Sort the list by date
    nextUe.sort(Comparator.comparing(Triplet::getValue1));

    nextUe.forEach(p -> {
      Charge charge = p.getValue0();
      Date date = p.getValue1();
      Ueberpruefung ue = p.getValue2();

      ChargeButton button = new ChargeButton(charge, date, ue);

      listView.getItems().add(button);
    });
  }

}

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

    List<Triplet<Charge, Date, Ueberpruefung>> nextUe = UeberpruefungManager.getUpcomingUeberpruefungen();

    nextUe.sort(Comparator.comparing(Triplet::getValue1));

    nextUe.forEach(p -> {
      ChargeButton button = new ChargeButton(p);
      listView.getItems().add(button);
    });
  }

}

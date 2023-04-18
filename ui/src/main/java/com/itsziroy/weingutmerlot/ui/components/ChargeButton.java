package com.itsziroy.weingutmerlot.ui.components;

import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;
import com.itsziroy.weingutmerlot.ui.App;
import com.itsziroy.weingutmerlot.ui.Enums.View;
import com.itsziroy.weingutmerlot.ui.controller.UeberpruefungController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Date;

public class ChargeButton extends MFXButton {

  private Charge charge;
  private Date date;
  private Ueberpruefung ueberpruefung;
  public ChargeButton(Triplet<Charge, Date, Ueberpruefung> triplet) {
    this.charge = triplet.getValue0();
    this.date = triplet.getValue1();
    this.ueberpruefung = triplet.getValue2();

    this.setText(date.toString());

    this.setOnAction(this::onUeberpruefungButtonClick);
  }
  private void onUeberpruefungButtonClick(ActionEvent e) {
    System.out.println(charge.getId());


    Pair<Parent, FXMLLoader> pair = App.loadView(View.UEBERPRUEFUNG);

    Parent root = pair.getValue0();
    FXMLLoader loader = pair.getValue1();

    UeberpruefungController controller = loader.getController();

    controller.initializeData(ueberpruefung.getGaerungsprozessschritt().getNextProzessschritt(), charge);

    App.setRoot(root);



  }
}

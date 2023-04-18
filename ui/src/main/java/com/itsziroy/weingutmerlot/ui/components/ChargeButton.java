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

import java.util.Date;

public class ChargeButton extends MFXButton {

  private Charge charge;
  private Date date;
  private Ueberpruefung ueberpruefung;

  /**
   * The ChargeButton Component
   *
   * @param charge Charge
   * @param date Date to display
   * @param ueberpruefung The last Überprüfung that ran
   */
  public ChargeButton(Charge charge, Date date, Ueberpruefung ueberpruefung) {
    this.charge = charge;
    this.date = date;
    this.ueberpruefung = ueberpruefung;

    this.setText(date.toString());

    this.setOnAction(this::onUeberpruefungButtonClick);
  }

  /**
   * Loads {@link UeberpruefungController} View and initializes it
   * with the desired data.
   * @param e ActionEvent
   */
  private void onUeberpruefungButtonClick(ActionEvent e) {
    Pair<Parent, FXMLLoader> pair = App.loadView(View.UEBERPRUEFUNG);

    Parent root = pair.getValue0();
    FXMLLoader loader = pair.getValue1();

    UeberpruefungController controller = loader.getController();

    controller.initializeData(ueberpruefung.getGaerungsprozessschritt().getNextProzessschritt(), charge);

    App.setRoot(root);



  }
}

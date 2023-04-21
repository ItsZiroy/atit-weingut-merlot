package com.itsziroy.weingutmerlot.ui.components;

import com.itsziroy.weingutmerlot.backend.ChargeManager;
import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;
import com.itsziroy.weingutmerlot.ui.App;
import com.itsziroy.weingutmerlot.ui.Enums.View;
import com.itsziroy.weingutmerlot.ui.controller.UeberpruefungController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.javatuples.Pair;

import java.text.SimpleDateFormat;
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
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM");
    String dateString = simpleDateFormat.format(date);
    int chargeId = charge.getId();
    Gaerungsprozessschritt current = ChargeManager.getCurrentGaerungsprozessschritt(charge);
    int gaerungsprozessschrittId = current.getSchritt();
    String chargeString = App.resourceBundle.getString("charge");
    String gaerungsprozessschritt = App.resourceBundle.getString("gaerungsprozessschritt");
    this.setText(dateString + ", " + chargeString + ": " + chargeId +", " + gaerungsprozessschritt + ": " + gaerungsprozessschrittId);

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

    Gaerungsprozessschritt current = ueberpruefung.getGaerungsprozessschritt();
    Gaerungsprozessschritt next = current.getNextProzessschritt();
    controller.initializeData(next, charge);

    App.setRoot(root);



  }
}

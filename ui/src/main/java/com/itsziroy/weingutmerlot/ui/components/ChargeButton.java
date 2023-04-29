package com.itsziroy.weingutmerlot.ui.components;

import com.itsziroy.weingutmerlot.backend.ChargeService;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;
import com.itsziroy.weingutmerlot.ui.App;
import com.itsziroy.weingutmerlot.ui.View;
import com.itsziroy.weingutmerlot.ui.controller.UeberpruefungController;
import com.itsziroy.weingutmerlot.ui.helper.LoadedView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChargeButton extends MFXButton {

    private Charge charge;
    private Gaerungsprozessschritt currentGaerungsprozessschritt;
    private Date date;
    private Ueberpruefung ueberpruefung;

    /**
     * The ChargeButton Component
     *
     * @param charge        Charge
     * @param date          Date to display
     * @param ueberpruefung The last Überprüfung that ran
     */
    public ChargeButton(Charge charge, Date date, Ueberpruefung ueberpruefung) {
        this.charge = charge;
        this.date = date;
        this.ueberpruefung = ueberpruefung;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM");
        String dateString = simpleDateFormat.format(date);
        int chargeId = charge.getId();

        ChargeService chargeService = DB.getChargeManager();
        // If the next step is not reached, another Überprüfung is created
        // for the same Gärungsprozessschritt again
        currentGaerungsprozessschritt = chargeService.getCurrentGaerungsprozessschritt(charge);
        int gaerungsprozessschrittId = currentGaerungsprozessschritt.getSchritt();
        String chargeString = App.getResourceBundle().getString("charge");
        String gaerungsprozessschritt = App.getResourceBundle().getString("gaerungsprozessschritt");
        this.setText(dateString + ", " + chargeString + ": " + chargeId + ", " + gaerungsprozessschritt + ": " + gaerungsprozessschrittId);

        this.setOnAction(this::onUeberpruefungButtonClick);
    }

    /**
     * Loads {@link UeberpruefungController} View and initializes it
     * with the desired data.
     *
     * @param e ActionEvent
     */
    private void onUeberpruefungButtonClick(ActionEvent e) {
        LoadedView loadedView = App.loadView(View.UEBERPRUEFUNG);

        Parent root = loadedView.parent();
        FXMLLoader loader = loadedView.loader();

        UeberpruefungController controller = loader.getController();

        controller.initializeData(currentGaerungsprozessschritt, charge);


        App.setRoot(root);

    }
}

package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.Wein;
import com.itsziroy.weingutmerlot.backend.db.entities.Weinart;
import com.itsziroy.weingutmerlot.backend.db.entities.enums.Suessegrad;
import com.itsziroy.weingutmerlot.ui.App;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.persistence.PersistenceException;
import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class WeinController extends Controller{

    @FXML
    private MFXSlider alkoholgehaltSlider;
    @FXML
    private MFXTextField descriptionInput;
    @FXML
    private MFXFilterComboBox<Suessegrad> suessegradCombobox;
    @FXML
    private MFXFilterComboBox<Weinart> weinartCombobox;

    public void handleCreateButtonPress() {
        Wein wein = new Wein();

        String description = descriptionInput.getText();
        Double alkohol = alkoholgehaltSlider.getValue();
        Suessegrad suessegrad = suessegradCombobox.getValue();
        Weinart weinart = weinartCombobox.getValue();

        wein.setBeschreibung(description);
        wein.setAlkoholgehalt(alkohol);
        wein.setSuessegrad(suessegrad);
        wein.setWeinart(weinart);

        try {
            DB.getEntityManager().getTransaction().begin();
            DB.getEntityManager().persist(wein);
            DB.getEntityManager().getTransaction().commit();
        } catch (PersistenceException e) {
            LogManager.getLogger().error("Datenbank-Transaktion fehlgeschlagen");
            App.error(App.getResourceBundle().getString("errorDatenbank"));
        }

    }

    @Override
    public void initialize() {
        this.initializeWeinartComboxbox();
        this.initializeSuessegradCombobox();
    }

    private void initializeWeinartComboxbox() {
        var query = DB.getEntityManager().createQuery("select p from Weinart p", Weinart.class);
        List<Weinart> weinarten = query.getResultList();
        for (var weinart : weinarten) {
            weinartCombobox.getItems().add(weinart);
        }
    }

    private void initializeSuessegradCombobox() {
        for (var suessegrad : Suessegrad.values()) {
            suessegradCombobox.getItems().add(suessegrad);
        }
    }

}

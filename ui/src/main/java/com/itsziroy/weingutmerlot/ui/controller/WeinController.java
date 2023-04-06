package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.backend.Entities.Enums.Suessegrad;
import com.itsziroy.weingutmerlot.backend.Entities.Wein;
import com.itsziroy.weingutmerlot.backend.Entities.Weinart;
import com.itsziroy.weingutmerlot.backend.db.DB;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.persistence.PersistenceException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class WeinController {

  @FXML
  MFXSlider alkoholgehaltSlider;

  @FXML
  MFXTextField descriptionInput;

  @FXML
  MFXFilterComboBox<Weinart> weinartCombobox;

  @FXML
  MFXFilterComboBox<Suessegrad> suessegradCombobox;
  @FXML
  public void initialize() {
    this.initializeWeinartComboxbox();
    this.initializeSuessegradCombobox();
  }

  public void handleCreateButtonPress(MouseEvent event) {
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
      LogManager.getLogger().error("Datenbank Transaktion Fehlgeschlagen");
    }


  }

  private void initializeWeinartComboxbox() {
    var query = DB.getEntityManager().createQuery("select p from Weinart p", Weinart.class);
    List<Weinart> weinarten = query.getResultList();
    for (var weinart:
            weinarten) {
      weinartCombobox.getItems().add(weinart);
    }
  }

  private void initializeSuessegradCombobox() {
    for (var suessegrad: Suessegrad.values()) {
      suessegradCombobox.getItems().add(suessegrad);
    }
  }

}

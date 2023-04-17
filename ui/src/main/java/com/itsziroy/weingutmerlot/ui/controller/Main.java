package com.itsziroy.weingutmerlot.ui.controller;

import com.itsziroy.weingutmerlot.ui.App;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.util.StringConverter;

import java.util.Locale;

public class Main {

  public MFXComboBox<Locale> languageSelection;
  @FXML
  public void initialize() {
    initializeLanguageSelection();
  }

  private void initializeLanguageSelection() {
    ObservableList<Locale> options = FXCollections.observableArrayList(Locale.GERMAN, Locale.ENGLISH, Locale.FRENCH);
    languageSelection.setItems(options);
    languageSelection.getSelectionModel().selectItem(App.locale);

    // Set Display converter for Language Selection
    languageSelection.setConverter(new StringConverter<>() {
      @Override
      public String toString(Locale locale) {
        if(locale != null) {
          return locale.getDisplayLanguage();
        }
        return "";
      }

      @Override
      public Locale fromString(String string) {
        return new Locale.Builder().setLanguage(string).build();
      }
    });

  }

  public void weinCreateButtonClicked() {
    App.loadView("/views/create/wein.fxml");
  }

  public void weinOverviewButtonClicked() {
    App.loadView("/views/read/wein.fxml");
  }

  public void handleLanguageChange() {
    Locale selection = languageSelection.getSelectionModel().getSelectedItem();

    if (selection != App.locale) {
      App.locale = selection;
      App.loadView("/views/Main.fxml");
    }
  }
}

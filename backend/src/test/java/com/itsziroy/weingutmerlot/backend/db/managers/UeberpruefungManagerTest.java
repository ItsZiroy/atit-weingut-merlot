package com.itsziroy.weingutmerlot.backend.db.managers;

import com.itsziroy.weingutmerlot.backend.UebererpruefungService;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.*;
import com.itsziroy.weingutmerlot.backend.db.entities.pivot.UeberpruefungenHasHefen;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UeberpruefungManagerTest {

    static UebererpruefungService ueberpruefungService;

    // Idea: Gärungsprozess (including Wein)-> Gärungsprozessschritte -> Charge -> Überprüfungen
    @BeforeAll
    static void setUp() {
        ueberpruefungService = DB.getUeberpruefungManager();
    }

    @Test
    void validateActualAlcoholNotNegative() {
        Ueberpruefung ueberpruefung = new Ueberpruefung();
        ueberpruefung.setIstZucker(200);
        ueberpruefung.setIstAlkohol(-1.0);
        assertThrows(IllegalArgumentException.class, () -> ueberpruefungService.validate(ueberpruefung));
    }

    @Test
    void validateActualSugarNotNegative() {
        Ueberpruefung ueberpruefung = new Ueberpruefung();
        ueberpruefung.setIstZucker(-1);
        assertThrows(IllegalArgumentException.class, () -> ueberpruefungService.validate(ueberpruefung));
    }

    @Test
    void validateAdaptionSugarNotNegative() {
        Ueberpruefung ueberpruefung = new Ueberpruefung();
        ueberpruefung.setIstZucker(200);
        ueberpruefung.setIstAlkohol(5.0);
        ueberpruefung.setAnpassungZucker(-1);
        assertThrows(IllegalArgumentException.class, () -> ueberpruefungService.validate(ueberpruefung));
    }

    @Test
    void validateHasHefenAdaptionNotNegative() {
        UeberpruefungenHasHefen ueberpruefungenHasHefen = new UeberpruefungenHasHefen();
        ueberpruefungenHasHefen.setAnpassung(-1.0);
        assertThrows(IllegalArgumentException.class, () -> ueberpruefungService.validateHasHefen(ueberpruefungenHasHefen));
    }

    @Test
    void validateNextDateNotBeforeToday() {
        Ueberpruefung ueberpruefung = new Ueberpruefung();
        ueberpruefung.setIstZucker(200);
        ueberpruefung.setIstAlkohol(5.0);
        ueberpruefung.setAnpassungZucker(5);
        // Date must be before nextDate, so we test whether both dates are before the current date
        // otherwise it would be a duplication of the test validateNextDateNotBeforeUeberpruefungDate
        ueberpruefung.setDatum(new Date(98, Calendar.DECEMBER, 24));
        ueberpruefung.setNextDate(new Date(99, Calendar.JANUARY, 1));
        assertThrows(IllegalArgumentException.class, () -> ueberpruefungService.validate(ueberpruefung));
    }

    @Test
    void validateNextDateNotBeforeUeberpruefungDate() {
        Ueberpruefung ueberpruefung = new Ueberpruefung();
        ueberpruefung.setIstZucker(200);
        ueberpruefung.setIstAlkohol(5.0);
        ueberpruefung.setAnpassungZucker(5);
        ueberpruefung.setDatum(new Date(2023, Calendar.APRIL, 29));
        ueberpruefung.setNextDate(new Date(99, Calendar.JANUARY, 1));
        assertThrows(IllegalArgumentException.class, () -> ueberpruefungService.validate(ueberpruefung));
    }

    @Test
    void validateNoNextDateOnNextStep() {
        Ueberpruefung ueberpruefung = new Ueberpruefung();
        ueberpruefung.setIstZucker(200);
        ueberpruefung.setIstAlkohol(5.0);
        ueberpruefung.setAnpassungZucker(5);
        ueberpruefung.setDatum(new Date(99, Calendar.JANUARY, 1));
        ueberpruefung.setNextDate(new Date(99, Calendar.JANUARY, 2));
        ueberpruefung.setNaechsterSchritt(true);
        assertThrows(IllegalArgumentException.class, () -> ueberpruefungService.validate(ueberpruefung));
    }
}
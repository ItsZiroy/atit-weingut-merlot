package com.itsziroy.weingutmerlot.backend.db.managers;

import com.itsziroy.weingutmerlot.backend.UebererpruefungService;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.*;
import com.itsziroy.weingutmerlot.backend.db.entities.pivot.UeberpruefungenHasHefen;
import com.itsziroy.weingutmerlot.backend.helper.UpcomingUeberpruefung;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UeberpruefungManagerTest {

    static Ueberpruefung ueberpruefung1, ueberpruefung2;
    static Charge charge;
    static Gaerungsprozessschritt[] gaerungsprozessschritte;

    static UebererpruefungService ueberpruefungService;
    // Idea: Gärungsprozess (including Wein)-> Gärungsprozessschritte -> Charge -> Überprüfungen
    @BeforeAll
    static void setUp() {
        DB.setPersistenceUnit("test");

        ueberpruefungService = DB.getUeberpruefungManager();

        System.out.println(ueberpruefungService);
        Gaerungsprozess gaerungsprozess = GaerungsprozessTest.createRandomGaerungsprozess(true);

        // Get Wein of the Gärungsprozess
        Wein wein = gaerungsprozess.getWein();

        // Create 3 Gärungsprozessschritte for the Gärungsprozess
        gaerungsprozessschritte = GaerungsprozessschrittTest.createGaerungsprozessschritteForGaerungsprozess(true, gaerungsprozess, 3);

        // Create new random Charge for the Wein
        charge = ChargeTest.createRandomChargeForWein(true, wein);


        // Create not accepted Überprüfung for Gärungsprozessschritt 2
        ueberpruefung1 = new Ueberpruefung();
        ueberpruefung1.setCharge(charge);
        Date date = new Date(2023, Calendar.APRIL, 20);
        ueberpruefung1.setDatum(date);
        ueberpruefung1.setGaerungsprozessschritt(gaerungsprozessschritte[1]);
        ueberpruefung1.setNaechsterSchritt(false);
        ueberpruefung1.setIstAlkohol(5.0);
        ueberpruefung1.setIstTemperatur(25.0);
        ueberpruefung1.setIstZucker(150);
        ueberpruefung1.setAnpassungTemperatur(0.0);
        ueberpruefung1.setAnpassungZucker(0);
        Date nextDate = new Date(2023, Calendar.APRIL, 25);
        ueberpruefung1.setNextDate(nextDate);

        // Create a accepted Überprüfung for Gärungssschritt 2
        ueberpruefung2 = new Ueberpruefung();
        ueberpruefung2.setCharge(charge);
        date = new Date(2023, Calendar.APRIL, 25);
        ueberpruefung2.setDatum(date);
        ueberpruefung2.setGaerungsprozessschritt(gaerungsprozessschritte[1]);
        ueberpruefung2.setNaechsterSchritt(true);
        ueberpruefung2.setIstAlkohol(5.0);
        ueberpruefung2.setIstTemperatur(25.0);
        ueberpruefung2.setIstZucker(150);
        ueberpruefung2.setAnpassungTemperatur(0.0);
        ueberpruefung2.setAnpassungZucker(0);

        DB.getEntityManager().getTransaction().begin();
        DB.getEntityManager().persist(ueberpruefung1);
        DB.getEntityManager().persist(ueberpruefung2);
        DB.getEntityManager().getTransaction().commit();

    }

    @Test
    void getUpcomingUeberpruefungen() {
        // actual
        List<UpcomingUeberpruefung> actualList = ueberpruefungService.getUpcomingUeberpruefungen();
        UpcomingUeberpruefung actual = actualList.get(0);

        // expected
        // the current Gärungsprozesschritt should be the 3rd
        // the current date should therefore be 10 days after April 25th
        Date expectedDate = new Date(2023, Calendar.MAY, 5);
        UpcomingUeberpruefung expected = new UpcomingUeberpruefung(charge, expectedDate, ueberpruefung2);
        assertEquals(expected, actual);
    }

    @Test
    void getNextUeberpruefungDateAcceptedNextStep() {
       Date actual = ueberpruefungService.getNextUeberpruefungDate(ueberpruefung2);
       // Date of ueberpruefung1 is 25.04.2023, Gärungsprozesschritt 3 shall be tested again after 10 days
       Date expected = new Date(2023, Calendar.MAY, 5);
       assertEquals(expected, actual);
    }
    @Test
    void getNextUeberpruefungDateDeclinedNextStep() {
        Date actual = ueberpruefungService.getNextUeberpruefungDate(ueberpruefung1);
        // Date of ueberpruefung2 is 20.04.2023, Gärungsprozesschritt 3 shall be tested again after 5 days
        Date expected =  new Date(2023, Calendar.APRIL, 25);
        assertEquals(expected, actual);
    }

    @Test
    void getCurrentUeberpruefung() {
        Ueberpruefung actual = ueberpruefungService.getCurrentUeberpruefung(charge);
        Ueberpruefung expected = ueberpruefung2;
        assertEquals(expected, actual);
    }

    @Test
    void validateActualSugarNotNegative() {
        Ueberpruefung ueberpruefung = new Ueberpruefung();
        ueberpruefung.setIstZucker(-1);
        assertThrows(IllegalArgumentException.class, () -> ueberpruefungService.validate(ueberpruefung));
    }

    @Test
    void validateActualAlcoholNotNegative() {
        Ueberpruefung ueberpruefung = new Ueberpruefung();
        ueberpruefung.setIstZucker(200);
        ueberpruefung.setIstAlkohol(-1.0);
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
    void validateNextDateNotBeforeToday() {
        Ueberpruefung ueberpruefung = new Ueberpruefung();
        ueberpruefung.setIstZucker(200);
        ueberpruefung.setIstAlkohol(5.0);
        ueberpruefung.setAnpassungZucker(5);
        // Date must be before nextDate, so we test whether both dates are before the current date
        // otherwise it would be a duplication of the test validateNextDateNotBeforeUeberpruefungDate
        ueberpruefung.setDatum(new Date(98, Calendar.DECEMBER, 24));
        ueberpruefung.setNextDate(new Date(99, Calendar.JANUARY,1));
        assertThrows(IllegalArgumentException.class, () -> ueberpruefungService.validate(ueberpruefung));
    }

    @Test
    void validateNextDateNotBeforeUeberpruefungDate() {
        Ueberpruefung ueberpruefung = new Ueberpruefung();
        ueberpruefung.setIstZucker(200);
        ueberpruefung.setIstAlkohol(5.0);
        ueberpruefung.setAnpassungZucker(5);
        ueberpruefung.setDatum(new Date(2023, Calendar.APRIL, 29));
        ueberpruefung.setNextDate(new Date(99, Calendar.JANUARY,1));
        assertThrows(IllegalArgumentException.class, () -> ueberpruefungService.validate(ueberpruefung));
    }

    @Test
    void validateNoNextDateOnNextStep() {
        Ueberpruefung ueberpruefung = new Ueberpruefung();
        ueberpruefung.setIstZucker(200);
        ueberpruefung.setIstAlkohol(5.0);
        ueberpruefung.setAnpassungZucker(5);
        ueberpruefung.setDatum(new Date(99, Calendar.JANUARY, 1));
        ueberpruefung.setNextDate(new Date(99, Calendar.JANUARY,2));
        ueberpruefung.setNaechsterSchritt(true);
        assertThrows(IllegalArgumentException.class, () -> ueberpruefungService.validate(ueberpruefung));
    }

    @Test
    void validateHasHefenAdaptionNotNegative() {
        UeberpruefungenHasHefen ueberpruefungenHasHefen = new UeberpruefungenHasHefen();
        ueberpruefungenHasHefen.setAnpassung(-1.0);
        assertThrows(IllegalArgumentException.class, ()-> ueberpruefungService.validateHasHefen(ueberpruefungenHasHefen));
    }
}
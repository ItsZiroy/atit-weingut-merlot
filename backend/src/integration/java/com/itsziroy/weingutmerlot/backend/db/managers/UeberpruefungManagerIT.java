package com.itsziroy.weingutmerlot.backend.db.managers;

import com.itsziroy.weingutmerlot.backend.UebererpruefungService;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.*;
import com.itsziroy.weingutmerlot.backend.helper.UpcomingUeberpruefung;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UeberpruefungManagerIT {

    static Charge charge;
    static Gaerungsprozessschritt[] gaerungsprozessschritte;
    static Ueberpruefung ueberpruefung1, ueberpruefung2;
    static UebererpruefungService ueberpruefungService;

    // Idea: Gärungsprozess (including Wein)-> Gärungsprozessschritte -> Charge -> Überprüfungen
    @BeforeAll
    static void setUp() {
        DB.setPersistenceUnit("test2");

        ueberpruefungService = DB.getUeberpruefungManager();

        System.out.println(ueberpruefungService);
        Gaerungsprozess gaerungsprozess = GaerungsprozessIT.createRandomGaerungsprozess(true);

        // Get Wein of the Gärungsprozess
        Wein wein = gaerungsprozess.getWein();

        // Create 3 Gärungsprozessschritte for the Gärungsprozess
        gaerungsprozessschritte = GaerungsprozessschrittIT.createGaerungsprozessschritteForGaerungsprozess(true, gaerungsprozess, 3);

        // Create new random Charge for the Wein
        charge = ChargeIT.createRandomChargeForWein(true, wein);


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
    void getCurrentUeberpruefung() {
        Ueberpruefung actual = ueberpruefungService.getCurrentUeberpruefung(charge);
        Ueberpruefung expected = ueberpruefung2;
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
        Date expected = new Date(2023, Calendar.APRIL, 25);
        assertEquals(expected, actual);
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
}
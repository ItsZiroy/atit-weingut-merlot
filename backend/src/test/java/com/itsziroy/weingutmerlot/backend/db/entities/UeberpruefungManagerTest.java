package com.itsziroy.weingutmerlot.backend.db.entities;

import com.itsziroy.weingutmerlot.backend.UebererpruefungService;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.helper.UpcomingUeberpruefung;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UeberpruefungManagerTest {

    static Ueberpruefung ueberpruefung1, ueberpruefung2, ueberpruefung3;
    static Charge charge;
    static Gaerungsprozessschritt[] gaerungsprozessschritte;

    static UebererpruefungService uebererpruefungService;
    // Idea: Gärungsprozess (including Wein)-> Gärungsprozessschritte -> Charge -> Überprüfungen
    @BeforeAll
    static void setUp() {
        DB.setPersistenceUnit("test");

        uebererpruefungService = DB.getUeberpruefungManager();

        System.out.println(uebererpruefungService);
        Gaerungsprozess gaerungsprozess = GaerungsprozessTest.createRandomGaerungsprozess(true);

        // Get Wein of the Gärungsprozess
        Wein wein = gaerungsprozess.getWein();

        // Create 3 Gärungsprozessschritte for the Gärungsprozess
        gaerungsprozessschritte = GaerungsprozessschrittTest.createNGaerungsprozessschritteForGaerungsprozess(true, gaerungsprozess, 3);

        // Create new random Charge for the Wein
        charge = ChargeTest.createRandomChargeForWein(true, wein);

        // Create accepted Überprüfung for Gärungsprozessschritt 1
        ueberpruefung1 = new Ueberpruefung();
        ueberpruefung1.setCharge(charge);
        Date date = new Date(2023, Calendar.APRIL, 10);
        ueberpruefung1.setDatum(date);
        ueberpruefung1.setGaerungsprozessschritt(gaerungsprozessschritte[0]);
        ueberpruefung1.setNaechsterSchritt(true);
        ueberpruefung1.setIstAlkohol(2.0);
        ueberpruefung1.setIstTemperatur(25.0);
        ueberpruefung1.setIstZucker(200);
        ueberpruefung1.setAnpassungTemperatur(0.0);
        ueberpruefung1.setAnpassungZucker(0);

        // Create not accepted Überprüfung for Gärungsprozessschritt 2
        ueberpruefung2 = new Ueberpruefung();
        ueberpruefung2.setCharge(charge);
        date = new Date(2023, Calendar.APRIL, 20);
        ueberpruefung2.setDatum(date);
        ueberpruefung2.setGaerungsprozessschritt(gaerungsprozessschritte[1]);
        ueberpruefung2.setNaechsterSchritt(false);
        ueberpruefung2.setIstAlkohol(5.0);
        ueberpruefung2.setIstTemperatur(25.0);
        ueberpruefung2.setIstZucker(150);
        ueberpruefung2.setAnpassungTemperatur(0.0);
        ueberpruefung2.setAnpassungZucker(0);
        Date nextDate = new Date(2023, Calendar.APRIL, 25);
        ueberpruefung2.setNextDate(nextDate);

        // Create a accepted Überprüfung for Gärungssschritt 2
        ueberpruefung3 = new Ueberpruefung();
        ueberpruefung3.setCharge(charge);
        date = new Date(2023, Calendar.APRIL, 25);
        ueberpruefung3.setDatum(date);
        ueberpruefung3.setGaerungsprozessschritt(gaerungsprozessschritte[1]);
        ueberpruefung3.setNaechsterSchritt(true);
        ueberpruefung3.setIstAlkohol(5.0);
        ueberpruefung3.setIstTemperatur(25.0);
        ueberpruefung3.setIstZucker(150);
        ueberpruefung3.setAnpassungTemperatur(0.0);
        ueberpruefung3.setAnpassungZucker(0);

        DB.getEntityManager().getTransaction().begin();
        DB.getEntityManager().persist(ueberpruefung1);
        DB.getEntityManager().persist(ueberpruefung2);
        DB.getEntityManager().persist(ueberpruefung3);
        DB.getEntityManager().getTransaction().commit();

        System.out.println(ueberpruefung2.getNextDate());

    }


    @Test
    void getUpcomingUeberpruefungen() {
        // actual
        List<UpcomingUeberpruefung> actualList = uebererpruefungService.getUpcomingUeberpruefungen();
        UpcomingUeberpruefung actual = actualList.get(0);

        // expected
        // the current Gärungsprozesschritt should be the 3rd
        // the current date should therefore be 10 days after April 25th
        Date expectedDate = new Date(2023, Calendar.MAY, 5);
        UpcomingUeberpruefung expected = new UpcomingUeberpruefung(charge, expectedDate, ueberpruefung3);
        assertEquals(expected, actual);
    }

    @Test
    void getNextUeberpruefungDateAcceptedNextStep() {
       Date actual = uebererpruefungService.getNextUeberpruefungDate(ueberpruefung1);
       // Date of ueberpruefung2 is 20.04.2023, Gärungsprozesschritt 3 shall be tested again after 5 days
       Date expected = new Date(2023, Calendar.APRIL, 20);
       assertEquals(expected, actual);
    }
    @Test
    void getNextUeberpruefungDateDeclinedNextStep() {
        Date actual = uebererpruefungService.getNextUeberpruefungDate(ueberpruefung2);
        // Date of ueberpruefung2 is 20.04.2023, Gärungsprozesschritt 3 shall be tested again after 5 days
        Date expected =  new Date(2023, Calendar.APRIL, 25);
        assertEquals(expected, actual);
    }

    @Test
    void getCurrentUeberpruefung() {
        Ueberpruefung actual = uebererpruefungService.getCurrentUeberpruefung(charge);
        Ueberpruefung expected = ueberpruefung3;
        assertEquals(expected, actual);
    }
}
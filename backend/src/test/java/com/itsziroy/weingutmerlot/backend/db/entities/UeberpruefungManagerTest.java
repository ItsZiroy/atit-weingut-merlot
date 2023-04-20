package com.itsziroy.weingutmerlot.backend.db.entities;

import com.itsziroy.weingutmerlot.backend.UeberpruefungManager;
import com.itsziroy.weingutmerlot.backend.db.DB;
import org.javatuples.Triplet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UeberpruefungManagerTest {

    static Ueberpruefung ueberpruefung1;
    static Ueberpruefung ueberpruefung2;
    static Charge charge;
    static Gaerungsprozessschritt[] gaerungsprozessschritte;
    // Idea: Gärungsprozess (including Wein)-> Gärungsprozessschritte -> Charge -> Überprüfungen
    @BeforeAll
    static void setUp() {
        boolean persist = true;
        Gaerungsprozess gaerungsprozess = GaerungsprozessTest.createRandomGaerungsprozess(true);

        // Create 4 Gärungsprozessschritte for the Gärungsprozess
        gaerungsprozessschritte = GaerungsprozessschrittTest.createNGaerungsprozessschritteForGaerungsprozess(true, gaerungsprozess, 3);

        // Get Wein of the Gärungsprozess
        Wein wein = gaerungsprozess.getWein();

        // Create new Charge for the Wein
        charge = ChargeTest.createRandomChargeForWein(true, wein);

        // Create done Überprüfung for Gärungsprozessschritt 1
        ueberpruefung1 = new Ueberpruefung();
        ueberpruefung1.setCharge(charge);
        Date date = convertStringToDate("10-04-2023");
        ueberpruefung1.setDatum(date);
        ueberpruefung1.setGaerungsprozessschritt(gaerungsprozessschritte[0]);
        ueberpruefung1.setNaechsterSchritt(true);
        ueberpruefung1.setIstAlkohol(2);
        ueberpruefung1.setIstTemperatur(25);
        ueberpruefung1.setIstZucker(200);
        ueberpruefung1.setAnpassungTemperatur(0);
        ueberpruefung1.setAnpassungZucker(0);

        // Create done Überprüfung for Gärungsprozessschritt 2
        ueberpruefung2 = new Ueberpruefung();
        ueberpruefung2.setCharge(charge);
        date = convertStringToDate("20-04-2023");
        ueberpruefung2.setDatum(date);
        ueberpruefung2.setGaerungsprozessschritt(gaerungsprozessschritte[1]);
        ueberpruefung2.setNaechsterSchritt(false);
        ueberpruefung2.setIstAlkohol(5);
        ueberpruefung2.setIstTemperatur(25);
        ueberpruefung2.setIstZucker(150);
        ueberpruefung2.setAnpassungTemperatur(0);
        ueberpruefung2.setAnpassungZucker(0);

        if (persist) {
            DB.getEntityManager().getTransaction().begin();
            DB.getEntityManager().persist(ueberpruefung1);
            DB.getEntityManager().persist(ueberpruefung2);
            DB.getEntityManager().getTransaction().commit();
        }

    }

    public static Date convertStringToDate(String stringDate){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = format.parse(stringDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    void getUpcomingUeberpruefungen() {
        List<Triplet<Charge,Date,Ueberpruefung>> actualList = UeberpruefungManager.getUpcomingUeberpruefungen();
        Triplet<Charge,Date,Ueberpruefung> actualTriplet = actualList.get(0);

        // expected
        Date expectedDate = convertStringToDate("30-04-2023");
        Triplet<Charge,Date,Ueberpruefung> expectedTriplet = new Triplet<>(charge, expectedDate, ueberpruefung2);
        assertEquals(expectedTriplet, actualTriplet);
    }

    @Test
    void getNextUeberpruefungDate() {
       Date actual = UeberpruefungManager.getNextUeberpruefungDate(ueberpruefung2);
       // Date of ueberpruefung2 is 30.04.2023, Gärungsprozesschritt 3 takes 10 days
       Date expected = convertStringToDate("30-04-2023");
       assertEquals(expected, actual);
    }

    @Test
    void getCurrentUeberpruefung() {
        Ueberpruefung actual = UeberpruefungManager.getCurrentUeberpruefung(charge);
        Ueberpruefung expected = ueberpruefung2;
        assertEquals(expected, actual);
    }
}
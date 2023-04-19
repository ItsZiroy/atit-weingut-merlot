package com.itsziroy.weingutmerlot.backend.db.entities;

import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UeberpruefungManagerTest {
    // Idea: Charge -> Gärungsprozess -> Gärungsprozessschritte -> Überprüfungen
    static void setUp(boolean persist) {
        Gaerungsprozess gaerungsprozess = GaerungsprozessTest.createRandomGaerungsprozess(true);
        Charge charge = ChargeTest.createRandomCharge(true);

        /*// Wein of the Charge shall get the Gärungsprozess
        Wein wein = charge.getWein();
        //wein.setGaehrungsprozess(gaerungsprozess);

        // Create 4 Gärungsprozessschritte for the Gärungsprozess
        Gaerungsprozessschritt[] gaerungsprozessschritte = GaerungsprozessschrittTest.createNGaerungsprozessschritteForGaerungsprozess(true, gaerungsprozess, 3);

        // create done Überprüfung for Gärungsprozessschritt 1
        Ueberpruefung ueberpruefung1 = new Ueberpruefung();
        ueberpruefung1.setCharge(charge);
        ueberpruefung1.setDatum(new Date("10.04.2003");
        ueberpruefung1.setGaerungsprozessschritt(gaerungsprozessschritte[0]);
        ueberpruefung1.setNaechsterSchritt(true);

        // create done Überprüfung for Gärungsprozessschritt 2
        Ueberpruefung ueberpruefung2 = new Ueberpruefung();
        ueberpruefung2.setCharge(charge);
        ueberpruefung2.setDatum(new Date("30.04.2003");
        ueberpruefung2.setGaerungsprozessschritt(gaerungsprozessschritte[1]);
        ueberpruefung2.setNaechsterSchritt(true);

        if (persist) {
            DB.getEntityManager().getTransaction().begin();
            DB.getEntityManager().persist(ueberpruefung1);
            DB.getEntityManager().persist(ueberpruefung2);
            DB.getEntityManager().getTransaction().commit();
        }*/

    }

    @Test
    void getUpcomingUeberpruefungen() {
    }

    @Test
    void getNextUeberpruefungDate() {
    }

    @Test
    void getCurrentUeberpruefung() {
    }
}
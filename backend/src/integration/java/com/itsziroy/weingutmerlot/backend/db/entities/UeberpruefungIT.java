package com.itsziroy.weingutmerlot.backend.db.entities;

import com.itsziroy.weingutmerlot.backend.db.DB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

public class UeberpruefungIT {

    public static Ueberpruefung createRandomUeberpruefung(boolean persist) {

        Ueberpruefung ueberpruefung = new Ueberpruefung();
        Charge charge = ChargeIT.createRandomCharge(persist);
        Gaerungsprozessschritt gaerungsprozessschritt = GaerungsprozessschrittIT.createRandomGaerungsprozessschritt(persist);

        ueberpruefung.setCharge(charge);
        Date date = new Date(2023, Calendar.APRIL, 25);
        ueberpruefung.setDatum(date);
        ueberpruefung.setGaerungsprozessschritt(gaerungsprozessschritt);
        ueberpruefung.setNaechsterSchritt(Math.random() < 0.5);
        ueberpruefung.setIstAlkohol(5.0);
        ueberpruefung.setIstTemperatur(25.0);
        ueberpruefung.setIstZucker(150);
        ueberpruefung.setAnpassungTemperatur(0.0);
        ueberpruefung.setAnpassungZucker(0);

        if (persist) {
            DB.getEntityManager().getTransaction().begin();
            DB.getEntityManager().persist(ueberpruefung);
            DB.getEntityManager().getTransaction().commit();
        }

        return ueberpruefung;
    }
    @BeforeAll
    static void setUp() {
        DB.setPersistenceUnit("test");
    }

    @Test
    void ueberpruefungPersistence() {
        Assertions.assertDoesNotThrow(() -> createRandomUeberpruefung(true));
    }

}

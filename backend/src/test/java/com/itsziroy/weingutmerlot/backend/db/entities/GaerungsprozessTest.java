package com.itsziroy.weingutmerlot.backend.db.entities;


import com.itsziroy.weingutmerlot.backend.db.DB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GaerungsprozessTest {

    @BeforeAll
    static void setUp() {
        DB.setPersistenceUnit("test");
    }

    @Test
    void gaerungsprozessPersistence() {
        Assertions.assertDoesNotThrow(() -> createRandomGaerungsprozess(true));
    }

    /**
     * Creates a random Gaerungsprozess including a Wein and optionally persists it
     * and all its dependencies to the test DB
     *
     * @param persist Persistence to DB
     * @return Gaerungsprozessschritt
     */
    public static Gaerungsprozess createRandomGaerungsprozess(boolean persist) {
        Gaerungsprozess gaerungsprozess = new Gaerungsprozess();
        gaerungsprozess.setLagerungsbehaelter("Holz");
        gaerungsprozess.setTemperatur((Math.random() * 100));
        gaerungsprozess.setZuckergehalt((int) (Math.random() * 100));
        gaerungsprozess.setDauer((int) (Math.random() * 100));

        Wein wein = WeinTest.createRandomWein(persist);

        gaerungsprozess.setWein(wein);

        if (persist) {
            DB.getEntityManager().getTransaction().begin();
            DB.getEntityManager().persist(gaerungsprozess);
            DB.getEntityManager().getTransaction().commit();
        }

        return gaerungsprozess;
    }

}
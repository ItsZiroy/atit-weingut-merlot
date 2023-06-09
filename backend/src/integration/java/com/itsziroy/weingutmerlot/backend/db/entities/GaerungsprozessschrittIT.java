package com.itsziroy.weingutmerlot.backend.db.entities;

import com.itsziroy.weingutmerlot.backend.db.DB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GaerungsprozessschrittIT {

    /**
     * A set of Gaerungsprozessschritte to use for Sibling testing
     */
    static Gaerungsprozessschritt[] schritte = {
            createRandomGaerungsprozessschritt(false),
            createRandomGaerungsprozessschritt(false),
            createRandomGaerungsprozessschritt(false)
    };

    /**
     * Creates the given number of Gärungsprozessschritte after <b>10</b> days for the given Gärungsprozess with
     * ascending number of Schritte
     *
     * @param persist         Persist in DB
     * @param gaerungsprozess Gärungsprizess
     * @param number          Number of steps to generate
     * @return Gärungsprozessschritte
     */
    public static Gaerungsprozessschritt[] createGaerungsprozessschritteForGaerungsprozess(boolean persist, Gaerungsprozess gaerungsprozess, int number) {
        Gaerungsprozessschritt[] gaerungsprozessschritte = new Gaerungsprozessschritt[number];
        for (int i = 1; i < number + 1; i++) {
            Gaerungsprozessschritt schritt = new Gaerungsprozessschritt();
            schritt.setNachZeit(10);
            schritt.setSollAlkohol((int) (Math.random() * 100));
            schritt.setSollTemperatur((int) (Math.random() * 100));
            schritt.setSollZucker((int) (Math.random() * 100));
            schritt.setSchritt(i);

            schritt.setGaerungsprozess(gaerungsprozess);

            gaerungsprozessschritte[i - 1] = schritt;

            if (persist) {
                DB.getEntityManager().getTransaction().begin();
                DB.getEntityManager().persist(schritt);
                DB.getEntityManager().getTransaction().commit();
            }
        }

        return gaerungsprozessschritte;
    }

    /**
     * This method runs before all tests of this class to create a
     * useful set of Entities that can be used throughout the rest of
     * the tests.
     */
    @BeforeAll
    static void setUp() {
        DB.setPersistenceUnit("test");

        Gaerungsprozess gaerungsprozess = GaerungsprozessIT.createRandomGaerungsprozess(false);

        DB.getEntityManager().getTransaction().begin();

        Wein wein = gaerungsprozess.getWein();
        Weinart weinart = wein.getWeinart();

        DB.getEntityManager().persist(weinart);
        DB.getEntityManager().persist(wein);
        DB.getEntityManager().persist(gaerungsprozess);


        for (int i = 0; i < schritte.length; i++) {
            schritte[i].setSchritt(i);
            schritte[i].setGaerungsprozess(gaerungsprozess);
            DB.getEntityManager().persist(schritte[i]);
        }

        DB.getEntityManager().getTransaction().commit();
    }

    @Test
    void gaerungsprozessSchrittPersistence() {
        Assertions.assertDoesNotThrow(() -> createRandomGaerungsprozessschritt(true));
    }

    /**
     * Creates a random Gaerungsprozessschritt and optionally persists it
     * and all its dependencies to the test DB
     *
     * @param persist Persistence to DB
     * @return Gaerungsprozessschritt
     */
    public static Gaerungsprozessschritt createRandomGaerungsprozessschritt(boolean persist) {
        Gaerungsprozessschritt schritt = new Gaerungsprozessschritt();
        schritt.setNachZeit((int) (Math.random() * 100));
        schritt.setSollAlkohol((int) (Math.random() * 100));
        schritt.setSollTemperatur((int) (Math.random() * 100));
        schritt.setSollZucker((int) (Math.random() * 100));
        schritt.setSchritt((int) (Math.random() * 100));

        Gaerungsprozess gaerungsprozess = GaerungsprozessIT.createRandomGaerungsprozess(persist);

        schritt.setGaerungsprozess(gaerungsprozess);

        if (persist) {
            DB.getEntityManager().getTransaction().begin();
            DB.getEntityManager().persist(schritt);
            DB.getEntityManager().getTransaction().commit();
        }

        return schritt;
    }

    @Test
    void getNextProzessschritt() {
        Gaerungsprozessschritt next = schritte[1].getNextProzessschritt();
        assertNotNull(next);
        assertEquals(next.getId(), schritte[2].getId());
    }

    @Test
    void getPreviousProzessschritt() {
        Gaerungsprozessschritt previous = schritte[1].getPreviousProzessschritt();
        assertNotNull(previous);
        assertEquals(previous.getId(), schritte[0].getId());
    }

    @Test
    void getSiblingProzessschrittByNumber() {
        Gaerungsprozessschritt sibling = schritte[0].getSiblingProzessschrittByNumber(2);
        assertNotNull(sibling);
        assertEquals(sibling.getId(), schritte[2].getId());
    }

    @Test
    void siblingDoesNotExist() {
        Gaerungsprozessschritt sibling = schritte[0].getSiblingProzessschrittByNumber(99999);
        assertNull(sibling);
    }
}
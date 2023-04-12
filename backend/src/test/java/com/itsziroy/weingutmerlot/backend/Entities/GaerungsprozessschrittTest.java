package com.itsziroy.weingutmerlot.backend.Entities;

import com.itsziroy.weingutmerlot.backend.db.DB;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GaerungsprozessschrittTest {

  /**
   * This method runs before all tests of this class to create a
   * useful set of Entities that can be used throughout the rest of
   * the tests.
   */
  @BeforeAll
  static void setUp() {

    DB.setPersistenceUnit("test");

    Gaerungsprozess gaerungsprozess = GaerungsprozessTest.createRandomGaerungsprozess(false);

    DB.getEntityManager().getTransaction().begin();

    DB.getEntityManager().persist(gaerungsprozess.getWein().getWeinart());
    DB.getEntityManager().persist(gaerungsprozess.getWein());
    DB.getEntityManager().persist(gaerungsprozess);


    for (int i = 0; i < schritte.length; i++) {
      schritte[i].setSchritt(i);
      schritte[i].setGaerungsprozess(gaerungsprozess);
      DB.getEntityManager().persist(schritte[i]);
    }

    DB.getEntityManager().getTransaction().commit();
  }

  /**
   * Creates a random Gaerungsprozessschritt and optionally persists it
   * and all its dependencies to the test DB
   *
   * @param persist Persistence to DB
   * @return Gaerungsprozessschritt
   */
  static Gaerungsprozessschritt createRandomGaerungsprozessschritt(boolean persist) {
    Gaerungsprozessschritt schritt = new Gaerungsprozessschritt();
    schritt.setNachZeit((int) (Math.random() * 100));
    schritt.setSollAlkohol((int) (Math.random() * 100));
    schritt.setSollTemperatur((int) (Math.random() * 100));
    schritt.setSollZucker((int) (Math.random() * 100));
    schritt.setSchritt((int) (Math.random() * 100));

    schritt.setGaerungsprozess(GaerungsprozessTest.createRandomGaerungsprozess(persist));

    if(persist) {
      DB.getEntityManager().getTransaction().begin();
      DB.getEntityManager().persist(schritt);
      DB.getEntityManager().getTransaction().commit();
    }

    return schritt;
  }

  static Gaerungsprozessschritt[] schritte = {
          createRandomGaerungsprozessschritt(false),
          createRandomGaerungsprozessschritt(false),
          createRandomGaerungsprozessschritt(false)
  };

  @Test
  void getPreviousProzessschritt() {
    Gaerungsprozessschritt previous = schritte[1].getPreviousProzessschritt();
    assertNotNull(previous);
    assertEquals(previous.getId(), schritte[0].getId());
  }

  @Test
  void getNextProzessschritt() {
    Gaerungsprozessschritt next = schritte[1].getNextProzessschritt();
    assertNotNull(next);
    assertEquals(next.getId(), schritte[2].getId());
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
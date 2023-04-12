package com.itsziroy.weingutmerlot.backend.Entities;


import com.itsziroy.weingutmerlot.backend.db.DB;

class GaerungsprozessTest {

  /**
   * Creates a random Gaerungsprozessschritt and optionally persists it
   * and all its dependencies to the test DB
   *
   * @param persist Persistence to DB
   * @return Gaerungsprozessschritt
   */
  static Gaerungsprozess createRandomGaerungsprozess(boolean persist) {
    Gaerungsprozess gaerungsprozess = new Gaerungsprozess();
    gaerungsprozess.setLagerungsbehaelter("Holz");
    gaerungsprozess.setTemperatur((int) (Math.random() * 100));
    gaerungsprozess.setZuckergehalt((int) (Math.random() * 100));
    gaerungsprozess.setDauer((int) (Math.random() * 100));

    gaerungsprozess.setWeine(WeinTest.createRandomWein(persist));

    if(persist) {
      DB.getEntityManager().getTransaction().begin();
      DB.getEntityManager().persist(gaerungsprozess);
      DB.getEntityManager().getTransaction().commit();
    }

    return gaerungsprozess;
  }
}
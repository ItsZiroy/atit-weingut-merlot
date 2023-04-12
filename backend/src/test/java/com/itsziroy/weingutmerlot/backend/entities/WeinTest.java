package com.itsziroy.weingutmerlot.backend.entities;

import com.itsziroy.weingutmerlot.backend.db.entities.Wein;
import com.itsziroy.weingutmerlot.backend.db.entities.Weinart;
import com.itsziroy.weingutmerlot.backend.db.entities.enums.Suessegrad;
import com.itsziroy.weingutmerlot.backend.db.DB;

class WeinTest {

  /**
   * Utitlity method for creating random Wein
   * @return Random Wein
   */
  static Wein createRandomWein(boolean persist) {
    Weinart weinart = new Weinart();
    weinart.setArt("Rotwein");
    weinart.setName("Test");

    Wein wein = new Wein();
    wein.setWeinart(weinart);
    wein.setSuessegrad(Suessegrad.FEINHERB);
    wein.setBeschreibung("Test");
    wein.setAlkoholgehalt(Math.random() * 100);

    if(persist) {
      DB.getEntityManager().getTransaction().begin();
      DB.getEntityManager().persist(weinart);
      DB.getEntityManager().persist(wein);
      DB.getEntityManager().getTransaction().commit();
    }

    return wein;
  }

}
package com.itsziroy.weingutmerlot.backend.db.managers;

import com.itsziroy.weingutmerlot.backend.ChargeService;
import com.itsziroy.weingutmerlot.backend.UebererpruefungService;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;
import com.itsziroy.weingutmerlot.backend.helper.UpcomingUeberpruefung;
import jakarta.persistence.TypedQuery;

import java.util.*;

/**
 * Manager for {@link Ueberpruefung} Entity
 */
public class UeberpruefungManager implements UebererpruefungService {

  @Override
  public List<UpcomingUeberpruefung> getUpcomingUeberpruefungen() {
    /*
     * Overview of the functionality:
     *
     * 1. Find all Chargen that have not been marked as ready yet
     * 2. Find the latest created Überprüfung for each Charge
     * 3. Find the date for the next Überprüfung
     */
    List<UpcomingUeberpruefung> nextUeberpruefungen = new ArrayList<>();


    // 1.
    TypedQuery<Charge> chargeQuery = 
            DB.getEntityManager().createQuery(
                    "select c from Charge c where c.istFertig = false", Charge.class);

    List<Charge> chargen = chargeQuery.getResultList();

    for(Charge charge : chargen) {
      // 2.
      Ueberpruefung ueberpruefung = getCurrentUeberpruefung(charge);
      // 3.
      Date nextDate = getNextUeberpruefungDate(ueberpruefung);
      // add triplet to the list
      nextUeberpruefungen.add(new UpcomingUeberpruefung(charge, nextDate, ueberpruefung));
    }
    return nextUeberpruefungen;
  }

  @Override
  public Date getNextUeberpruefungDate(Ueberpruefung ueberpruefung) {
    if(!ueberpruefung.isNaechsterSchritt()) {
      return ueberpruefung.getNextDate();
    }

    ChargeService chargeService = DB.getChargeManager();

    Charge charge = ueberpruefung.getCharge();
    Gaerungsprozessschritt current = chargeService.getCurrentGaerungsprozessschritt(charge);

    // To calculate next date of Überprüfung add the duration of the  current Gärungsprozessschritt to the last Überprüfung date
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(ueberpruefung.getDatum());
    calendar.add(Calendar.DAY_OF_YEAR, current.getNachZeit());

    return calendar.getTime();
  }

  @Override
  public Ueberpruefung getCurrentUeberpruefung(Charge charge) {
    TypedQuery<Ueberpruefung> ueberpruefungQuery= DB.getEntityManager().createQuery(
            "select u from Ueberpruefung u where u.charge.id = :charge " +
                    "order by u.gaerungsprozessschritt.schritt DESC, u.datum DESC", Ueberpruefung.class);

    ueberpruefungQuery.setParameter("charge", charge.getId());

    List<Ueberpruefung> ueberpruefungen = ueberpruefungQuery.getResultList();

    return ueberpruefungen.get(0);
  }

  @Override
  public List<Ueberpruefung> getAll() {
    return DB.getEntityManager().createQuery("select u from Ueberpruefung u", Ueberpruefung.class).getResultList();
  }

  @Override
  public Ueberpruefung getOne(int id) {
    return DB.getEntityManager().find(Ueberpruefung.class, id);
  }
}

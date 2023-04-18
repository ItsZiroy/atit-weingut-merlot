package com.itsziroy.weingutmerlot.backend;

import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;
import jakarta.persistence.TypedQuery;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UeberpruefungManager {

  public static List<Triplet<Charge,Date,Ueberpruefung>> getUpcomingUeberpruefungen() {
    /*
     * 1. Finde alle Chargen, die nicht fertig sind
     * 2. Finde aktuellen Überprüfungsschritt
     * 3. Finde nächsten Gärungsprozessschritt
     */
    List<Triplet<Charge,Date, Ueberpruefung>> nextUeberpruefungen = new ArrayList<>();

    TypedQuery<Charge> chargeQuery = 
            DB.getEntityManager().createQuery(
                    "select c from Charge c where c.istFertig = false", Charge.class);

    List<Charge> chargen = chargeQuery.getResultList();
    for(Charge charge : chargen) {
      Ueberpruefung ueberpruefung = getCurrentUeberpruefung(charge);
      Date nextDate = getNextUeberpruefungDate(ueberpruefung);
      nextUeberpruefungen.add(new Triplet<>(charge, nextDate, ueberpruefung));
    }
    return nextUeberpruefungen;
  }

  private static Date getNextUeberpruefungDate(Ueberpruefung ueberpruefung) {
    Gaerungsprozessschritt next = ueberpruefung.getGaerungsprozessschritt().getNextProzessschritt();

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(ueberpruefung.getDatum());
    calendar.add(Calendar.DAY_OF_YEAR, next.getNachZeit());

    return calendar.getTime();
  }

  private static Ueberpruefung getCurrentUeberpruefung(Charge charge) {
    TypedQuery<Ueberpruefung> ueberpruefungQuery= DB.getEntityManager().createQuery(
            "select u from Ueberpruefung u where u.charge.id = :charge order by u.gaerungsprozessschritt.schritt ASC", Ueberpruefung.class);
    ueberpruefungQuery.setParameter("charge", charge.getId());
    List<Ueberpruefung> ueberpruefungen = ueberpruefungQuery.getResultList();
    return ueberpruefungen.get(0);
  }
}

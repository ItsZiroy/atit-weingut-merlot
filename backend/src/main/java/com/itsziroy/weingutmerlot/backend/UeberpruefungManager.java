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

/**
 * Manager for {@link Ueberpruefung} Entity
 */
public final class UeberpruefungManager {

  /**
   * Gets all Überprüfungen that are due in the future.
   * @return List of all upcoming Überprüfungen with Charge, Date and Überprüfung
   */
  public static List<Triplet<Charge,Date,Ueberpruefung>> getUpcomingUeberpruefungen() {
    /*
     * Overview of the functionality:
     *
     * 1. Find all Chargen that have not been marked as ready yet
     * 2. Find the latest created Überprüfungsschritt
     * 3. Find next Gärungsprozessschritt of above
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

  /**
   * Gets date for the next Überprüfung
   * @param ueberpruefung Current Überprüfung
   * @return Date for next Überprüfung
   */
  public static Date getNextUeberpruefungDate(Ueberpruefung ueberpruefung) {
    // Get the Gärungsprozessschritt of the latest Überprüfung
    Gaerungsprozessschritt last = ueberpruefung.getGaerungsprozessschritt();
    // The next/current Gaehrungsprozessschritt is relevant for the calculation
    Gaerungsprozessschritt current = last.getNextProzessschritt();

    // To calculate next date of Überprüfung add the duration of the Gärungsprozessschritt to the last Überprüfung date
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(ueberpruefung.getDatum());
    calendar.add(Calendar.DAY_OF_YEAR, current.getNachZeit());

    return calendar.getTime();
  }

  /**
   * Gets the latest created Überprüfung for a Charge
   * @param charge Charge
   * @return Latest Überprüfung
   */
  public static Ueberpruefung getCurrentUeberpruefung(Charge charge) {
    TypedQuery<Ueberpruefung> ueberpruefungQuery= DB.getEntityManager().createQuery(
            "select u from Ueberpruefung u where u.charge.id = :charge " +
                    "order by u.gaerungsprozessschritt.schritt DESC", Ueberpruefung.class);

    ueberpruefungQuery.setParameter("charge", charge.getId());

    List<Ueberpruefung> ueberpruefungen = ueberpruefungQuery.getResultList();

    return ueberpruefungen.get(0);
  }
}

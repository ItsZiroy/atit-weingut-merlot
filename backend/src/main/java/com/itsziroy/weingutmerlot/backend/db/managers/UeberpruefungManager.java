package com.itsziroy.weingutmerlot.backend.db.managers;

import com.itsziroy.weingutmerlot.backend.ChargeService;
import com.itsziroy.weingutmerlot.backend.GaerungsprozessService;
import com.itsziroy.weingutmerlot.backend.UebererpruefungService;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozess;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;
import com.itsziroy.weingutmerlot.backend.db.entities.pivot.UeberpruefungenHasHefen;
import com.itsziroy.weingutmerlot.backend.helper.UpcomingUeberpruefung;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;

import java.time.Instant;
import java.util.*;

/**
 * Manager for {@link Ueberpruefung} Entity
 */
public class UeberpruefungManager implements UebererpruefungService {

    /**
     * Gets and returns all available Überprüfungen from the DB.
     *
     * @return List of Überprüfungen
     */
    @Override
    public List<Ueberpruefung> getAll() {
        LogManager.getLogger().debug("Getting all Überprüfungen");
        return DB.getEntityManager().createQuery("select u from Ueberpruefung u", Ueberpruefung.class).getResultList();
    }

    /**
     * Gets and returns a single Überprüfung by its id.
     *
     * @return Überprüfung
     */
    @Override
    public Ueberpruefung getOne(int id) {
        LogManager.getLogger().debug("Getting Überprüfung " + id);
        return DB.getEntityManager().find(Ueberpruefung.class, id);
    }

    @Override
    public List<UpcomingUeberpruefung> getUpcomingUeberpruefungen() {
        LogManager.getLogger().debug("Getting next Upcoming Ueberpruefungen.");
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

        for (Charge charge : chargen) {
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
        LogManager.getLogger().debug("Getting next Ueberprueung Date for Ueberpruefung " + ueberpruefung.getId());
        if (!ueberpruefung.isNaechsterSchritt()) {
            return ueberpruefung.getNextDate();
        }

        ChargeService chargeService = DB.getChargeManager();

        Charge charge = ueberpruefung.getCharge();
        Gaerungsprozessschritt current = chargeService.getCurrentGaerungsprozessschritt(charge);

        // To calculate next date of Überprüfung add the duration of the  current Gärungsprozessschritt to the last Überprüfung date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ueberpruefung.getDatum());
        calendar.add(Calendar.DAY_OF_YEAR, current.getNachZeit());
        LogManager.getLogger().debug("Next date is " + calendar.getTime());
        return calendar.getTime();
    }

    @Override
    public Ueberpruefung getCurrentUeberpruefung(Charge charge) {
        LogManager.getLogger().debug("Getting current Ueberprueung for Charge" + charge.getId());

        TypedQuery<Ueberpruefung> ueberpruefungQuery = DB.getEntityManager().createQuery(
                "select u from Ueberpruefung u where u.charge.id = :charge " +
                        "order by u.gaerungsprozessschritt.schritt DESC, u.datum DESC", Ueberpruefung.class);

        ueberpruefungQuery.setParameter("charge", charge.getId());

        List<Ueberpruefung> ueberpruefungen = ueberpruefungQuery.getResultList();

        return ueberpruefungen.get(0);
    }

    @Override
    public void save(Ueberpruefung ueberpruefung, List<UeberpruefungenHasHefen> ueberpruefungenHasHefenList)
            throws PersistenceException, IllegalArgumentException {
        LogManager.getLogger().debug("Started validating Ueberpruefung");
        this.validate(ueberpruefung);
        LogManager.getLogger().debug("Successfully validated Ueberpruefung");
        // persist new Überprüfung
        DB.getEntityManager().getTransaction().begin();
        DB.getEntityManager().persist(ueberpruefung);

        // persist new ueberpruefungHasHefen
        for (UeberpruefungenHasHefen ueberpruefungenHasHefen : ueberpruefungenHasHefenList) {
            ueberpruefungenHasHefen.setUeberpruefung(ueberpruefung);
            this.validateHasHefen(ueberpruefungenHasHefen);
            DB.getEntityManager().persist(ueberpruefungenHasHefen);
        }

        GaerungsprozessService gaerungsprozessService = DB.getGaerungsprozessManager();
        Gaerungsprozess gaerungsprozess = ueberpruefung.getGaerungsprozessschritt().getGaerungsprozess();
        Gaerungsprozessschritt lastStep = gaerungsprozessService.getLastGaerungsprozessschritt(gaerungsprozess);

        Gaerungsprozessschritt currentStep = ueberpruefung.getGaerungsprozessschritt();
        Charge charge = ueberpruefung.getCharge();
        // If current Überprüfung is set to be valid, and we proceed to the next step
        // we need to check if it is the last process step because then the charge
        // is finished.
        if ((Objects.equals(currentStep.getSchritt(), lastStep.getSchritt())
                && ueberpruefung.isNaechsterSchritt())) {
            charge.setFertig(true);
            DB.getEntityManager().persist(charge);
        }

        DB.getEntityManager().getTransaction().commit();
        LogManager.getLogger().debug("Successfully persisted Ueberpruefung.");
    }

    @Override
    public void validate(Ueberpruefung ueberpruefung) throws IllegalArgumentException {
        if (ueberpruefung.getIstZucker() < 0) {
            throw new IllegalArgumentException("Sugar value cannot be negative");
        }
        if (ueberpruefung.getIstAlkohol() < 0) {
            throw new IllegalArgumentException("Alkohol value cannot be negative");
        }
        if (ueberpruefung.getAnpassungZucker() < 0) {
            throw new IllegalArgumentException("Sugar adaption value cannot be negative");
        }
        if (ueberpruefung.getNextDate() != null) {
            if (ueberpruefung.getNextDate().before(ueberpruefung.getDatum())) {
                throw new IllegalArgumentException("Next date cannot be before the date of the Ueberpruefung");
            }
            if (ueberpruefung.getNextDate().before(Date.from(Instant.now()))) {
                throw new IllegalArgumentException("Next date cannot be before current date");
            }
            // When the next step is enabled, there must not be a next date
            if (ueberpruefung.isNaechsterSchritt()) {
                throw new IllegalArgumentException("When the next step is enabled, there must not be a next date");
            }
        }
    }

    @Override
    public void validateHasHefen(UeberpruefungenHasHefen ueberpruefungenHasHefen) throws IllegalArgumentException {
        if (ueberpruefungenHasHefen.getAnpassung() < 0) {
            throw new IllegalArgumentException();
        }
    }
}

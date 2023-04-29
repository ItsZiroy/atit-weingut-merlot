package com.itsziroy.weingutmerlot.backend.db.managers;

import com.itsziroy.weingutmerlot.backend.ChargeService;
import com.itsziroy.weingutmerlot.backend.UebererpruefungService;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;

import java.util.List;

/**
 * Manager for {@link Charge} Entity
 */
public class ChargeManager implements ChargeService {

    @Override
    public Gaerungsprozessschritt getCurrentGaerungsprozessschritt(Charge charge) {
        UebererpruefungService uebererpruefungService = DB.getUeberpruefungManager();

        Ueberpruefung currentUeberpruefung = uebererpruefungService.getCurrentUeberpruefung(charge);

        // Get the Gärungsprozessschritt of the Überprüfung
        Gaerungsprozessschritt last = currentUeberpruefung.getGaerungsprozessschritt();

        // Get the current Gärungsprozessschritt
        Gaerungsprozessschritt currentGaerungsprozessschritt;
        // if the last Überprüfung was accepted
        if(currentUeberpruefung.isNaechsterSchritt()){
            // the current Gärungsprozessschritt is one higher than the last one
            currentGaerungsprozessschritt = last.getNextProzessschritt();
        } else{
            // otherwise the current Gärungsprozessschritt is still the same
            currentGaerungsprozessschritt = last;
        }
        return currentGaerungsprozessschritt;
    }

    /**
     * Gets and returns all available Chargen from the DB.
     * @return List of Chargen
     */
    @Override
    public List<Charge> getAll() {
        return DB.getEntityManager().createQuery("select c from Charge c", Charge.class).getResultList();
    }

    /**
     * Gets and returns a single Charge by its id.
     * @return Charge
     */
    @Override
    public Charge getOne(int id) {
        return DB.getEntityManager().find(Charge.class, id);
    }
}
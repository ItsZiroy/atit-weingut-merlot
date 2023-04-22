package com.itsziroy.weingutmerlot.backend;

import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;

/**
 * Manager for {@link Charge} Entity
 */
public final class ChargeManager {
    /**
     * Gets current Gärungsprozessschritt for the Charge
     * @param charge Charge
     * @return Gaerungsprozessschritt Current Gärungsprozesschritt
     */
    public static Gaerungsprozessschritt getCurrentGaerungsprozessschritt(Charge charge){
        Ueberpruefung currentUeberpruefung = UeberpruefungManager.getCurrentUeberpruefung(charge);

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
}

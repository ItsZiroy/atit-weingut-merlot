package com.itsziroy.weingutmerlot.backend;

import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;
import com.itsziroy.weingutmerlot.backend.helper.UpcomingUeberpruefung;

import java.util.Date;
import java.util.List;

public interface UebererpruefungService extends Service<Ueberpruefung> {
    /**
     * Gets for each Charge the next Überprüfung that is due in the future.
     * @return List of all upcoming Überprüfungen with their Charge, Date and latest Überprüfung
     */
    List<UpcomingUeberpruefung> getUpcomingUeberpruefungen();

    /**
     * Gets date for the next Überprüfung
     * @param ueberpruefung Current Überprüfung
     * @return Date for next Überprüfung
     */
    Date getNextUeberpruefungDate(Ueberpruefung ueberpruefung);

    /**
     * Gets the latest created Überprüfung for a Charge
     * It is important to sort both by step and date because there might be multiple
     * Überprüfungen for the same Gaerungsprozessschritt due to errors
     * @param charge Charge
     * @return Latest Überprüfung
     */
    Ueberpruefung getCurrentUeberpruefung(Charge charge);

}
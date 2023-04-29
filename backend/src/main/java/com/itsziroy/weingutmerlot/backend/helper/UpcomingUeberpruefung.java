package com.itsziroy.weingutmerlot.backend.helper;

import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;
import com.itsziroy.weingutmerlot.backend.db.managers.UeberpruefungManager;

import java.util.Date;

/**
 * This is a small helper class for the calculation of upcoming
 * Überprüfungen for Chargen in {@link UeberpruefungManager#getUpcomingUeberpruefungen()}.
 * <br>
 * It stores the charge, the next Überprüfung date and the latest Überprüfung that
 * was persisted in the DB (done) for the charge.
 *
 * @param charge            Charge
 * @param nextDate          Next Überprüfung Date
 * @param lastUeberpruefung Latest Überprüfung that was persisted in DB
 */
public record UpcomingUeberpruefung(Charge charge, Date nextDate, Ueberpruefung lastUeberpruefung) {
}

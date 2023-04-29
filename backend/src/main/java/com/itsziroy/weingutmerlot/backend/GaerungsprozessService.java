package com.itsziroy.weingutmerlot.backend;

import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozess;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;

public interface GaerungsprozessService extends Service<Gaerungsprozess> {
    /**
     * Returns the last Gaerungsprozessschritt of the Gaerungsprozess
     *
     * @param gaerungsprozess Gaerungsprozess
     * @return last Gaerungsprozessschritt
     */
    Gaerungsprozessschritt getLastGaerungsprozessschritt(Gaerungsprozess gaerungsprozess);
}

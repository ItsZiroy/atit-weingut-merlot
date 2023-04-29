package com.itsziroy.weingutmerlot.backend;

import com.itsziroy.weingutmerlot.backend.db.entities.Charge;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;

public interface ChargeService extends Service<Charge> {
    /**
     * Gets current Gärungsprozessschritt for the Charge
     *
     * @param charge Charge
     * @return Gaerungsprozessschritt Current Gärungsprozesschritt
     */
    Gaerungsprozessschritt getCurrentGaerungsprozessschritt(Charge charge);

    /**
     * For whatever cicumstances the production of the charge failed
     * This method marks the charge as finished and unusable
     *
     * @param charge Charge
     */
    void setIrreversiblyDamaged(Charge charge);

}

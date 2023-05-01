package com.itsziroy.weingutmerlot.backend.db.managers;

import com.itsziroy.weingutmerlot.backend.GaerungsprozessService;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozess;
import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class GaerungsprozessManager implements GaerungsprozessService {

    /**
     * Gets and returns all available Gärungsprozesse from the DB.
     *
     * @return List of Gärungsprozess
     */
    @Override
    public List<Gaerungsprozess> getAll() {
        LogManager.getLogger().debug("Getting all Gärungsprozesse");
        return DB.getEntityManager().createQuery("select g from Gaerungsprozess g", Gaerungsprozess.class).getResultList();
    }

    /**
     * Gets and returns a single Gärungsprozess by its id.
     *
     * @return Gärungsprozess
     */
    @Override
    public Gaerungsprozess getOne(int id) {
        LogManager.getLogger().debug("Getting Gärungsprozess " + id);
        return DB.getEntityManager().find(Gaerungsprozess.class, id);
    }

    @Override
    public Gaerungsprozessschritt getLastGaerungsprozessschritt(Gaerungsprozess gaerungsprozess) {
        LogManager.getLogger().debug("Getting latest Gaerungsprozessschritt for Gaerungsprozess "
                + gaerungsprozess.getId());

        TypedQuery<Gaerungsprozessschritt> lastStepQuery = DB.getEntityManager().createQuery(
                "select g from Gaerungsprozessschritt g where g.gaerungsprozess.id = :id " +
                        "order by schritt desc", Gaerungsprozessschritt.class);
        lastStepQuery.setParameter("id", gaerungsprozess.getId());
        List<Gaerungsprozessschritt> result = lastStepQuery.getResultList();
        return result.get(0);
    }
}

package com.itsziroy.weingutmerlot.backend.db.managers;

import com.itsziroy.weingutmerlot.backend.HefeService;
import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.Hefe;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class HefeManager implements HefeService {

    /**
     * Gets and returns all available Hefen from the DB.
     *
     * @return List of Hefen
     */
    @Override
    public List<Hefe> getAll() {
        LogManager.getLogger().debug("Getting all Hefen");
        return DB.getEntityManager().createQuery("select h from Hefe h", Hefe.class).getResultList();
    }

    /**
     * Gets and returns a single Hefe by its id.
     *
     * @return Hefe
     */
    @Override
    public Hefe getOne(int id) {
        LogManager.getLogger().debug("Getting Hefe " + id);
        return DB.getEntityManager().find(Hefe.class, id);
    }
}

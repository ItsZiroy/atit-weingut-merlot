package com.itsziroy.weingutmerlot.backend.db;

import com.itsziroy.weingutmerlot.backend.UebererpruefungService;
import com.itsziroy.weingutmerlot.backend.db.managers.ChargeManager;
import com.itsziroy.weingutmerlot.backend.db.managers.GaerungsprozessManager;
import com.itsziroy.weingutmerlot.backend.db.managers.HefeManager;
import com.itsziroy.weingutmerlot.backend.db.managers.UeberpruefungManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import org.apache.logging.log4j.LogManager;

/**
 * This class is meant to serve as the general entry point for all
 * Database related methods. As there is usually only one DB per
 * project, a static class mapping to exactly that makes sense to use
 */
public final class DB {

    private static final ChargeManager chargeManager = new ChargeManager();
    private static final GaerungsprozessManager gaerungsprozessManager = new GaerungsprozessManager();
    private static final HefeManager hefeManager = new HefeManager();
    private static final UeberpruefungManager ueberpruefungManager = new UeberpruefungManager();
    private static EntityManager entityManager = null;
    private static String persistenceUnitName = "default";

    private DB() {
    }

    public static ChargeManager getChargeManager() {
        return chargeManager;
    }

    /**
     * A shorthand functions that combines
     * {@link #setPersistenceUnit(String)} and {@link #getEntityManager()}
     * to create an entity manager for a specific persistence unit. This
     * is especially helpful for creating tests.
     *
     * @param pUnitName Persistence Unit name
     * @return Entity Manager
     * @throws PersistenceException An error with persistence config
     */
    public static EntityManager getEntityManager(String pUnitName) throws PersistenceException {
        LogManager.getLogger().debug("Getting Entity Manager for Persistence Unit " + pUnitName);
        if (entityManager == null || !pUnitName.equals(persistenceUnitName)) {
            setPersistenceUnit(pUnitName);
        }
        return entityManager;
    }

    /**
     * Sets the persistence unit and reinitialized the DB connector.
     *
     * @param name Name of persistence unit
     * @throws PersistenceException An error with persistence config
     */
    public static void setPersistenceUnit(String name) throws PersistenceException {
        LogManager.getLogger().debug("Setting persistence Unit to " + name);
        persistenceUnitName = name;
        initialize();
    }

    /**
     * Initializes a new EntityManager
     *
     * @throws PersistenceException An error with persistence config
     */
    private static void initialize() throws PersistenceException {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(persistenceUnitName);

        entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Gets the entity manager for the current persistence unit
     *
     * @return EntityManager
     * @throws PersistenceException An error with persistence config
     */
    public static EntityManager getEntityManager() throws PersistenceException {
        LogManager.getLogger().debug("Getting Entity Manager for standard persistence Unit");
        if (entityManager == null) {
            initialize();
        }
        return entityManager;
    }

    public static GaerungsprozessManager getGaerungsprozessManager() {
        return gaerungsprozessManager;
    }

    public static HefeManager getHefeManager() {
        return hefeManager;
    }

    /**
     * @return Persistence Unit Name
     */
    public static String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    public static UebererpruefungService getUeberpruefungManager() {
        return ueberpruefungManager;
    }

}

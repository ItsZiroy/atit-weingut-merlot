package com.itsziroy.weingutmerlot.backend.db;

import com.itsziroy.weingutmerlot.backend.*;
import com.itsziroy.weingutmerlot.backend.db.managers.ChargeManager;
import com.itsziroy.weingutmerlot.backend.db.managers.GaerungsprozessManager;
import com.itsziroy.weingutmerlot.backend.db.managers.HefeManager;
import com.itsziroy.weingutmerlot.backend.db.managers.UeberpruefungManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

/**
 * This class is meant to serve as the general entry point for all
 * Database related methods. As there is usually only one DB per
 * project, a static class mapping to exactly that makes sense to use
 */
public final class DB {

  private DB () {}
  private static String persistenceUnitName = "default";
  private static EntityManager entityManager = null;

  private static final UeberpruefungManager ueberpruefungManager = new UeberpruefungManager();
  private static final HefeManager hefeManager = new HefeManager();
  private static final ChargeManager chargeManager = new ChargeManager();
  private static final GaerungsprozessManager gaerungsprozessManager = new GaerungsprozessManager();
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
   * Sets the persistence unit and reinitialized the DB connector.
   *
   * @param name Name of persistence unit
   * @throws PersistenceException An error with persistence config
   */
  public static void setPersistenceUnit(String name) throws PersistenceException {
    persistenceUnitName = name;
    initialize();
  }

  /**
   * @return Persistence Unit Name
   */
  public static String getPersistenceUnitName() {
    return persistenceUnitName;
  }

  /**
   * Gets the entity manager for the current persistence unit
   *
   * @return EntityManager
   * @throws PersistenceException An error with persistence config
   */
  public static EntityManager getEntityManager() throws PersistenceException {
    if(entityManager == null) {
      initialize();
    }
    return entityManager;
  }

  /**
   * A shorthand functions that combines
   * {@link #setPersistenceUnit(String)} and {@link #getEntityManager()}
   * to create an entity manager for a specific persistence unit. This
   * is especially helpful for creating tests.
   *
   * @param p_unit_name Persistence Unit name
   * @return Entity Manager
   * @throws PersistenceException An error with persistence config
   */
  public static EntityManager getEntityManager(String p_unit_name) throws PersistenceException {
    if(entityManager == null || !p_unit_name.equals(persistenceUnitName)) {
      setPersistenceUnit(p_unit_name);
    }
    return entityManager;
  }

  public static UebererpruefungService getUeberpruefungManager() {
    return ueberpruefungManager;
  }

  public static ChargeManager getChargeManager() {
    return chargeManager;
  }

  public static HefeManager getHefeManager() {
    return hefeManager;
  }

  public static GaerungsprozessManager getGaerungsprozessManager() {
    return gaerungsprozessManager;
  }

}

package com.itsziroy.weingutmerlot.backend.db;

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
  private static String persistence_unit_name = "default";
  private static EntityManager entityManager = null;

  /**
   * Initializes a new EntityManager
   *
   * @throws PersistenceException An error with persistence config
   */
  private static void initialize() throws PersistenceException {
    EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory(persistence_unit_name);

    entityManager = entityManagerFactory.createEntityManager();
  }

  /**
   * Sets the persistence unit and reinitialized the DB connector.
   *
   * @param name Name of persistence unit
   * @throws PersistenceException An error with persistence config
   */
  public static void setPersistenceUnit(String name) throws PersistenceException {
    persistence_unit_name = name;
    initialize();
  }

  /**
   * @return Persistence Unit Name
   */
  public static String getPersistence_unit_name() {
    return persistence_unit_name;
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
    if(entityManager == null || !p_unit_name.equals(persistence_unit_name)) {
      setPersistenceUnit(p_unit_name);
    }
    return entityManager;
  }
}

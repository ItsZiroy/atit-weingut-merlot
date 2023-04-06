package com.itsziroy.weingutmerlot.backend.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;


public final class DB {
  private static final String PERSISTENCE_UNIT_NAME = "default";
  private static EntityManagerFactory entityManagerFactory = null;
  private static EntityManager entityManager = null;
  public static void initialize() throws PersistenceException {
    entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    entityManager = entityManagerFactory.createEntityManager();
  }

  public static EntityManager getEntityManager() {
    if(entityManager == null) {
      initialize();
    }
    return entityManager;
  }
}

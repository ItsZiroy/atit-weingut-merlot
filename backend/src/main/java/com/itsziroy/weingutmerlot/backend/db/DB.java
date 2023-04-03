package com.itsziroy.weingutmerlot.backend.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public final class DB {
  private static final String PERSISTENCE_UNIT_NAME = "default";
  private static EntityManagerFactory entityManagerFactory = null;
  public static EntityManager entityManager = null;
  public static void initialize() {

    entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    entityManager = entityManagerFactory.createEntityManager();
  }
}

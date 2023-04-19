package com.itsziroy.weingutmerlot.backend.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DBTest {
  /**
   * This test makes sure that when a persistence Unit does not exist,
   * an exception is being thrown
   */
  @Test
  void persistenceUnitDoesNotExist() throws ClassNotFoundException {
    Assertions.assertThrows(PersistenceException.class, () -> DB.setPersistenceUnit("this_will_definitely_not_exist"));
  }

  /**
   * Getting entity manager with manual persistence Unit declaration
   */
  @Test
  void getEntityManager() {
    DB.setPersistenceUnit("test");
    Assertions.assertInstanceOf(EntityManager.class, DB.getEntityManager());
  }

  /**
   * Getting entity manager with shorthand persistence Unit
   */
  @Test
  void getEntityManagerShorthand() {
    // Test shorthand Entity Manager method that initializes with different persistence unit
    Assertions.assertInstanceOf(EntityManager.class, DB.getEntityManager("test"));
  }
}
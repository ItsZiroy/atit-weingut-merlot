package com.itsziroy.weingutmerlot.backend;

import java.util.List;

/**
 * Generic Interface to ensure all Services have basic Entity query methods.
 *
 * @param <T> Entity
 */
public interface Service<T> {

    /**
     * Gets and returns all Entities from DB.
     *
     * @return List of Entities
     */
    List<T> getAll();

    /**
     * Gets and returns a single Element by its id.
     *
     * @return Single Entry from DB
     */
    T getOne(int id);
}

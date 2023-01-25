package com.hristoforov.elective.dao.interfaces;

import com.hristoforov.elective.exceptions.DataBaseInteractionException;

import java.util.List;

/**
 * DAO interface with CRUD operations
 *
 * @param <E> type of entities
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public interface Dao<E> {
    /**
     * Insert entity into db
     *
     * @param entity - concrete entity
     * @throws DataBaseInteractionException
     */
    void create(E entity) throws DataBaseInteractionException;

    /**
     * Update entity in db
     *
     * @param entity -  concrete entity
     * @throws DataBaseInteractionException
     */
    void update(E entity) throws DataBaseInteractionException;

    /**
     * Remove entity from db
     *
     * @param id - id of entity
     * @throws DataBaseInteractionException
     */
    void remove(Long id) throws DataBaseInteractionException;

    /**
     * Obtains list of all entities from database
     *
     * @return list of entities
     * @throws DataBaseInteractionException
     */
    List<E> findAll() throws DataBaseInteractionException;

    /**
     * Find entity by id
     *
     * @param id - id of entity
     * @return entity with this id
     * @throws DataBaseInteractionException
     */
    E findById(Long id) throws DataBaseInteractionException;
}

package com.dk.gym.dao;

import com.dk.gym.pool.ProxyConnection;
import com.dk.gym.entity.Entity;
import com.dk.gym.exception.DaoException;

import java.util.List;

/**
 * The Class AbstractDao. Contains general DAO methods.
 *
 * @param <T> the generic type
 */
public abstract class AbstractDao<T extends Entity> implements AutoCloseable {

    /** The Constant RETURNED_NEGATIVE_RESULT. */
    public static final int RETURNED_NEGATIVE_RESULT = -1;

    /** The connection. */
    protected ProxyConnection connection;

    /**
     * Sets the connection.
     *
     * @param connection the new connection
     */
    public void setConnection(ProxyConnection connection) {
        this.connection = connection;
    }

    /**
     * Find all.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<T> findAll() throws DaoException;

    /**
     * Creates the.
     *
     * @param entity the entity
     * @return the int
     * @throws DaoException the dao exception
     */
    public abstract int create(T entity) throws DaoException;

    /**
     * Update.
     *
     * @param entity the entity
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public abstract boolean update(T entity) throws DaoException;

    /* (non-Javadoc)
     * @see java.lang.AutoCloseable#close()
     */
    @Override
    public void close() {
        if (connection != null) {
            connection.close();
        }
    }
}

package com.dk.gym.dao;

import com.dk.gym.pool.ProxyConnection;
import com.dk.gym.entity.Entity;
import com.dk.gym.exception.DaoException;

import java.util.List;

public abstract class AbstractDao<T extends Entity> implements AutoCloseable {

    public static final int RETURNED_NEGATIVE_RESULT = -1;

    protected ProxyConnection connection;

    public void setConnection(ProxyConnection connection) {
        this.connection = connection;
    }

    public abstract List<T> findAll() throws DaoException;

    public abstract int create(T entity) throws DaoException;

    public abstract boolean update(T entity) throws DaoException;

    @Override
    public void close() {
        if (connection != null) {
            connection.close();
        }
    }
}

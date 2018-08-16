package com.dk.gym.dao;

import com.dk.gym.entity.Activity;
import com.dk.gym.exception.DaoException;

/**
 * The Class ActivityDao. Contains specific methods.
 */
public abstract class ActivityDao extends AbstractDao<Activity> {

    /**
     * Find by id.
     *
     * @param id the id
     * @return the activity
     * @throws DaoException the dao exception
     */
    public abstract Activity findById(int id) throws DaoException;

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public abstract boolean delete(int id) throws DaoException;
}

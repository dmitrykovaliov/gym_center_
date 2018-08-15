package com.dk.gym.dao;

import com.dk.gym.entity.Activity;
import com.dk.gym.exception.DaoException;

public abstract class ActivityDao extends AbstractDao<Activity> {

    public abstract Activity findById(int id) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;
}

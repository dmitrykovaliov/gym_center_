package com.dk.gym.dao;

import com.dk.gym.entity.Activity;
import com.dk.gym.exception.DaoException;

import java.util.List;

public abstract class ActivityDao extends AbstractDao<Activity> {

    @Override
    public abstract List<Activity> findAll() throws DaoException;

    @Override
    public abstract Activity findEntityById(int id) throws DaoException;

    @Override
    public abstract int create(Activity entity) throws DaoException;

    @Override
    public abstract boolean update(Activity entity) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;
}

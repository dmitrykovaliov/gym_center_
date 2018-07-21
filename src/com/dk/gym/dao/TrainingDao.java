package com.dk.gym.dao;

import com.dk.gym.entity.Training;
import com.dk.gym.exception.DaoException;

import java.util.List;

public abstract class TrainingDao extends AbstractDao<Training> {

    @Override
    public abstract List<Training> findAll() throws DaoException;

    @Override
    public abstract Training findEntityById(int id) throws DaoException;

    @Override
    public abstract int create(Training entity) throws DaoException;

    @Override
    public abstract boolean update(Training entity) throws DaoException;
}

package com.dk.gym.dao;

import com.dk.gym.entity.Trainer;
import com.dk.gym.exception.DaoException;

import java.util.List;

public abstract class TrainerDao extends AbstractDao<Trainer> {

    @Override
    public abstract List<Trainer> findAll() throws DaoException;

    @Override
    public abstract Trainer findEntityById(int id) throws DaoException;

    @Override
    public abstract int create(Trainer entity) throws DaoException;

    @Override
    public abstract boolean update(Trainer entity) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;

    public abstract boolean updateUserId(Integer  idUser, int idTrainer) throws DaoException;
}

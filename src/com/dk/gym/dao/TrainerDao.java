package com.dk.gym.dao;

import com.dk.gym.entity.Trainer;
import com.dk.gym.exception.DaoException;

public abstract class TrainerDao extends AbstractDao<Trainer> {

    public abstract Trainer findById(int id) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;

    public abstract boolean updateUserId(Integer  idUser, int idTrainer) throws DaoException;

    public abstract Trainer findByUserId(int idUser) throws DaoException;
}

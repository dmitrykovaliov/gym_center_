package com.dk.gym.dao;

import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.Training;
import com.dk.gym.exception.DaoException;

import java.util.List;

public abstract class TrainingDao extends AbstractDao<Training> {

    @Override
    public abstract List<Training> findAll() throws DaoException;

    public abstract Training findById(int id) throws DaoException;

    @Override
    public abstract int create(Training entity) throws DaoException;

    @Override
    public abstract boolean update(Training entity) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;

    public abstract List<Trainer> findRelatedAllTrainer() throws DaoException;

    public abstract List<Training> findAllTrainingByTrainer(int idUser) throws DaoException;

    public abstract List<Training> findAllTrainingByClient(int idUser) throws DaoException;

    public abstract List<Trainer> findRelatedAllTrainerByClient(int idUser) throws DaoException;
}

package com.dk.gym.dao;

import com.dk.gym.entity.Prescription;
import com.dk.gym.entity.Trainer;
import com.dk.gym.exception.DaoException;
import java.util.List;

public abstract class PrescriptionDao extends AbstractDao<Prescription> {

    @Override
    public abstract List<Prescription> findAll() throws DaoException;

    public abstract Prescription findById(int idOrder, int idTrainer) throws DaoException;

    @Override
    public abstract int create(Prescription entity) throws DaoException;

    @Override
    public abstract boolean update(Prescription entity) throws DaoException;

    public abstract boolean delete(int idTrainer, int idOrder) throws DaoException;

    public abstract List<Trainer> findRelatedAllTrainer() throws DaoException;

    public abstract List<Prescription> findAllPrescriptionByTrainer(int idUser) throws DaoException;

    public abstract List<Prescription> findAllPrescriptionByClient(int idUser) throws DaoException;

    public abstract List<Trainer> findRelatedAllTrainerByClient(int idUser) throws DaoException;
}

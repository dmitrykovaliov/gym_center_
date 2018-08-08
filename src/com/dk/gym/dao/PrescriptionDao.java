package com.dk.gym.dao;

import com.dk.gym.entity.Prescription;
import com.dk.gym.entity.Trainer;
import com.dk.gym.exception.DaoException;

import java.util.List;
import java.util.Set;

public abstract class PrescriptionDao extends AbstractDao<Prescription> {

    @Override
    public abstract List<Prescription> findAll() throws DaoException;

    public abstract Prescription findEntityById(int idOrder, int idTrainer) throws DaoException;

    @Override
    public abstract int create(Prescription entity) throws DaoException;

    @Override
    public abstract boolean update(Prescription entity) throws DaoException;

    public abstract boolean delete(int idTrainer, int idOrder) throws DaoException;

    public abstract List<Trainer> findAllTrainer() throws DaoException;

    public abstract List<Prescription> findTrainerPrescriptions(int idUser) throws DaoException;

    public abstract List<Prescription> findClientPrescriptions(int idUser) throws DaoException;
}

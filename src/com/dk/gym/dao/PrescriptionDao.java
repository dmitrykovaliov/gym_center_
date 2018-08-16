package com.dk.gym.dao;

import com.dk.gym.entity.Prescription;
import com.dk.gym.entity.Trainer;
import com.dk.gym.exception.DaoException;
import java.util.List;

/**
 * The Class PrescriptionDao. Contains specific methods.
 */
public abstract class PrescriptionDao extends AbstractDao<Prescription> {

    /**
     * Find by id.
     *
     * @param idOrder the id order
     * @param idTrainer the id trainer
     * @return the prescription
     * @throws DaoException the dao exception
     */
    public abstract Prescription findById(int idOrder, int idTrainer) throws DaoException;

    /**
     * Delete.
     *
     * @param idTrainer the id trainer
     * @param idOrder the id order
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public abstract boolean delete(int idTrainer, int idOrder) throws DaoException;

    /**
     * Find related all trainer.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Trainer> findRelatedAllTrainer() throws DaoException;

    /**
     * Find all prescription by trainer.
     *
     * @param idUser the id user
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Prescription> findAllPrescriptionByTrainer(int idUser) throws DaoException;

    /**
     * Find all prescription by client.
     *
     * @param idUser the id user
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Prescription> findAllPrescriptionByClient(int idUser) throws DaoException;

    /**
     * Find related all trainer by client.
     *
     * @param idUser the id user
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Trainer> findRelatedAllTrainerByClient(int idUser) throws DaoException;
}

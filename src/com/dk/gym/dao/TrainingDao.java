package com.dk.gym.dao;

import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.Training;
import com.dk.gym.exception.DaoException;
import java.util.List;

/**
 * The Class TrainingDao. Contains specific methods.
 */
public abstract class TrainingDao extends AbstractDao<Training> {

    /**
     * Find by id.
     *
     * @param id the id
     * @return the training
     * @throws DaoException the dao exception
     */
    public abstract Training findById(int id) throws DaoException;

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public abstract boolean delete(int id) throws DaoException;

    /**
     * Find related all trainer.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Trainer> findRelatedAllTrainer() throws DaoException;

    /**
     * Find all training by trainer.
     *
     * @param idUser the id user
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Training> findAllTrainingByTrainer(int idUser) throws DaoException;

    /**
     * Find all training by client.
     *
     * @param idUser the id user
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Training> findAllTrainingByClient(int idUser) throws DaoException;

    /**
     * Find related all trainer by client.
     *
     * @param idUser the id user
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Trainer> findRelatedAllTrainerByClient(int idUser) throws DaoException;
}

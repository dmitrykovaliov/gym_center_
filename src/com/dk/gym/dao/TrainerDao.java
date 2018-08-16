package com.dk.gym.dao;

import com.dk.gym.entity.Trainer;
import com.dk.gym.exception.DaoException;

/**
 * The Class TrainerDao. Contains specific methods.
 */
public abstract class TrainerDao extends AbstractDao<Trainer> {

    /**
     * Find by id.
     *
     * @param id the id
     * @return the trainer
     * @throws DaoException the dao exception
     */
    public abstract Trainer findById(int id) throws DaoException;

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public abstract boolean delete(int id) throws DaoException;

    /**
     * Update user id.
     *
     * @param idUser the id user
     * @param idTrainer the id trainer
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public abstract boolean updateUserId(Integer  idUser, int idTrainer) throws DaoException;

    /**
     * Find by user id.
     *
     * @param idUser the id user
     * @return the trainer
     * @throws DaoException the dao exception
     */
    public abstract Trainer findByUserId(int idUser) throws DaoException;
}

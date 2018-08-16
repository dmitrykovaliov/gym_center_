package com.dk.gym.dao;

import com.dk.gym.entity.Client;
import com.dk.gym.exception.DaoException;

import java.util.List;

/**
 * The Class ClientDao. Contains specific methods.
 */
public abstract class ClientDao extends AbstractDao<Client> {

    /**
     * Find by id.
     *
     * @param id the id
     * @return the client
     * @throws DaoException the dao exception
     */
    public abstract Client findById(int id) throws DaoException;

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
     * @param idClient the id client
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public abstract boolean updateUserId(Integer idUser, int idClient) throws DaoException;

    /**
     * Find all by trainer.
     *
     * @param idUser the id user
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Client> findAllByTrainer(int idUser) throws DaoException;

    /**
     * Find by user id.
     *
     * @param idUser the id user
     * @return the client
     * @throws DaoException the dao exception
     */
    public abstract Client findByUserId(int idUser) throws DaoException;
}

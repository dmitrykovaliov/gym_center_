package com.dk.gym.dao;

import com.dk.gym.entity.Client;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.User;
import com.dk.gym.exception.DaoException;

import java.util.List;

/**
 * The Class UserDao. Contains specific methods.
 */
public abstract class UserDao extends AbstractDao<User> {

    /**
     * Find by id.
     *
     * @param id the id
     * @return the user
     * @throws DaoException the dao exception
     */
    public abstract User findById(int id) throws DaoException;

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public abstract boolean delete(int id) throws DaoException;

    /**
     * Find user.
     *
     * @param login the login
     * @param pass the pass
     * @return the user
     * @throws DaoException the dao exception
     */
    public abstract User findUser(String login, String pass) throws DaoException;

    /**
     * Find login.
     *
     * @param login the login
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public abstract boolean findLogin(String login) throws DaoException;

    /**
     * Find related all client.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Client> findRelatedAllClient() throws DaoException;

    /**
     * Find related all trainer.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Trainer> findRelatedAllTrainer() throws DaoException;
}

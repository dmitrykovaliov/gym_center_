package com.dk.gym.dao;

import com.dk.gym.entity.Activity;
import com.dk.gym.entity.Client;
import com.dk.gym.entity.Order;
import com.dk.gym.exception.DaoException;
import java.util.List;

/**
 * The Class OrderDao. Contains specific methods.
 */
public abstract class OrderDao extends AbstractDao<Order> {

    /**
     * Find by id.
     *
     * @param id the id
     * @return the order
     * @throws DaoException the dao exception
     */
    public abstract Order findById(int id) throws DaoException;

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public abstract boolean delete(int id) throws DaoException;

    /**
     * Find related all client.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Client> findRelatedAllClient() throws DaoException;

    /**
     * Find related all activity.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Activity> findRelatedAllActivity() throws DaoException;

    /**
     * Find all order by trainer.
     *
     * @param idUser the id user
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Order> findAllOrderByTrainer(int idUser) throws DaoException;

    /**
     * Find related all client by trainer.
     *
     * @param idUser the id user
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Client> findRelatedAllClientByTrainer(int idUser) throws DaoException;

    /**
     * Find related all activity by trainer.
     *
     * @param idUser the id user
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Activity> findRelatedAllActivityByTrainer(int idUser) throws DaoException;

    /**
     * Find related all order by client.
     *
     * @param idUser the id user
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Order> findRelatedAllOrderByClient(int idUser) throws DaoException;

    /**
     * Find all activity by client.
     *
     * @param idUser the id user
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Activity> findAllActivityByClient(int idUser) throws DaoException;
}

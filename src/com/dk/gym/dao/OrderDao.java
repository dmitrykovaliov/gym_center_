package com.dk.gym.dao;

import com.dk.gym.entity.Activity;
import com.dk.gym.entity.Client;
import com.dk.gym.entity.Order;
import com.dk.gym.exception.DaoException;
import java.util.List;

public abstract class OrderDao extends AbstractDao<Order> {

    public abstract Order findById(int id) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;

    public abstract List<Client> findRelatedAllClient() throws DaoException;

    public abstract List<Activity> findRelatedAllActivity() throws DaoException;

    public abstract List<Order> findAllOrderByTrainer(int idUser) throws DaoException;

    public abstract List<Client> findRelatedAllClientByTrainer(int idUser) throws DaoException;

    public abstract List<Activity> findRelatedAllActivityByTrainer(int idUser) throws DaoException;

    public abstract List<Order> findRelatedAllOrderByClient(int idUser) throws DaoException;

    public abstract List<Activity> findAllActivityByClient(int idUser) throws DaoException;
}

package com.dk.gym.dao;

import com.dk.gym.entity.Activity;
import com.dk.gym.entity.Client;
import com.dk.gym.entity.Order;
import com.dk.gym.exception.DaoException;
import java.util.List;

public abstract class OrderDao extends AbstractDao<Order> {
    @Override
    public abstract List<Order> findAll() throws DaoException;

    public abstract Order findById(int id) throws DaoException;

    @Override
    public abstract int create(Order entity) throws DaoException;

    @Override
    public abstract boolean update(Order entity) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;

    public abstract List<Client> findRelatedAllClient() throws DaoException;

    public abstract List<Activity> findRelatedAllActivity() throws DaoException;

    public abstract List<Order> findAllOrderByTrainer(int idUser) throws DaoException;

    public abstract List<Client> findAllClientByTrainer(int idUser) throws DaoException;

    public abstract List<Activity> findAllActivityByTrainer(int idUser) throws DaoException;

    public abstract List<Order> findAllOrderByClient(int idUser) throws DaoException;

    public abstract List<Activity> findAllActivityByClient(int idUser) throws DaoException;
}

package com.dk.gym.dao;

import com.dk.gym.entity.Order;
import com.dk.gym.entity.join.JoinOrder;
import com.dk.gym.exception.DaoException;

import java.util.List;

public abstract class OrderDao extends AbstractDao<Order> {
    @Override
    public abstract List<Order> findAll() throws DaoException;

    @Override
    public abstract Order findEntityById(int id) throws DaoException;

    @Override
    public abstract int create(Order entity) throws DaoException;

    @Override
    public abstract boolean update(Order entity) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;

    public abstract List<JoinOrder> findJoinAll() throws DaoException;
}

package com.dk.gym.dao;

import com.dk.gym.entity.Payment;
import com.dk.gym.exception.DaoException;

import java.util.List;

public abstract class PaymentDao extends AbstractDao<Payment> {

    @Override
    public abstract List<Payment> findAll() throws DaoException;
    @Override
    public abstract Payment findEntityById(int id) throws DaoException;
    @Override
    public abstract int create(Payment entity) throws DaoException;
    @Override
    public abstract boolean update(Payment entity) throws DaoException;
    
}

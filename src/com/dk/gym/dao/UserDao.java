package com.dk.gym.dao;

import com.dk.gym.entity.Client;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.User;
import com.dk.gym.exception.DaoException;

import java.util.List;

public abstract class UserDao extends AbstractDao<User> {

    @Override
    public abstract List<User> findAll() throws DaoException;

    public abstract User findEntityById(int id) throws DaoException;

    @Override
    public abstract int create(User entity) throws DaoException;

    @Override
    public abstract boolean update(User entity) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;

    public abstract User findUser(String login, String pass) throws DaoException;

    public abstract boolean findLogin(String login) throws DaoException;

    public abstract List<Client> findAllClient() throws DaoException;

    public abstract List<Trainer> findAllTrainer() throws DaoException;
}

package com.dk.gym.dao;

import com.dk.gym.entity.Client;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.User;
import com.dk.gym.exception.DaoException;

import java.util.List;

public abstract class UserDao extends AbstractDao<User> {

    public abstract User findById(int id) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;

    public abstract User findUser(String login, String pass) throws DaoException;

    public abstract boolean findLogin(String login) throws DaoException;

    public abstract List<Client> findRelatedAllClient() throws DaoException;

    public abstract List<Trainer> findRelatedAllTrainer() throws DaoException;
}

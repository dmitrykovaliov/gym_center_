package com.dk.gym.dao;

import com.dk.gym.entity.Role;
import com.dk.gym.entity.User;
import com.dk.gym.exception.DaoException;

import java.util.List;

public abstract class UserDao extends AbstractDao<User> {

    @Override
    public abstract List<User> findAll() throws DaoException;

    @Override
    public abstract User findEntityById(int id) throws DaoException;

    @Override
    public abstract int create(User entity) throws DaoException;

    @Override
    public abstract boolean update(User entity) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;

    public abstract Role findUser(String login, String pass) throws DaoException;
}

package com.dk.gym.dao;

import com.dk.gym.entity.Client;
import com.dk.gym.entity.Role;
import com.dk.gym.exception.DaoException;

import java.util.List;

public abstract class ClientDao extends AbstractDao<Client> {

    @Override
    public abstract List<Client> findAll() throws DaoException;

    @Override
    public abstract Client findEntityById(int id) throws DaoException;

    @Override
    public abstract int create(Client entity) throws DaoException;

    @Override
    public abstract boolean update(Client entity) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;

    public abstract boolean updateUserId(Integer idUser, int idClient) throws DaoException;
}

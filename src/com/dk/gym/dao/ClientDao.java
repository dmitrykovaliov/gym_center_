package com.dk.gym.dao;

import com.dk.gym.entity.Client;
import com.dk.gym.exception.DaoException;

import java.util.List;

public abstract class ClientDao extends AbstractDao<Client> {

    public abstract Client findById(int id) throws DaoException;

    public abstract boolean delete(int id) throws DaoException;

    public abstract boolean updateUserId(Integer idUser, int idClient) throws DaoException;

    public abstract List<Client> findAllByTrainer(int idUser) throws DaoException;

    public abstract Client findByUserId(int idUser) throws DaoException;
}

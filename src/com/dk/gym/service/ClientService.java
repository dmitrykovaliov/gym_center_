package com.dk.gym.service;

import com.dk.gym.dao.TransactionManager;
import com.dk.gym.builder.ClientDirector;
import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.dao.ClientDao;
import com.dk.gym.dao.impl.ClientDaoImpl;
import com.dk.gym.entity.Client;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.util.FileLoader;
import com.dk.gym.controller.SessionRequestContent;
import com.dk.gym.validation.chain.ChainIdValidator;
import com.dk.gym.validation.entity.ClientValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.command.ReturnMessageType.*;
import static com.dk.gym.dao.AbstractDao.RETURNED_NEGATIVE_RESULT;
import static com.dk.gym.service.ParamConstant.*;


public class ClientService {

    private static final Logger LOGGER = LogManager.getLogger();

    private static ClientService instance;

    private ClientService() {
    }

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }

        return instance;
    }

    public ReturnMessageType createClient(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new ClientValidator().validate(content)) {
                try (ClientDao clientDao = new ClientDaoImpl()) {

                        new FileLoader().loadFile(Client.class.getSimpleName().toLowerCase(), content);

                        Client client = new ClientDirector().buildClient(content);

                    int createdItemId = clientDao.create(client);

                        if (createdItemId != RETURNED_NEGATIVE_RESULT) {
                            message = DONE;
                        } else {
                            message = ENTER_ERROR;
                        }
                } catch (DaoException e) {
                    throw new ServiceException(e);
                }
        }

        LOGGER.log(Level.DEBUG, "CreateItemMessage: " + message);

        return message;
    }

    public List<Client> findAllClient() throws ServiceException {
        List<Client> itemList;

        try (ClientDao clientDao = new ClientDaoImpl()) {
            itemList = clientDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public ReturnMessageType updateClient(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new ClientValidator().validate(content)) {
            TransactionManager transactionManager = new TransactionManager();
                try (ClientDao clientDao = new ClientDaoImpl()) {

                    transactionManager.startTransaction(clientDao);

                        Client client = clientDao.findById(Integer.parseInt(content.findParameter(PARAM_ID)));

                        new FileLoader().loadFile(Client.class.getSimpleName().toLowerCase(), content);

                        new ClientDirector().buildClient(client, content);

                        clientDao.update(client);

                        transactionManager.commit();

                        message = DONE;

                } catch (DaoException e) {
                    transactionManager.rollback();
                    throw new ServiceException(e);
                }

        }

        LOGGER.log(Level.DEBUG, "UpdateItemMessage: " + message);

        return message;
    }

    public ReturnMessageType deleteClient(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new ChainIdValidator().validate(content.findParameter(PARAM_ID))) {
            try (ClientDao clientDao = new ClientDaoImpl()) {
                int parsedId = Integer.parseInt(content.findParameter(PARAM_ID));

                clientDao.delete(parsedId);

                message = DONE;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }

        LOGGER.log(Level.DEBUG, "DeleteItemMessage: " + message);

        return message;
    }

    public List<Client> findAllClientByTrainer(SessionRequestContent content) throws ServiceException {
        List<Client> itemList;

        try (ClientDao clientDao = new ClientDaoImpl()) {
            itemList = clientDao.findAllByTrainer((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "Size of user collection: " + itemList.size());

        return itemList;
    }

    public  Client findClientByUserId(SessionRequestContent content) throws ServiceException {
            Client client;

            try (ClientDao clientDao = new ClientDaoImpl()) {
                client = clientDao.findByUserId((int)content.findSessionAttribute(PARAM_USER_ID));
            } catch (DaoException e) {
                throw new ServiceException(e);
            }

            LOGGER.log(Level.INFO, "clientByUserId: " + client);

            return client;
    }
}

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
import com.dk.gym.controller.RequestContent;
import com.dk.gym.validator.ChainIdValidator;
import com.dk.gym.validator.impl.ClientValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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

    public ReturnMessageType createItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new ClientValidator().validate(content)) {
                try (ClientDao clientDao = new ClientDaoImpl()) {

                        new FileLoader().loadFile(Client.class.getSimpleName().toLowerCase(), content);

                        Client client = new ClientDirector().buildClient(content);

                    int createdItemId = clientDao.create(client);

                        if (createdItemId != -1) {
                            message = ReturnMessageType.DONE;
                        } else {
                            message = ReturnMessageType.ENTER_ERROR;
                        }

                } catch (DaoException e) {
                    throw new ServiceException(e);
                }

        } else {
            message = ReturnMessageType.INVALID;
        }

        LOGGER.log(Level.DEBUG, "CreateItemMessage: " + message);

        return message;
    }

    public List<Client> findItems() throws ServiceException {
        List<Client> itemList;

        try (ClientDao clientDao = new ClientDaoImpl()) {
            itemList = clientDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "Size of collection: " + itemList.size());

        return itemList;
    }

    public ReturnMessageType updateItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new ClientValidator().validate(content)) {
            try (TransactionManager transactionManager = new TransactionManager()) {
                try (ClientDao clientDao = new ClientDaoImpl()) {

                    transactionManager.startTransaction(clientDao);


                        Client client = clientDao.findEntityById(Integer.parseInt(content.findParameter(PARAM_ID)));

                        new FileLoader().loadFile(Client.class.getSimpleName().toLowerCase(), content);

                        new ClientDirector().buildClient(client, content);

                        clientDao.update(client);

                        transactionManager.commit();

                        message = ReturnMessageType.DONE;

                } catch (DaoException e) {
                    transactionManager.rollback();
                    throw new ServiceException(e);
                }
            }
        } else {
            message = ReturnMessageType.INVALID;
        }

        LOGGER.log(Level.DEBUG, "UpdateItemMessage: " + message);

        return message;
    }

    public ReturnMessageType deleteItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new ChainIdValidator().validate(content.findParameter(PARAM_ID))) {
            try (ClientDao clientDao = new ClientDaoImpl()) {
                int parsedId = Integer.parseInt(content.findParameter(PARAM_ID));

                LOGGER.log(Level.DEBUG, "ID: " + parsedId);

                clientDao.delete(parsedId);

                message = ReturnMessageType.DONE;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            message = ReturnMessageType.INVALID;
        }

        LOGGER.log(Level.DEBUG, "DeleteItemMessage: " + message);

        return message;
    }

    public List<Client> findTrainerItems(RequestContent content) throws ServiceException {
        List<Client> itemList;

        try (ClientDao clientDao = new ClientDaoImpl()) {
            itemList = clientDao.findTrainerAll((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "Size of user collection: " + itemList.size());

        return itemList;
    }
}

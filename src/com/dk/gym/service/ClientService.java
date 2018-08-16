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


/**
 * The Class ClientService. Singleton. Logical layer of the application.
 * Contains several useful methods to support interaction between dao and commands.
 */
public class ClientService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** The instance. */
    private static ClientService instance;

    /**
     * Instantiates a new client service.
     */
    private ClientService() {
    }

    /**
     * Gets the single instance of ClientService.
     *
     * @return single instance of ClientService
     */
    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }

        return instance;
    }

    /**
     * Creates the client.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
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

    /**
     * Find all client.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Client> findAllClient() throws ServiceException {
        List<Client> itemList;

        try (ClientDao clientDao = new ClientDaoImpl()) {
            itemList = clientDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    /**
     * Update client.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
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

    /**
     * Delete client.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
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

    /**
     * Find all client by trainer.
     *
     * @param content the content
     * @return the list
     * @throws ServiceException the service exception
     */
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

    /**
     * Find client by user id.
     *
     * @param content the content
     * @return the client
     * @throws ServiceException the service exception
     */
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

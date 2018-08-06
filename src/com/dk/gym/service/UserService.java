package com.dk.gym.service;

import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.dao.*;
import com.dk.gym.dao.impl.ClientDaoImpl;
import com.dk.gym.dao.impl.TrainerDaoImpl;
import com.dk.gym.dao.impl.UserDaoImpl;
import com.dk.gym.entity.Client;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.User;
import com.dk.gym.entity.Role;
import com.dk.gym.builder.UserDirector;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.util.CryptPass;
import com.dk.gym.validator.*;
import com.dk.gym.validator.impl.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

import static com.dk.gym.service.ParamConstant.*;

public class UserService {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DEFAULT_ID_VALUE = "0";

    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }

        return instance;
    }

    public ReturnMessageType createItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;
        String idClient = content.findParameter(ParamConstant.PARAM_CLIENT_ID);
        String idTrainer = content.findParameter(ParamConstant.PARAM_TRAINER_ID);
        boolean validClient = !DEFAULT_ID_VALUE.equals(idClient);
        boolean validTrainer = !DEFAULT_ID_VALUE.equals(idTrainer);

        if (!(!new UserValidator().validate(content) || validClient && validTrainer)) {
            try (TransactionManager transactionManager = new TransactionManager()) {
                try (UserDao userDao = new UserDaoImpl();
                     ClientDao clientDao = new ClientDaoImpl();
                     TrainerDao trainerDao = new TrainerDaoImpl()) {
                    if(!userDao.findLogin(content.findParameter(PARAM_LOGIN))) {

                        transactionManager.startTransaction(userDao, clientDao, trainerDao);

                        User user = new UserDirector().buildUser(content);

                        int userId = userDao.create(user);

                        if (validClient) {
                            clientDao.updateUserId(userId, Integer.parseInt(idClient));
                        }
                        if (validTrainer) {
                            trainerDao.updateUserId(userId, Integer.parseInt(idTrainer));
                        }

                        if (userId != -1) {
                            transactionManager.commit();
                            message = ReturnMessageType.DONE;
                        } else {
                            transactionManager.rollback();
                            message = ReturnMessageType.ENTER_ERROR;
                        }
                    } else {
                        transactionManager.rollback();
                        message = ReturnMessageType.USER_EXIST;
                    }
                } catch (DaoException e) {
                    transactionManager.rollback();
                    throw new ServiceException(e);
                }
            }
        } else {
            message = ReturnMessageType.INVALID;
        }

        LOGGER.log(Level.DEBUG, "CreateItemMessage: " + message);

        return message;
    }

    public List<User> findItems() throws ServiceException {
        List<User> itemList;

        try (UserDao userDao = new UserDaoImpl()) {
            itemList = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "Size of collection: " + itemList.size());

        return itemList;
    }

    public Set<Client> findClientItems() throws ServiceException {
        Set<Client> itemList;

        try (UserDao userDao = new UserDaoImpl()) {
            itemList = userDao.findAllClient();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        LOGGER.log(Level.INFO, "Size of Client: " + itemList.size());

        return itemList;
    }

    public Set<Trainer> findTrainerItems() throws ServiceException {
        Set<Trainer> itemList;

        try (UserDao userDao = new UserDaoImpl()) {
            itemList = userDao.findAllTrainer();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        LOGGER.log(Level.INFO, "Size of Trainer: " + itemList.size());

        return itemList;
    }

    public ReturnMessageType deleteItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new ChainIdValidator().validate(content.findParameter(PARAM_ID))) {
            try (TransactionManager transactionManager = new TransactionManager()){
                try (UserDao userDao = new UserDaoImpl();
                     ClientDao clientDao = new ClientDaoImpl();
                     TrainerDao trainerDao = new TrainerDaoImpl()) {

                    transactionManager.startTransaction(userDao, clientDao, trainerDao);

                    int parsedId = Integer.parseInt(content.findParameter(PARAM_ID));

                    LOGGER.log(Level.DEBUG, "ID: " + parsedId);

                    String idClient = content.findParameter(ParamConstant.PARAM_CLIENT_ID);
                    String idTrainer = content.findParameter(ParamConstant.PARAM_TRAINER_ID);

                    if(new NotEmptyValidator().validate(idClient)) {
                        clientDao.updateUserId(null, Integer.parseInt(idClient));
                    }
                    if(new NotEmptyValidator().validate(idTrainer)) {
                        trainerDao.updateUserId(null, Integer.parseInt(idTrainer));
                    }

                    userDao.delete(parsedId);

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
        LOGGER.log(Level.DEBUG, "DeleteItemMessage: " + message);

        return message;
    }

    public ReturnMessageType checkUser(RequestContent content) throws ServiceException {

        ReturnMessageType message;
        Role role;
        int userId;

        String login = content.findParameter(PARAM_LOGIN);
        String pass = content.findParameter(PARAM_PASS);
        String encryptPass = CryptPass.cryptSha(pass);

        if (encryptPass == null) {
            throw new ServiceException("Pass was not encrypted");
        }

        LOGGER.log(Level.DEBUG, String.format("Login: %s, pass: %s", login, encryptPass));

        if (new UserValidator().validate(content)) {
            try (UserDao userDao = new UserDaoImpl()) {
                User user = userDao.findUser(login, encryptPass);
                role = user.getRole();
                userId = user.getIdUser();
            } catch (DaoException e) {
                throw new ServiceException(e);
            }

            if (role == null) {
                message = ReturnMessageType.USER_NOT_EXIST;
            } else {
                message = ReturnMessageType.DONE;
                content.insertSessionAttribute(PARAM_ROLE, role.toString());
                content.insertSessionAttribute(PARAM_USER_ID, userId);
            }
        } else {
            message = ReturnMessageType.INVALID;
        }
        return message;
    }
}

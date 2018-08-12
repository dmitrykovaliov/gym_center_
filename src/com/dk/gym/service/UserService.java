package com.dk.gym.service;

import com.dk.gym.command.PageConstant;
import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.dao.*;
import com.dk.gym.dao.impl.ClientDaoImpl;
import com.dk.gym.dao.impl.TrainerDaoImpl;
import com.dk.gym.dao.impl.UserDaoImpl;
import com.dk.gym.entity.Client;
import com.dk.gym.entity.Role;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.User;
import com.dk.gym.builder.UserDirector;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.controller.SessionRequestContent;
import com.dk.gym.util.CryptPass;
import com.dk.gym.validator.chain.ChainIdValidator;
import com.dk.gym.validator.impl.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

import static com.dk.gym.command.ReturnMessageType.*;
import static com.dk.gym.dao.AbstractDao.RETURNED_NEGATIVE_RESULT;
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

    public ReturnMessageType createUser(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        String idClient = content.findParameter(ParamConstant.PARAM_CLIENT_ID);
        String idTrainer = content.findParameter(ParamConstant.PARAM_TRAINER_ID);
        boolean validIdClient = !DEFAULT_ID_VALUE.equals(idClient);
        boolean validIdTrainer = !DEFAULT_ID_VALUE.equals(idTrainer);

        if (!(!new UserValidator().validate(content) || validIdClient && validIdTrainer)) {

            TransactionManager transactionManager = new TransactionManager();

            try (UserDao userDao = new UserDaoImpl();
                 ClientDao clientDao = new ClientDaoImpl();
                 TrainerDao trainerDao = new TrainerDaoImpl()) {

                transactionManager.startTransaction(userDao, clientDao, trainerDao);

                if (!userDao.findLogin(content.findParameter(PARAM_LOGIN))) {

                    User user = new UserDirector().buildUser(content);

                    int createdUserId = userDao.create(user);

                    if (createdUserId != RETURNED_NEGATIVE_RESULT) {
                        if (validIdClient) {
                            clientDao.updateUserId(createdUserId, Integer.parseInt(idClient));
                        }
                        if (validIdTrainer) {
                            trainerDao.updateUserId(createdUserId, Integer.parseInt(idTrainer));
                        }
                        transactionManager.commit();
                        message = DONE;
                    } else {
                        transactionManager.rollback();
                        message = ENTER_ERROR;
                    }
                } else {
                    transactionManager.rollback();
                    message = USER_EXIST;
                }
            } catch (DaoException e) {
                transactionManager.rollback();
                throw new ServiceException(e);
            }
        }
        LOGGER.log(Level.DEBUG, "createUserMessage: " + message);

        return message;
    }

    public List<User> findAllUser() throws ServiceException {
        List<User> itemList;

        try (UserDao userDao = new UserDaoImpl()) {
            itemList = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public List<Client> findRelatedAllClient() throws ServiceException {
        List<Client> itemList;

        try (UserDao userDao = new UserDaoImpl()) {
            itemList = userDao.findRelatedAllClient();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public List<Trainer> findRelatedAllTrainer() throws ServiceException {
        List<Trainer> itemList;

        try (UserDao userDao = new UserDaoImpl()) {
            itemList = userDao.findRelatedAllTrainer();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public ReturnMessageType deleteUser(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new ChainIdValidator().validate(content.findParameter(PARAM_ID))) {
            TransactionManager transactionManager = new TransactionManager();
            try (UserDao userDao = new UserDaoImpl();
                 ClientDao clientDao = new ClientDaoImpl();
                 TrainerDao trainerDao = new TrainerDaoImpl()) {

                transactionManager.startTransaction(userDao, clientDao, trainerDao);

                int userId = Integer.parseInt(content.findParameter(PARAM_ID));

                String idClient = content.findParameter(ParamConstant.PARAM_CLIENT_ID);
                String idTrainer = content.findParameter(ParamConstant.PARAM_TRAINER_ID);

                if (!DEFAULT_ID_VALUE.equals(idClient)) {
                    clientDao.updateUserId(null, Integer.parseInt(idClient));
                }
                if (!DEFAULT_ID_VALUE.equals(idTrainer)) {
                    trainerDao.updateUserId(null, Integer.parseInt(idTrainer));
                }
                userDao.delete(userId);

                transactionManager.commit();

                message = DONE;
            } catch (DaoException e) {
                transactionManager.rollback();
                throw new ServiceException(e);
            }

        }
        LOGGER.log(Level.DEBUG, "deleteUserMessage: " + message);

        return message;
    }

    public ReturnMessageType checkUser(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;
        Role role;
        int userId;

        String login = content.findParameter(PARAM_LOGIN);
        String pass = content.findParameter(PARAM_PASS);
        String encryptPass = CryptPass.cryptSha(pass);

        if (encryptPass.isEmpty()) {
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
                message = USER_NOT_EXIST;
            } else {
                message = DONE;
                content.insertSessionAttribute(PARAM_ROLE, role.toString());
                content.insertSessionAttribute(PARAM_USER_ID, userId);
                content.insertSessionAttribute(PARAM_URL_QUERY, PageConstant.PAGE_MAIN);
            }
        }
        return message;
    }
}
